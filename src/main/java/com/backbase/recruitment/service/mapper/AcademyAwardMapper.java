package com.backbase.recruitment.service.mapper;

import com.backbase.recruitment.domain.AcademyAward;
import com.backbase.recruitment.rest.dtos.AcademyAwardDTO;
import org.mapstruct.*;

import java.util.Collection;

@Mapper(
        config = GlobalMapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AcademyAwardMapper extends EntityMapper<AcademyAwardDTO, AcademyAward> {
    @Override
    @Mapping(target = "id", ignore = true)
    AcademyAward toEntity(AcademyAwardDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    AcademyAwardDTO toDto(AcademyAward entity);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Override
    Collection<AcademyAwardDTO> toDto(Collection<AcademyAward> entityList);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Override
    Collection<AcademyAward> toEntity(Collection<AcademyAwardDTO> dtoList);
}