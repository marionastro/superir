package it.unisa.superir.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Document {
    private final File file;
    private final Text title;
    private final Text body;
    private final Vocabulary vocabulary;

    public Document(File file) throws IOException {
        this.file = file;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String t = reader.readLine();
            String b = reader.lines().collect(Collectors.joining());

            this.title = new Text(t);
            this.body  = new Text(b);

            Stream<String> concatStream = Stream.concat(
                    Arrays.stream(t.split("\\W+")),
                    Arrays.stream(b.split("\\W+"))
            );

            this.vocabulary = new Vocabulary(concatStream);
        }
    }

    public Document(String fileName) throws IOException {
        this(new File(fileName));
    }

    public Text getTitle() {
        return title;
    }

    public Text getBody() {
        return body;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
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
