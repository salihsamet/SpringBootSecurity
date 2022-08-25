package com.clinked.articleservice.enums;

public enum Content {
    SCIENCE("SCIENCE"),
    MUSIC("MUSIC"),
    ART("ART"),
    EDUCATION("EDUCATION");

    private String value;

    private Content(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
