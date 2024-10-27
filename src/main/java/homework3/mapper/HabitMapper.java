package homework3.mapper;

import homework3.dto.HabitDto;
import homework3.entity.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HabitMapper {
    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "frequency", target = "frequency")
    HabitDto toDto(Habit habit);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "frequency", target = "frequency")
    Habit toEntity(HabitDto habitDto);
}