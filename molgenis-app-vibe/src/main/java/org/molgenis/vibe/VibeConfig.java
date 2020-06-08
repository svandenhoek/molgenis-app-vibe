package org.molgenis.vibe;

import static java.util.Objects.requireNonNull;
import static org.molgenis.core.ui.file.FileDownloadController.URI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.molgenis.jobs.Job;
import org.molgenis.jobs.JobFactory;
import org.molgenis.vibe.core.exceptions.InvalidStringFormatException;
import org.molgenis.vibe.core.formats.Phenotype;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(VibeServiceImpl.class)
@Configuration
public class VibeConfig {
  private final VibeService vibeService;

  public VibeConfig(VibeService vibeService) {
    this.vibeService = requireNonNull(vibeService);
  }

  @Bean
  public JobFactory<VibeJobExecution> vibeJobExecutionJobFactory() {
    return new JobFactory<VibeJobExecution>() {
      @Override
      public Job createJob(VibeJobExecution vibeJobExecution) {
        String filename = getDownloadFilename();
        vibeJobExecution.setResultUrl(URI + '/' + filename);
        vibeJobExecution.setProgressInt(0);
        vibeJobExecution.setProgressMessage("Preparing input ...");

        Set<Phenotype> inputPhenotypes = prepareInputPhenotypes(vibeJobExecution.getPhenotypes());

        vibeJobExecution.setProgressMessage("Starting ...");
        return progress ->
            vibeService.retrieveGeneDiseaseCollection(inputPhenotypes, filename, progress);
      }
    };
  }

  private Set<Phenotype> prepareInputPhenotypes(List<String> phenotypes) {
    Set<Phenotype> inputPhenotypes = new HashSet<>();
    List<String> invalidStrings = new ArrayList<>();

    // Tries to convert all input phenotypes to Phenotype objects.
    for (String phenotypeString : phenotypes) {
      try {
        inputPhenotypes.add(new Phenotype(phenotypeString));
      } catch (InvalidStringFormatException e) {
        invalidStrings.add(phenotypeString);
      }
    }

    return inputPhenotypes;
  }

  private String getDownloadFilename() {
    String timestamp =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss.SSS"));
    return String.format("%s.tsv", timestamp);
  }
}
