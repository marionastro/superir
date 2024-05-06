package it.unisa.superir.model;

import java.util.HashSet;
import java.util.Set;

public class Folder {
    private final Set<Document> documents;
    private Vocabulary vocabulary;

    public Folder() {
        this.documents = new HashSet<>();
        this.vocabulary = new Vocabulary();
    }

    public int getSize() {
        return documents.size();
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public boolean add(Document document) {
        if (documents.add(document)) {
            vocabulary = vocabulary.join(document.getVocabulary());
            return true;
        }

        return false;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }
}
