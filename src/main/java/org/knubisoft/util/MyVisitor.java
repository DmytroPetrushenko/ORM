package org.knubisoft.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MyVisitor extends SimpleFileVisitor<Path> {
    private final List<Path> classList = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean matches = file.toString().matches("(\\w\\:(\\\\.+)+?\\\\\\w+\\.class)");
        if (matches) {
            classList.add(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
