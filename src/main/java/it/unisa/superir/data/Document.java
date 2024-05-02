package it.unisa.superir.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Document {
    private final File file;
    private final String title;
    private final String body;
    private final Vocabulary titleVocabulary;
    private final Vocabulary bodyVocabulary;
    private int[] titleValues;
    private int[] bodyValues;

    public Document(File file) throws IOException {
        this.file = file;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            title = reader.readLine();
            body  = reader.lines().collect(Collectors.joining());
        }

        this.titleVocabulary = new Vocabulary(title == null ? "" : title);
        this.bodyVocabulary  = new Vocabulary(body);
    }

    public Document(String fileName) throws IOException {
        this(new File(fileName));
    }

    //todo To refactor, i dont like
    public int[] getTitleValues() {
        if (titleValues != null)
            return titleValues;

        titleValues = new int[titleVocabulary.getLength()];

        for (String titleWord : title.toLowerCase().split("\\W+")) {
            Map.Entry<Token, Integer> entry = titleVocabulary.getEntry(titleWord);
            titleValues[entry.getValue()] += Math.round(entry.getKey().getWeight() * 10);
        }

        return titleValues;
    }

    //todo To refactor, i dont like
    public int[] getBodyValues() {
        if (bodyValues != null)
            return bodyValues;

        bodyValues = new int[bodyVocabulary.getLength()];

        for (String bodyWord : body.toLowerCase().split("\\W+")) {
            Map.Entry<Token, Integer> entry = bodyVocabulary.getEntry(bodyWord);
            bodyValues[entry.getValue()] += Math.round(entry.getKey().getWeight() * 10);
        }

        return bodyValues;
    }

    public Vocabulary getTitleVocabulary() {
        return titleVocabulary;
    }

    public Vocabulary getBodyVocabulary() {
        return bodyVocabulary;
    }
}
