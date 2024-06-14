package it.unisa.superir.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This Class represents a text file and its content.
 * 
 * @author Gruppo5
 */
public class Document {
    private final File file;
    private final Text title;
    private final Text body;
    private final Vocabulary vocabulary;

    /**
     * Constructor given the file.
     * 
     * @param file          the file to represent.
     * @throws IOException  throws an IOException if the file is not found.
     */
    public Document(File file) throws IOException {
        this.file = file;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String t = reader.readLine();
            String b = reader.lines().collect(Collectors.joining("\n"));

            this.title = new Text(t);
            this.body  = new Text(b);

            Stream<String> concatStream = Stream.concat (
                    Arrays.stream(t.split("[^A-Za-zÀ-ú0-9]+")),
                    Arrays.stream(b.split("[^A-Za-zÀ-ú0-9]+"))
            );

            this.vocabulary = new Vocabulary(concatStream);
        }
    }

    /**
     * Constructor given the file name.
     * 
     * @param fileName      the name of the file to represent.
     * @throws IOException  throws an IOException if the file is not found.
     */
    public Document(String fileName) throws IOException {
        this(new File(fileName));
    }

    /**
     * Function to call to get the document title (first line of file) as a TExt Object.
     * 
     * @return  the title (first line) of the document.
     */
    public Text getTitle() {
        return title;
    }

    /**
     * Function to call to get the document body as a TExt Object.
     * 
     * @return  the body of the document.
     */
    public Text getBody() {
        return body;
    }

    /**
     * Function to call to get the document vocabulary (set of words it contains).
     * 
     * @return  the set of words used in the document.
     */
    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    /**
     *Function to call to get the file the document rappresents.
     * 
     * @return  the file the document is rappresenting.
     */
    public File getFile() {
        return file;
    }

    /**
     * Function to call to get the document length (sum of title and body number of characters).
     * 
     * @return  the number of words in the title and in the body.
     */
    public int getLength() {
        return getTitle().getJoining().split(" ").length +
               getBody().getJoining().split(" ").length;
    }

    /**
     * Function to call to get a mapping between a word in the document and
     * the number of times it is present in it.
     * 
     * @return      a map with words and their score.
     */
    public Map<String, Long> getOccurrences() {
        return new HashMap<String, Long>() {
            {
                getTitle().getOccurrences().forEach((w, o) -> merge(w, 1L, (k, v) -> v + o));
                getBody().getOccurrences().forEach((w, o)  -> merge(w, 1L, (k, v) -> v + o));
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(file, document.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
