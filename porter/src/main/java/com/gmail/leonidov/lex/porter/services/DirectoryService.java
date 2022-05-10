package com.gmail.leonidov.lex.porter.services;

import com.gmail.leonidov.lex.porter.dtos.DirectoryDto;
import com.gmail.leonidov.lex.porter.dtos.FileDto;
import com.gmail.leonidov.lex.porter.entities.Directory;
import com.gmail.leonidov.lex.porter.entities.File;
import com.gmail.leonidov.lex.porter.mappers.DirectoryMapper;
import com.gmail.leonidov.lex.porter.mappers.FileMapper;
import com.gmail.leonidov.lex.porter.repositories.DirectoryRepository;
import com.gmail.leonidov.lex.porter.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryRepository directoryRepository;
    private final FileService fileService;

    public Page<DirectoryDto> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DirectoryDto> list;

        if (getAll().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, getAll().size());
            list = getAll().subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), getAll().size());
    }

    private List<DirectoryDto> getAll() {
        return directoryRepository.findAll().stream()
                .map(DirectoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<FileDto> getAllFromDirectory(Long id) {
        List<FileDto> result = directoryRepository.getById(id).getFiles().stream()
                .map(FileMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return Util.sort(result);
    }

    public void create(String str) {
        Path path = Paths.get(str);
        if (Util.isDirectory(path)) {
            DirectoryDto dto = createDto(path);
            Directory dir = directoryRepository.save(DirectoryMapper.INSTANCE.toEntity(dto));
            fileService.saveAll(getFilesForDirectory(path, dir));
            log.info("The state of the directory was saved to the database along the path << {} >>, c ID << {} >>, {}",
                    dir.getPath(), dir.getId(), dir.getDate());
        } else {
            log.info("Not saved. The user specified an invalid path, or it is not the path to the directory {}",
                    LocalDateTime.now());
        }
    }

    private DirectoryDto createDto(Path path) {
        DirectoryDto dto = new DirectoryDto(path.toString());
        dto.setDate(LocalDateTime.now().toString().split("\\.")[0].replace("T", " "));
        dto.setDirCount(Util.getDirsCount(path));
        dto.setFileCount(Util.getFilesCount(path));
        dto.setSize(Util.convertFileSize(path));
        return dto;
    }

    @SneakyThrows
    private List<File> getFilesForDirectory(Path path, Directory dir) {
        List<File> result = new ArrayList<>();
        List<Path> paths = Files.list(path).collect(Collectors.toList());
        for (Path p: paths) {
            result.add(FileMapper.INSTANCE.toEntity(fileService.createDto(p, dir)));
        }
        return result;
    }
}
