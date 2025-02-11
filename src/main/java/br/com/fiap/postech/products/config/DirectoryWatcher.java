package br.com.fiap.postech.products.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Component
public class DirectoryWatcher {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public DirectoryWatcher(JobLauncher jobLauncher, Job processProductCsvJob) {
        this.jobLauncher = jobLauncher;
        this.job = processProductCsvJob;
    }

    public void watchDirectoryPath(Path path) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                if (fileName.toString().equals("products.csv")) {
                    System.out.println("Novo arquivo detectado: " + fileName);

                    // Configura e inicia o job do Spring Batch
                    JobParameters jobParameters = new JobParametersBuilder()
                            .addString("filePath", path.resolve(fileName).toString())
                            .addLong("time", System.currentTimeMillis())
                            .toJobParameters();

                    try {
                        jobLauncher.run(job, jobParameters);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            key.reset();
        }
    }
}