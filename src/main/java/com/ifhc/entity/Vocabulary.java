package com.ifhc.entity;

public class Vocabulary {
    String word;
    int freqs;

    public Vocabulary(String word, int freqs) {
        this.word = word;
        this.freqs = freqs;
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "Word='" + word + '\'' +
                ", freqs=" + freqs +
                '}';
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        word = word;
    }

    public int getFreqs() {
        return freqs;
    }

    public void setFreqs(int freqs) {
        this.freqs = freqs;
    }
}
