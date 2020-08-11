package org.molgenis.vibe.response;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.molgenis.vibe.core.formats.Source.Level;

/**
 * The response that is returned to the front-end. This class emulates a clean version of {@link
 * org.molgenis.vibe.core.formats.GeneDiseaseCollection} that does not contain multiple variables
 * that allow for easier data processing but only stores information as simple as possible (so that
 * it can be converted f.e. by serializing and be used elsewhere). As these variables are unused,
 * {@code @SuppressWarnings("unused")} was added to these variables.
 */
public class GeneDiseaseCollectionResponse {
  @SuppressWarnings("unused")
  private Set<Combination> combinations;

  @SuppressWarnings("unused")
  private Map<String, Gene> genes;

  @SuppressWarnings("unused")
  private Map<String, Disease> diseases;

  @SuppressWarnings("unused")
  private Map<String, Source> sources;

  void setCombinations(Set<Combination> combinations) {
    this.combinations = combinations;
  }

  void setGenes(Map<String, Gene> genes) {
    this.genes = genes;
  }

  void setDiseases(Map<String, Disease> diseases) {
    this.diseases = diseases;
  }

  void setSources(Map<String, Source> sources) {
    this.sources = sources;
  }

  static class Combination {
    @SuppressWarnings("unused")
    private String geneId;

    @SuppressWarnings("unused")
    private String diseaseId;

    @SuppressWarnings("unused")
    private Double disgenetScore;

    @SuppressWarnings("unused")
    private Map<String, Integer> sourceCounts;

    @SuppressWarnings("unused")
    private Map<String, Set<PubmedEvidence>> sourcePubmedEvidence;

    Combination(
        String geneId,
        String diseaseId,
        Double disgenetScore,
        Map<String, Integer> sourceCounts,
        Map<String, Set<PubmedEvidence>> sourcePubmedEvidence) {
      this.geneId = requireNonNull(geneId);
      this.diseaseId = requireNonNull(diseaseId);
      this.disgenetScore = requireNonNull(disgenetScore);
      this.sourceCounts = requireNonNull(sourceCounts);
      this.sourcePubmedEvidence = requireNonNull(sourcePubmedEvidence);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Combination that = (Combination) o;
      return Objects.equals(geneId, that.geneId) && Objects.equals(diseaseId, that.diseaseId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(geneId, diseaseId);
    }
  }

  static class PubmedEvidence {
    @SuppressWarnings("unused")
    private URI uri;

    @SuppressWarnings("unused")
    private int year;

    PubmedEvidence(URI uri, int year) {
      this.uri = requireNonNull(uri);
      this.year = requireNonNull(year);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PubmedEvidence that = (PubmedEvidence) o;
      return Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
      return Objects.hash(uri);
    }
  }

  static class Gene {
    @SuppressWarnings("unused")
    private String id;

    @SuppressWarnings("unused")
    private String symbol;

    Gene(String id, String symbol) {
      this.id = requireNonNull(id);
      this.symbol = requireNonNull(symbol);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Gene gene = (Gene) o;
      return Objects.equals(id, gene.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
  }

  static class Disease {
    @SuppressWarnings("unused")
    private String id;

    @SuppressWarnings("unused")
    private String name;

    Disease(String id, String name) {
      this.id = requireNonNull(id);
      this.name = requireNonNull(name);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Disease disease = (Disease) o;
      return Objects.equals(id, disease.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
  }

  static class Source {
    @SuppressWarnings("unused")
    private String name;

    @SuppressWarnings("unused")
    private Level level;

    public Source(String name, Level level) {
      this.name = name;
      this.level = level;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Source source = (Source) o;
      return Objects.equals(name, source.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name);
    }
  }
}
