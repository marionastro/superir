package it.unisa.superir.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represent a set of words of a file.
 * 
 * @author Gruppo5
 */
public class Vocabulary {
    private final List<String> tokens;

    /**
     * Constructor given a stream of strings (words).
     * @param stream a stream of words.
     */
    public Vocabulary(Stream<String> stream) {
        this.tokens = stream
                .filter(s -> !s.trim().isEmpty()) // Exclude blank strings.
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Constructor given a collection of strings (words).
     * @param strings   a collection of words.
     */
    public Vocabulary(Collection<String> strings) {
        this(strings.stream());
    }

    /**
     * Constructor given a series of strings (words).
     * 
     * @param strings   a series of words.
     */
    public Vocabulary(String... strings) {
        this(Arrays.stream(strings));
    }

    /**
     * Function to run to initialize the  set of words to an empty set.
     */
    public Vocabulary() {
        this.tokens = new ArrayList<>();
    }

    /**
     * Function to run to get the stream of words of the vocabulary.
     * @return  the stream of the vocabulary.
     */
    public Stream<String> stream() {
        return tokens.stream();
    }

    /**
     * Function to run to add to merge the vocabulary with another vocabulary.
     * @param vocabulary    the vocabulary to fuse with.
     * @return              a vocabulary that contains all the instances words plus the param ones.
     */
    public Vocabulary join(Vocabulary vocabulary) {
        return new Vocabulary(Stream.concat(stream(), vocabulary.stream()));
    }

    /**
     * Function to run to filter out from the vocabulary the words present in another vocabulary.
     * @param vocabulary    the vocabulary with words to filter out.
     * @return              a vocabulary that contains the instance words except the param ones.
     */
    public Vocabulary filter(Vocabulary vocabulary) {
        Stream<String> res = stream().filter(s -> vocabulary.stream().noneMatch(s::equals));
        return new Vocabulary(res);
    }

    /**
     * Function to run to get the word corresponding to an index in the set of words.
     * @param index     the index of a word in the list.
     * @return          the word at that index.
     */
    public String getToken(int index) {
        return tokens.get(index);
    }

    /**
     * Function to run to get the index of a word in the set of words.
     * @param token     a word present in the list.
     * @return          its index.
     */
    public Integer getIndex(String token) {
        return tokens.indexOf(token);
    }

    /**
     * Function to run to get the number of words in the vocabulary.
     * @return  the number of words in the vocabulary.
     */
    public int getLength() {
        return tokens.size();
    }

    @Override
    public String toString() {
        return String.join(", ", tokens);
    }
}
