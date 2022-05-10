package com.gmail.leonidov.lex.porter.services;

import com.gmail.leonidov.lex.porter.dtos.FileDto;
import com.gmail.leonidov.lex.porter.entities.Directory;
import com.gmail.leonidov.lex.porter.entities.File;
import com.gmail.leonidov.lex.porter.repositories.FileRepository;
import com.gmail.leonidov.lex.porter.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void saveAll (List<File> files) {
        fileRepository.saveAll(files);
        log.info("Information about files from this directory is stored in the database.");
    }

    public FileDto createDto(Path path, Directory dir) {
        String name = path.getFileName().toString();
        java.io.File file = path.toFile();
        String size;
        if (file.isDirectory()) {
            size = Util.DIR_SIZE;
        } else {
            size = Util.convertFileSize(path);
        }
        return new FileDto(name, size, path.toString(), dir);
    }
}
