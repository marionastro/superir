package it.unisa.superir.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *This class rappresents a generic file containing stopwords.
 * @author Gruppo5
 */
public class StopWordsFile {
    private final File file;
    private final Vocabulary vocabulary;

    /**
     * Costructor given the file, its content becomes the StopWordsFile vocabulary.
     * @param file          the file to use as stopwordsfiles.
     * @throws IOException  throws an IOException if the file is not found.
     */
    public StopWordsFile(File file) throws IOException {
        this.file = file;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] words = reader.lines()
                    .collect(Collectors.joining())
                    .split("[^A-zÀ-ú0-9]+");
            this.vocabulary = new Vocabulary(words);
        }
    }

    /**
     *Costructor given the file containing Stop words name.
     * @param fileName        the name of the file to use as stopwordsfiles.
     * @throws IOException    throws an IOException if the file is not found.
     */
    public StopWordsFile(String fileName) throws IOException {
        this(new File(fileName));
    }

    /**
     *Function to run to obtain the file.
     * @return  the file used as a stopword file.
     */
    public File getFile() {
        return file;
    }

    /**
     *Function to run to obtain the vocabulary (set of words present in the file).
     * @return  the vocabulary of the stopword file.
     */
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
