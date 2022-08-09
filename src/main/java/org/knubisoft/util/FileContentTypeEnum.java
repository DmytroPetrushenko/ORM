package org.knubisoft.util;

public enum FileContentTypeEnum {
    CSV(".+\\.csv\\b"),
    XML(".+\\.xml\\b"),
    JSON(".+\\.json\\b");

    private String pattern;

    FileContentTypeEnum(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
