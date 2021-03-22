package org.molgenis.vibe;

import static java.util.Objects.requireNonNull;
import static org.molgenis.core.ui.file.FileDownloadController.URI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.molgenis.data.DataService;
import org.molgenis.data.file.FileStore;
import org.molgenis.data.file.model.FileMeta;
import org.molgenis.data.file.model.FileMetaFactory;
import org.molgenis.data.file.model.FileMetaMetadata;
import org.molgenis.jobs.Progress;
import org.molgenis.vibe.core.database_processing.GenesForPhenotypeRetriever;
import org.molgenis.vibe.core.formats.Phenotype;
import org.molgenis.vibe.core.io.input.ModelReader;
import org.springframework.stereotype.Service;

@Service
class VibeServiceImpl implements VibeService {
  private final DataService dataService;
  private final FileStore fileStore;
  private final FileMetaFactory fileMetaFactory;

  VibeServiceImpl(DataService dataService, FileStore fileStore, FileMetaFactory fileMetaFactory) {
    this.dataService = requireNonNull(dataService);
    this.fileStore = requireNonNull(fileStore);
    this.fileMetaFactory = requireNonNull(fileMetaFactory);
  }

  @Override
  public FileMeta retrieveGeneDiseaseCollection(
      ModelReader reader, Set<Phenotype> phenotypes, String filename, Progress progress)
      throws IOException {
    GenesForPhenotypeRetriever retriever = new GenesForPhenotypeRetriever(reader, phenotypes);
    retriever.run();
    String collectionJson =
        VibeSerializer.serializeGeneDiseaseCollection(retriever.getGeneDiseaseCollection());

    FileMeta fileMeta;
    try (InputStream inputStream =
        new ByteArrayInputStream(collectionJson.getBytes(StandardCharsets.UTF_8))) {
      File file = fileStore.store(inputStream, filename);
      fileMeta = createFileMeta(file);
      dataService.add(FileMetaMetadata.FILE_META, fileMeta);
    }
    progress.increment(1);
    progress.status("Done.");
    return fileMeta;
  }

  private FileMeta createFileMeta(File file) {
    FileMeta fileMeta = fileMetaFactory.create(file.getName());
    fileMeta.setContentType("application/json");
    fileMeta.setSize(file.length());
    fileMeta.setFilename("vibe-results.json");
    fileMeta.setUrl(URI + "/" + file.getName());
    return fileMeta;
  }
}
