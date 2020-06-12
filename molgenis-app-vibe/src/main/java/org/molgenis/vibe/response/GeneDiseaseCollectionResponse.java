package org.molgenis.vibe.response;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.molgenis.vibe.core.formats.Source.Level;

public class GeneDiseaseCollectionResponse {
  private Set<Combination> combinations;
  private Set<Gene> genes;
  private Set<Disease> diseases;
  private Set<Source> sources;

  void setCombinations(Set<Combination> combinations) {
    this.combinations = combinations;
  }

  void setGenes(Set<Gene> genes) {
    this.genes = genes;
  }

  void setDiseases(Set<Disease> diseases) {
    this.diseases = diseases;
  }

  void setSources(Set<Source> sources) {
    this.sources = sources;
  }

  static class Combination {
    private String geneId;
    private String diseaseId;
    private Double disgenetScore;
    private Map<String, Integer> sourceCounts;
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
    private URI uri;
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
    private String id;
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
    private String id;
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
    private String name;
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
