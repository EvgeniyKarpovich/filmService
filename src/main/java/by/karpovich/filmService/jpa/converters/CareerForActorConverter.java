package by.karpovich.filmService.jpa.converters;

import by.karpovich.filmService.jpa.model.Career;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Converter
public class CareerForActorConverter implements AttributeConverter<List<Career>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Career> careers) {
        if (careers.isEmpty()) {
            return null;
        }
        return careers.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<Career> convertToEntityAttribute(String career) {
        if (career != null) {
            String[] strings = career.split(SEPARATOR);
            return Arrays.stream(strings)
                    .map(Career::valueOf)
                    .collect(toList());
        } else {
            return null;
        }
    }
}
