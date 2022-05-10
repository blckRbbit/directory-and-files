package com.gmail.leonidov.lex.porter.mappers;

import com.gmail.leonidov.lex.porter.dtos.DirectoryDto;
import com.gmail.leonidov.lex.porter.entities.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DirectoryMapper {
    DirectoryMapper INSTANCE = Mappers.getMapper(DirectoryMapper.class);
    DirectoryDto toDto(Directory directory);
    @Mapping(target = "id", ignore = true)
    Directory toEntity(DirectoryDto dto);
}
