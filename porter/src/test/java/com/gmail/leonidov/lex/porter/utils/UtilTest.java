package com.gmail.leonidov.lex.porter.utils;

import com.gmail.leonidov.lex.porter.dtos.FileDto;
import com.gmail.leonidov.lex.porter.entities.Directory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilTest {

    @Test
    public void testGetFilesCount() {
        Path path = Paths.get("src/test/resources/testedPackage");
        assertEquals(Util.getFilesCount(path), 5);
        assertNotEquals(Util.getFilesCount(path), 2);
    }

    @Test
    public void testGetDirsCount() {
        Path path = Paths.get("src/test/resources/testedPackage");
        assertEquals(Util.getDirsCount(path), 1);
        assertNotEquals(Util.getDirsCount(path), 2);
    }

    @Test
    public void testConvertFileSize() {
        Path path = Paths.get("src/test/resources/testedPackage/1.txt");
        assertEquals("3 B", Util.convertFileSize(path));
    }

    @Test
    public void testGetFileSize() {
        Path path = Paths.get("src/test/resources/testedPackage/1.txt");
        assertEquals(3, Util.getFileSize(path));
    }

    @Test
    public void testIsDirectory() {
        Path pathToDir = Paths.get("src/test/resources/testedPackage/tp");
        Path pathToFile = Paths.get("src/test/resources/testedPackage/1.txt");
        assertTrue(Util.isDirectory(pathToDir));
        assertFalse(Util.isDirectory(pathToFile));
    }

    @Test
    public void testSort() {
        List<FileDto> dtos = Util.sort(getDtos());
        assertEquals(Util.sort(dtos).get(0).getName(), "tp");
    }

    private List<FileDto> getDtos() {
        FileDto dto0 = new FileDto("tp", "<DIR>", "src/test/resources/testedPackage/tp", new Directory());
        FileDto dto1 = new FileDto("2c3p.txt", "1 B", "src/test/resources/testedPackage/2c3p.txt", new Directory());
        FileDto dto2 = new FileDto("2c4p.txt", "1 B", "src/test/resources/testedPackage/2c4p.txt", new Directory());
        FileDto dto3 = new FileDto("a2b3.txt", "1 B", "src/test/resources/testedPackage/a2b3.txt", new Directory());
        FileDto dto4 = new FileDto("b2b3.txt", "1 B", "src/test/resources/testedPackage/b2b3.txt", new Directory());
        FileDto dto5 = new FileDto("1.txt", "1 B", "src/test/resources/testedPackage/1.txt", new Directory());
        FileDto dto6 = new FileDto("tp1", "<DIR>", "src/test/resources/testedPackage/tp1", new Directory());
        return new ArrayList<>(Arrays.asList(dto0, dto1, dto2, dto3, dto4, dto5, dto6));
    }
}
