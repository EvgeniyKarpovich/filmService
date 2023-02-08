package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.countryDto.CountryDto;
import by.karpovich.filmService.jpa.model.CountryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CountryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    CountryModel mapModelFromDto(CountryDto countryDto);

    CountryDto mapDtoFromModel(CountryModel country);

    List<CountryModel> mapListModelFromListDto(List<CountryDto> countryDtoList);

    List<CountryDto> mapListDtoFromListModel(List<CountryModel> countries);
}
