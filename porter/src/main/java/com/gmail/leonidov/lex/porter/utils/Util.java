package com.gmail.leonidov.lex.porter.utils;

import com.gmail.leonidov.lex.porter.dtos.FileDto;
import com.gmail.leonidov.lex.porter.exceptions.NotDirectoryException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Util {
    public static final String DIR_SIZE = "<DIR>";

    @SneakyThrows
    public static int getFilesCount(Path path) {
        return Math.toIntExact(Files.list(path).filter(f -> !isDirectory(f)).count());
    }

    @SneakyThrows
    public static int getDirsCount(Path path) {
        return Math.toIntExact(Files.list(path).filter(Util::isDirectory).count());
    }

    public static String convertFileSize(Path path) {
        long size = getFileSize(path);
        double temp;
        String result = size + " B";

        if (size > 1024L && size < 1048576L) {
            temp = size / 1024.;
            result = String.format("%.2f", temp) + " Kb";
        }

        if (size > 1048576L && size < 1073741824) {
            temp = size / 1048576.;
            result = String.format("%.2f", temp) + " Mb";
        }

        if (size > 1048576 * 1024) {
            temp = size / 1073741824.;
            result = String.format("%.2f", temp) + " Gb";
        }
        return result;
    }

    @SneakyThrows
    public static long getFileSize(Path path) {
        return Files.walk(path)
                .map(Path::toFile)
                .filter(File::isFile)
                .mapToLong(File::length)
                .sum();
    }

    @SneakyThrows
    public static Boolean isDirectory(Path path) {
        return Optional.of(path.toFile().isDirectory()).orElseThrow(
                () -> new NotDirectoryException("The specified object is not a directory!"));
    }

    public static List<FileDto> sort(List<FileDto> dtos) {
       return dtos.stream()
               .sorted(compare())
               .sorted(compareToNumericCache())
               .collect(Collectors.toList());
    }

    private static Comparator<FileDto> compare() {
         return  (d1, d2) -> {
            if (isDirectory(Paths.get(d1.getPath())) && !isDirectory(Paths.get(d2.getPath()))) return -1;
            else if (isDirectory(Paths.get(d2.getPath())) && !isDirectory(Paths.get(d1.getPath()))) return 1;
            else Comparator.comparing(FileDto::getName);
            return 0;
        };
    }

    private static Comparator<FileDto> compareToNumericCache() {
        return (d1, d2) -> {
            if ((isDirectory(Paths.get(d1.getPath())) && !isDirectory(Paths.get(d2.getPath())))
                    || ((isDirectory(Paths.get(d2.getPath())) && !isDirectory(Paths.get(d1.getPath()))))
                    || (!d1.doesTheFilenameContainNumbers() && !d2.doesTheFilenameContainNumbers())) {
                return 0;
            }
            else if (d1.doesTheFilenameContainNumbers() && !d2.doesTheFilenameContainNumbers()) return -1;
            else if (d2.doesTheFilenameContainNumbers() && !d1.doesTheFilenameContainNumbers()) return 1;
            else Comparator.comparing(dto -> getNumericCache(d1.getName()) - getNumericCache(d2.getName()));
            return 0;
        };
    }

    private static int getNumericCache(String name) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(name);
        int result = 0;
        int start = 0;
        while (matcher.find(start)) {
            String value = name.substring(matcher.start(), matcher.end());
            result = Integer.parseInt(value);
            start = matcher.end();
        }
        return result;
    }
}
