package it.unisa.superir.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vocabulary {
    private final List<String> tokens;

    public Vocabulary(Stream<String> stream) {
        this.tokens = stream
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    public Vocabulary(Collection<String> strings) {
        this(strings.stream());
    }

    public Vocabulary(String... strings) {
        this(Arrays.stream(strings));
    }

    public Vocabulary() {
        this.tokens = new ArrayList<>();
    }

    public Stream<String> stream() {
        return tokens.stream();
    }

    public Vocabulary join(Vocabulary vocabulary) {
        return new Vocabulary(Stream.concat(stream(), vocabulary.stream()));
    }

    public Vocabulary filter(Vocabulary vocabulary) {
        Stream<String> res = stream()
                .filter(s -> vocabulary.stream().noneMatch(s::equals));
        return new Vocabulary(res);
    }

    public String getToken(int index) {
        return tokens.get(index);
    }

    public Integer getIndex(String token) {
        return tokens.indexOf(token);
    }

    public int getLength() {
        return tokens.size();
    }

    @Override
    public String toString() {
        return tokens.toString();
    }
}
