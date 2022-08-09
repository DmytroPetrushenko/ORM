package org.knubisoft.util;

public enum FileContentTypeEnum {
    CSV("(([\\w\\d\\-]+[,])+[\\w\\d\\-]+\\b)"),
    XML("\\<\\?xml.+\\>\\<\\w+s\\>.*\\<\\/\\w+s\\>"),
    JSON("\\[.*\\{.+\\}+.*\\]");

    private final String pattern;

    FileContentTypeEnum(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
