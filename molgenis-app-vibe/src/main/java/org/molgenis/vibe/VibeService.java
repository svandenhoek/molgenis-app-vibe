package org.molgenis.vibe;

import java.io.IOException;
import java.util.Set;
import org.molgenis.data.file.model.FileMeta;
import org.molgenis.jobs.Progress;
import org.molgenis.vibe.core.formats.Phenotype;
import org.molgenis.vibe.core.io.input.ModelReader;

interface VibeService {
  FileMeta retrieveGeneDiseaseCollection(
      ModelReader reader, Set<Phenotype> phenotypes, String filename, Progress progress)
      throws IOException;
}
