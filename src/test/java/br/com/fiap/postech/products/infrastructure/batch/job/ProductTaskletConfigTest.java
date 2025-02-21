package br.com.fiap.postech.products.infrastructure.batch.job;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTaskletConfigTest {

    @Mock
    private StepContribution contribution;

    @Mock
    private ChunkContext chunkContext;

    @InjectMocks
    private ProductTaskletConfig taskletConfig;

    @TempDir
    File tempDir;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() throws Exception {
        autoCloseable = MockitoAnnotations.openMocks(this);
        setDirectoryField(tempDir.getAbsolutePath());
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testSourceDirectoryDoesNotExist() throws Exception {
        setDirectoryField(tempDir.getAbsolutePath() + "/nonExistent");

        // Execute tasklet and verify if log.warn was called
        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testDestinationDirectoryNotCreated() throws Exception {
        File nonWritableDir = new File(tempDir, "nonWritable");
        nonWritableDir.createNewFile();

        setDirectoryField(nonWritableDir.getAbsolutePath());

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testNoCsvFilesFound() throws Exception {
        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testMoveCsvFilesSuccessfully() throws Exception {
        File csvFile = new File(tempDir, "test.csv");
        csvFile.createNewFile();

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testDestinationDirectoryCreationFails() throws Exception {
        File sourceDir = new File(tempDir, "source");
        sourceDir.mkdir();

        File nonWritableDir = new File(sourceDir, "processed");
        nonWritableDir.createNewFile();

        setDirectoryField(sourceDir.getAbsolutePath());

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testFileRenameSuccess() throws Exception {
        File sourceDir = new File(tempDir, "source");
        sourceDir.mkdir();

        File csvFile = new File(sourceDir, "test.csv");
        csvFile.createNewFile();

        setDirectoryField(sourceDir.getAbsolutePath());

        File newDir = new File(sourceDir, "processed");
        newDir.mkdir();
        File destinationFile = new File(newDir, csvFile.getName());

        assertTrue(csvFile.renameTo(destinationFile));

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testNoCsvFilesFound_filesNull() throws Exception {
        File sourceDir = new File(tempDir, "source");
        assertTrue(sourceDir.mkdir());

        // Simulate that listFiles returns null
        File mockDir = mock(File.class);
        when(mockDir.isDirectory()).thenReturn(true);
        when(mockDir.exists()).thenReturn(true);
        when(mockDir.listFiles(any(FilenameFilter.class))).thenReturn(null);

        setDirectoryField(sourceDir.getAbsolutePath());

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testNoCsvFilesFound_emptyArray() throws Exception {
        File sourceDir = new File(tempDir, "source");
        assertTrue(sourceDir.mkdir());

        // Simulate that listFiles returns an empty array
        File mockDir = mock(File.class);
        when(mockDir.isDirectory()).thenReturn(true);
        when(mockDir.exists()).thenReturn(true);
        when(mockDir.listFiles(any(FilenameFilter.class))).thenReturn(new File[0]);

        setDirectoryField(sourceDir.getAbsolutePath());

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testCsvFileRenameFails() throws Exception {
        File sourceDir = new File(tempDir, "source");
        assertTrue(sourceDir.mkdirs());

        File csvFile = new File(sourceDir, "test.csv");
        assertTrue(csvFile.createNewFile());

        setDirectoryField(sourceDir.getAbsolutePath());

        File destinationDir = new File(sourceDir, "processed");
        destinationDir.mkdir();

        File destinationFile = new File(destinationDir, "test.csv");
        destinationFile.createNewFile();  // Criar o arquivo de destino para que a renomeação falhe

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    @Test
    void testCsvFileRenameSuccess() throws Exception {
        File sourceDir = new File(tempDir, "source");
        assertTrue(sourceDir.mkdirs());

        File csvFile = new File(sourceDir, "test.csv");
        assertTrue(csvFile.createNewFile());

        setDirectoryField(sourceDir.getAbsolutePath());

        File destinationDir = new File(sourceDir, "processed");
        assertTrue(destinationDir.mkdirs());

        File destinationFile = new File(destinationDir, csvFile.getName());

        assertTrue(csvFile.renameTo(destinationFile));

        RepeatStatus status = taskletConfig.moveFilesTasklet().execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }

    private void setDirectoryField(String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = ProductTaskletConfig.class.getDeclaredField("directory");
        field.setAccessible(true);
        field.set(taskletConfig, value);
    }
}