package org.molgenis.vibe;

import org.mockito.Mock;
import org.molgenis.data.DataService;
import org.molgenis.data.file.FileStore;
import org.molgenis.data.file.model.FileMeta;
import org.molgenis.data.file.model.FileMetaFactory;
import org.molgenis.jobs.Progress;
import org.molgenis.util.AppDataRootProvider;
import org.testng.annotations.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VibeServiceImplIT {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    private final String FILE_NAME = "testFile";

    @Mock private File file;

    private VibeServiceImpl vibeServiceImpl;

    @Mock private DataService dataService;
    @Mock private FileStore fileStore;
    @Mock private FileMetaFactory fileMetaFactory;
    @Mock private FileMeta fileMeta;
    @Mock private Progress progress;

    @BeforeClass
    public void beforeAll() throws IOException {
        System.setErr(new PrintStream(errContent));

        AppDataRootProvider.setAppDataRoot(Paths.get(Thread.currentThread().getContextClassLoader().getResource("app_data_root").getFile()));

        dataService = mock(DataService.class);

        file = mock(File.class);
        when(file.getName()).thenReturn(FILE_NAME);

        fileStore = mock(FileStore.class);
        when(fileStore.store(any(), anyString())).thenReturn(file);

        fileMeta = mock(FileMeta.class);

        fileMetaFactory = mock(FileMetaFactory.class);
        when(fileMetaFactory.create(FILE_NAME)).thenReturn(fileMeta);

        progress = mock(Progress.class);

        vibeServiceImpl =
                new VibeServiceImpl(dataService, fileStore, fileMetaFactory);
    }

    @AfterClass
    public void afterAll() {
        System.setErr(originalErr);
    }

    @AfterTest
    public void afterEach() {
        errContent.reset();
    }

    @Test
    public void testOneValidHpoTerm() throws IOException {
        vibeServiceImpl.executeVibe(Arrays.asList(new String[]{"HP:0002996"}), FILE_NAME, progress);
    }

    @Test
    public void testNoHpoTerms() throws IOException {
        vibeServiceImpl.executeVibe(new ArrayList<>(), FILE_NAME, progress);
        // TODO: test on expected behavior
    }

    @Test
    public void testInvalidInput() throws IOException {
        String inputValue = "UMLS:C0123456";
        vibeServiceImpl.executeVibe(Arrays.asList(new String[]{inputValue}), FILE_NAME, progress);
        // TODO: test on expected behavior
    }
}
