package br.com.fiap.postech.products.infrastructure.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductTaskletConfig {

    @Value("${upload.directory}")
    private String directory;

    @Bean
    public Tasklet moveFilesTasklet() {
        return (contribution, chunkContext) -> {
            File sourceFolder = new File(directory);
            File destinationFolder = new File(directory + "/processed");

            if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
                log.warn("Source directory '{}' does not exist or is not a directory.", directory);
                return RepeatStatus.FINISHED;
            }

            if (!destinationFolder.exists() && !destinationFolder.mkdirs()) {
                log.error("Failed to create destination directory '{}'.", destinationFolder.getAbsolutePath());
                return RepeatStatus.FINISHED;
            }

            File[] files = sourceFolder.listFiles((dir, name) -> name.endsWith(".csv"));
            if (files == null || files.length == 0) {
                log.info("No CSV files found in '{}'.", directory);
                return RepeatStatus.FINISHED;
            }

            for (File file : files) {
                File destinationFile = new File(destinationFolder, file.getName());
                if (file.renameTo(destinationFile)) {
                    log.info("File '{}' moved to '{}'.", file.getName(), destinationFolder.getAbsolutePath());
                } else {
                    log.error("Could not move file '{}'.", file.getName());
                }
            }

            return RepeatStatus.FINISHED;
        };
    }
}