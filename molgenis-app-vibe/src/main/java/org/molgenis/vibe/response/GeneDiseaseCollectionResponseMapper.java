package org.molgenis.vibe.response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.molgenis.vibe.core.formats.Disease;
import org.molgenis.vibe.core.formats.Gene;
import org.molgenis.vibe.core.formats.GeneDiseaseCollection;
import org.molgenis.vibe.core.formats.GeneDiseaseCombination;
import org.molgenis.vibe.core.formats.PubmedEvidence;
import org.molgenis.vibe.core.formats.Source;

/**
 * Converts a {@link GeneDiseaseCollection} to a {@link GeneDiseaseCollectionResponse}. <br>
 * <br>
 * <b>IMPORTANT:</b> For {@link Source} conversion/key usage, currently {@link Source#getName()} is
 * used and not {@link Source#getUri()}. This simplify the output as this field is only relevant
 * when multiple {@link Source}{@code s} would return the same {@link Source#getName()}. This should
 * only be the case when multiple {@link Source} ojects in a {@link GeneDiseaseCollection} are
 * present that refer to a different version of the same source. Under normal circumstances, this
 * should not happen.
 */
public abstract class GeneDiseaseCollectionResponseMapper {
  public static GeneDiseaseCollectionResponse mapToResponse(GeneDiseaseCollection source) {
    GeneDiseaseCollectionResponse response = new GeneDiseaseCollectionResponse();
    mapToResponse(source, response);
    return response;
  }

  private static void mapToResponse(
      GeneDiseaseCollection source, GeneDiseaseCollectionResponse target) {
    processCombinations(source.getGeneDiseaseCombinations(), target);
    processGenes(source.getGenes(), target);
    processDiseases(source.getDiseases(), target);
  }

  /**
   * Processes the {@link GeneDiseaseCollection#getGeneDiseaseCombinations()}
   *
   * @param sourceCombinations the {@link GeneDiseaseCombination}{@code s} from the {@link
   *     GeneDiseaseCollection}
   * @param target the {@link GeneDiseaseCollectionResponse} in which the data should be stored
   */
  private static void processCombinations(
      Set<GeneDiseaseCombination> sourceCombinations, GeneDiseaseCollectionResponse target) {
    Set<GeneDiseaseCollectionResponse.Combination> responseCombinations = new HashSet<>();
    Map<String, GeneDiseaseCollectionResponse.Source> responseSources = new HashMap<>();

    sourceCombinations.forEach(
        geneDiseaseCombination -> {
          // Adds combinations.
          responseCombinations.add(
              new GeneDiseaseCollectionResponse.Combination(
                  geneDiseaseCombination.getGene().getId(),
                  geneDiseaseCombination.getDisease().getId(),
                  geneDiseaseCombination.getDisgenetScore(),
                  processSourcesCount(geneDiseaseCombination.getSourcesCount()),
                  processPubmedEvidence(geneDiseaseCombination)));

          // Adds sources.
          geneDiseaseCombination
              .getSourcesCount()
              .keySet()
              .forEach(
                  sourceEntity ->
                      responseSources.put(
                          sourceEntity.getName(),
                          new GeneDiseaseCollectionResponse.Source(
                              sourceEntity.getName(), sourceEntity.getLevel())));
        });

    // Sets values in target.
    target.setCombinations(responseCombinations);
    target.setSources(responseSources);
  }

  /**
   * Processes the counts for the {@link Source}{@code s} in a {@link GeneDiseaseCombination}.
   *
   * @param sourcesCount {@link GeneDiseaseCombination#getSourcesCount()}
   * @return a {@link Map} containing the {@link Source#getUri()} as key and the count belonging to
   *     that {@link Source} as value
   */
  private static Map<String, Integer> processSourcesCount(Map<Source, Integer> sourcesCount) {
    Map<String, Integer> responseSourcesCount = new HashMap<>();

    sourcesCount
        .keySet()
        .forEach(source -> responseSourcesCount.put(source.getName(), sourcesCount.get(source)));

    return responseSourcesCount;
  }

  /**
   * Processes the {@link PubmedEvidence} belonging to a {@link GeneDiseaseCombination}.
   *
   * @param geneDiseaseCombination the {@link GeneDiseaseCombination} that needs to be processed
   * @return a {@link Map} containing the {@link Source#getUri()} as key and a {@link
   *     org.molgenis.vibe.response.GeneDiseaseCollectionResponse.PubmedEvidence} {@link Set}
   *     belonging to that {@link Source} as value
   */
  private static Map<String, Set<GeneDiseaseCollectionResponse.PubmedEvidence>>
      processPubmedEvidence(GeneDiseaseCombination geneDiseaseCombination) {
    Map<String, Set<GeneDiseaseCollectionResponse.PubmedEvidence>> responsePubmedEvidence =
        new HashMap<>();

    geneDiseaseCombination
        .getSourcesWithPubmedEvidence()
        .forEach(
            source ->
                responsePubmedEvidence.put(
                    source.getName(),
                    convertPubmedEvidenceSetToResponse(
                        geneDiseaseCombination.getPubmedEvidenceForSource(source))));

    return responsePubmedEvidence;
  }

  /**
   * Converts a {@link Set} of {@link PubmedEvidence} to a {@link Set} of {@link
   * org.molgenis.vibe.response.GeneDiseaseCollectionResponse.PubmedEvidence}
   *
   * @param evidence the {@link PubmedEvidence} {@link Set} to be converted
   * @return the converted {@link
   *     org.molgenis.vibe.response.GeneDiseaseCollectionResponse.PubmedEvidence} {@link Set}
   */
  private static Set<GeneDiseaseCollectionResponse.PubmedEvidence>
      convertPubmedEvidenceSetToResponse(Set<PubmedEvidence> evidence) {
    Set<GeneDiseaseCollectionResponse.PubmedEvidence> responsePubmedEvidence = new HashSet<>();

    evidence.forEach(
        pubmedEvidence ->
            responsePubmedEvidence.add(
                new GeneDiseaseCollectionResponse.PubmedEvidence(
                    pubmedEvidence.getUri(), pubmedEvidence.getReleaseYear())));

    return responsePubmedEvidence;
  }

  /**
   * Processes the {@link Gene}{@code s} from a {@link GeneDiseaseCollection}
   *
   * @param sourceGenes {@link GeneDiseaseCollection#getGenes()}
   * @param target the {@link GeneDiseaseCollectionResponse} in which the data should be stored
   */
  private static void processGenes(Set<Gene> sourceGenes, GeneDiseaseCollectionResponse target) {
    Map<String, GeneDiseaseCollectionResponse.Gene> responseGenes = new HashMap<>();

    sourceGenes.forEach(
        gene ->
            responseGenes.put(
                gene.getId(),
                new GeneDiseaseCollectionResponse.Gene(gene.getId(), gene.getSymbol().getId())));

    // Sets values in target.
    target.setGenes(responseGenes);
  }

  /**
   * Processes the {@link Disease}{@code s} from a {@link GeneDiseaseCollection}
   *
   * @param sourceDiseases {@link GeneDiseaseCollection#getDiseases()}
   * @param target the {@link GeneDiseaseCollectionResponse} in which the data should be stored
   */
  private static void processDiseases(
      Set<Disease> sourceDiseases, GeneDiseaseCollectionResponse target) {
    Map<String, GeneDiseaseCollectionResponse.Disease> responseDiseases = new HashMap<>();

    sourceDiseases.forEach(
        disease ->
            responseDiseases.put(
                disease.getId(),
                new GeneDiseaseCollectionResponse.Disease(disease.getId(), disease.getName())));

    // Sets values in target.
    target.setDiseases(responseDiseases);
  }
}
