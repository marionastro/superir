package it.unisa.superir.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public int getTotalWords() {
        return getDocuments().stream().mapToInt(Document::getLength).sum();
    }

    public Map<String, Long> getOccurrences() {
        return new HashMap<String, Long>() {
            {
                for (Document document : getDocuments()) {
                    document.getTitle().getOccurrences().forEach((w, o) -> merge(w, 0L, (k, v) -> v + o));
                    document.getBody().getOccurrences().forEach((w, o)  -> merge(w, 0L, (k, v) -> v + o));
                }

            }
        };
    }

    public int getAverageDocumentsWords() {
        return getTotalWords() / documents.size();
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public String getName() {
        return name;
    }
}
