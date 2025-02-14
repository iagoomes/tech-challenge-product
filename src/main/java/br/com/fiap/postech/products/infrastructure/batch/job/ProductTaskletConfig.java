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

            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            File[] files = sourceFolder.listFiles((dir, name) -> name.endsWith(".csv"));

            if (files != null) {
                for (File file : files) {
                    File destinationFile = new File(destinationFolder, file.getName());
                    if (file.renameTo(destinationFile)) {
                        log.info("File moved: {}", file.getName());
                    } else {
                        log.warn("Could not move file: {}", file.getName());
                        throw new RuntimeException("Could not move file: " + file.getName());
                    }
                }
            }
            return RepeatStatus.FINISHED;
        };
    }
}