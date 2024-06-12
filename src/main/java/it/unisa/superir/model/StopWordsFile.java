package it.unisa.superir.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class StopWordsFile {
    private final File file;
    private final Vocabulary vocabulary;

    public StopWordsFile(File file) throws IOException {
        this.file = file;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] words = reader.lines()
                    .collect(Collectors.joining())
                    .split("[^A-zÀ-ú0-9]+");
            this.vocabulary = new Vocabulary(words);
        }
    }

    public StopWordsFile(String fileName) throws IOException {
        this(new File(fileName));
    }

    public File getFile() {
        return file;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopWordsFile that = (StopWordsFile) o;
        return Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
