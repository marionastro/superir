package it.unisa.superir.service;

import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

public class FolderLoaderService extends Service<Folder> {
    private final File folderFile;

    public FolderLoaderService(File folderFile) {
        this.folderFile = folderFile;
    }

    @Override
    protected Task<Folder> createTask() {
        return new Task<Folder>() {
            @Override
            protected Folder call() throws Exception {
                Folder folder = new Folder(folderFile.getName());
                File[] files = folderFile.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".txt")) {
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
