package it.unisa.superir.service;

import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

/**
 * Service used to get all the documents in a folder.
 * @author Gruppo5
 */
public class FolderLoaderService extends Service<Folder> {
    private final File folderFile;

    /**
     * This service receives a folder name and creates a Folder instance for it,
     * it extracts all txt files that it contains creating a Document instance for each
     * and adding it to the Folder instance.
     * 
     * @param folderFile    the name of a folder.
     */
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
