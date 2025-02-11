package br.com.fiap.postech.products.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DirectoryWatcherRunner implements CommandLineRunner {

    @Autowired
    private DirectoryWatcher directoryWatcher;

    @Override
    public void run(String... args) throws Exception {
        Path directoryPath = Paths.get("src/main/resources");
        directoryWatcher.watchDirectoryPath(directoryPath);
    }
}