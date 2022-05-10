package com.gmail.leonidov.lex.porter.dtos;

import com.gmail.leonidov.lex.porter.entities.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryDto {
    private Long id;
    private String date;
    private String path;
    private Integer fileCount;
    private Integer dirCount;
    private String size;
    private List<File> files;
    public DirectoryDto(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("DirectoryDto: { path: %s, createdAt: %s, files: %s, dirs: %s, size: %s }",
                path, date, fileCount, dirCount, size);
    }
}
