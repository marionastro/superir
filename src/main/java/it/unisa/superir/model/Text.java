package it.unisa.superir.model;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Text {
    private final String[] content;

    public Text(String... content) {
        this.content = content;
    }

    public Text(String string) {
        this(string.split("\\b"));
    }

    public Stream<String> stream() {
        return Arrays.stream(content);
    }

    public boolean containsIgnoreCase(String string) {
        return stream().anyMatch(s -> s.equalsIgnoreCase(string));
    }

    public long countIgnoreCase(String string) {
        return stream().filter(s -> s.equalsIgnoreCase(string)).count();
    }

    public String getJoining() {
        return stream().collect(Collectors.joining());
    }

    public String[] getContent() {
        return content;
    }
}
