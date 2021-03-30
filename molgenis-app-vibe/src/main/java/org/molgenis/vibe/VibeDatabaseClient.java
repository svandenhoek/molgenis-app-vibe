package org.molgenis.vibe;

import static java.util.Objects.requireNonNull;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import org.molgenis.vibe.core.io.input.ModelReader;
import org.molgenis.vibe.core.io.input.ModelReaderFactory;
import org.molgenis.vibe.core.io.input.VibeDatabase;

public class VibeDatabaseClient implements Closeable {
  private Path hdtPath;
  private ModelReader reader;

  VibeDatabaseClient(Path hdtPath) {
    this.hdtPath = requireNonNull(hdtPath);
  }

  public ModelReader getReader() {
    return reader;
  }

  @PostConstruct
  public void init() throws IOException {
    VibeDatabase database = new VibeDatabase(hdtPath, ModelReaderFactory.HDT);
    reader = database.getModelReader();
  }

  @Override
  public void close() {
    reader.close();
  }
}
