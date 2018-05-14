package com.example.thien.recentwords;

public class Word {
    private String keyword;
    private String meaning;

    public Word(String keyword, String meaning) {
        this.keyword = keyword;
        this.meaning = meaning;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword( String keyword) {
        this.keyword = keyword;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Word {keyword=" + keyword + ", meaning=" + meaning + "}";
    }
}
