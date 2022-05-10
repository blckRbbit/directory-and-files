package com.gmail.leonidov.lex.porter.mappers;

import com.gmail.leonidov.lex.porter.dtos.FileDto;
import com.gmail.leonidov.lex.porter.entities.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);
    FileDto toDto(File file);
    @Mapping(target = "id", ignore = true)
    File toEntity(FileDto dto);
}
