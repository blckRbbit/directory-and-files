package com.gmail.leonidov.lex.porter.dtos;

import com.gmail.leonidov.lex.porter.entities.Directory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private String name;
    private String size;
    private String path;
    private Directory directory;

    public boolean doesTheFilenameContainNumbers() {
        return name.chars().anyMatch(Character::isDigit);
    }

    @Override
    public String toString() {
        return String.format("FileDto: { name: %s, size: %s, directory_id: %s }", name, size, directory.getId());
    }
}
