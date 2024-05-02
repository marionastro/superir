package it.unisa.superir.data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vocabulary {
    private final Map<Token, Integer> tokens;

    public Vocabulary(Set<Token> tokens) {
        AtomicInteger indexes = new AtomicInteger(0);
        this.tokens = tokens.stream()
                .collect(Collectors.toMap(Function.identity(), o -> indexes.getAndIncrement()));
    }

    public Vocabulary(Collection<String> strings) {
        this(strings.stream()
                .distinct()
                .map(Token::new)
                .collect(Collectors.toSet())
        );
    }

    public Vocabulary(String... strings) {
        this(Arrays.asList(strings));
    }

    public Vocabulary(String s) {
        this(s.toLowerCase().split("\\W+"));
    }

    public Stream<Map.Entry<Token, Integer>> stream() {
        return tokens.entrySet().stream();
    }

    public Vocabulary join(Vocabulary vocabulary) {
        Set<Token> tokens = Stream.concat(stream(), vocabulary.stream())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return new Vocabulary(tokens);
    }

    public Map.Entry<Token, Integer> getEntry(String s) {
        return stream()
                .filter(e -> e.getKey().getString().equals(s))
                .findFirst()
                .orElse(null);
    }

    public int getLength() {
        return tokens.size();
    }

    // Util
    public void printSorted() {
        stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return tokens.toString();
    }
}
