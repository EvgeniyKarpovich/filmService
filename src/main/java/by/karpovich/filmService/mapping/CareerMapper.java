package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.CareerDto;
import by.karpovich.filmService.jpa.model.CareerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CareerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    CareerModel mapModelFromDto(CareerDto careerDto);

    CareerDto mapDtoFromModel(CareerModel careerModel);

    List<CareerModel> mapListModelFromListDto(List<CareerDto> careerDtoList);

    List<CareerDto> mapListDtoFromListModel(List<CareerModel> careerModelList);
}
