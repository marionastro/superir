package it.unisa.superir.data;

import java.util.Objects;

public class Token {
    private final String string;
    private float weight;

    // Constructor for IF-IDF, different weights
    public Token(String string, float weight) {
        this.string = string;
        this.weight = weight;
    }

    // Constructor for standard IR algorithm, same weights
    public Token(String string) {
        this(string, 0.1f);
    }

    public String getString() { return string; }

    public float getWeight() { return weight; }

    public void setWeight(float weight) { this.weight = weight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(string, token.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

    @Override
    public String toString() {
        return String.format("%s(%.2f)", string, weight);
    }
}
