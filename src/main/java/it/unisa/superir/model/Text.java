package it.unisa.superir.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *This class rappresents the text of a textual document.
 * @author Gruppo5
 */
public class Text {
    private final String[] content;

    /**
     *Costructor given a series of strings.
     * @param content   a series of strings.
     */
    public Text(String... content) {
        this.content = content;
    }

    /**
     *Costructor given a single string to split on word boundaries.
     * @param string    a string.
     */
    public Text(String string) {
        this(string.split("\\b"));
    }

    /**
     * Function to run to get the stream of the words in the documents.
     * @return      the stream of the text.
     */
    public Stream<String> stream() {
        return Arrays.stream(content);
    }

    /**
     * Function to run to see if a word is present in a Text ignoring capitalization.
     * @param string        a string that gets searched in the Text.
     * @return              true if the Text contains the word, false otherwise.
     */
    public boolean containsIgnoreCase(String string) {
        return stream().anyMatch(s -> s.equalsIgnoreCase(string));
    }

    /**
     * Function to run to get a string obtained by joining all the Text content.
     * @return  a string composed of all the Text words.
     */
    public String getJoining() {
        return stream().collect(Collectors.joining());
    }

    /**
     *Function to run to get the number of words in a text.
     * @return  the number of words in the Text.
     */
    public Map<String, Long> getOccurrences() {
        return stream().collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }

    /**
     *Function to run to get the array of all the Text words and other characters
     * (such as spaces; commas; question marks etc).
     * 
     * @return  the entire Text.
     */
    public String[] getContent() {
        return content;
    }
}
