package it.unisa.superir.service;

import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

public class FolderLoaderService extends Service<Folder> {
    private final File folderFile;

    // TODO se fosse null ?
    public FolderLoaderService(String folderPath) {
        this.folderFile = new File(folderPath);
    }

    @Override
    protected Task<Folder> createTask() {
        return new Task<Folder>() {
            @Override
            protected Folder call() throws Exception {
                Folder folder = new Folder();
                File[] files = folderFile.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            Document document = new Document(file);
                            folder.add(document);
                        }
                    }
                }

                return folder;
            }
        };
    }
}
