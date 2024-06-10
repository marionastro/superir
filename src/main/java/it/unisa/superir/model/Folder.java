package it.unisa.superir.model;

import java.util.HashSet;
import java.util.Set;

public class Folder {
    public final String name;
    private final Set<Document> documents;
    private Vocabulary vocabulary;

    public Folder(String name) {
        this.name = name;
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

    public void filter(Vocabulary vocabulary) {
        this.vocabulary = this.vocabulary.filter(vocabulary);
    }

    public void filter(StopWordsFile stopWordsFile) {
        this.filter(stopWordsFile.getVocabulary());
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public String getName() {
        return name;
    }
}
