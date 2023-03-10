package by.karpovich.filmService.jpa.specification;

import by.karpovich.filmService.api.dto.criteriaDto.FilmDtoCriteria;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class FilmSpecificationUtils {

    public static Specification<FilmModel> findByGenre(String genre) {
        return (root, query, criteriaBuilder) -> {
            Join<GenreModel, FilmModel> filmGenre = root.join("genres");
            return criteriaBuilder.equal(filmGenre.get("name"), genre);
        };
    }

    public static Specification<FilmModel> findByRating(String rating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("ratingIMDB"), rating);

    }

    public static Specification<FilmModel> findByDate(String date) {
        String startDate = date + "-01-01T00:00:00.000+00:00";
        String endDate = date + "-12-31T00:00:00.000+00:00";

        Instant parseStart = Instant.parse(startDate);
        Instant parseEnd = Instant.parse(endDate);
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("releaseDate"), parseStart, parseEnd);
    }

    public static Specification<FilmModel> defaultSpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), 0);
    }

    public static Specification<FilmModel> createFromCriteria(FilmDtoCriteria criteriaDto) {
        Specification<FilmModel> filmSpecification = defaultSpecification();

        if (criteriaDto.getNameGenre() != null) {
            filmSpecification = filmSpecification.and(findByGenre(criteriaDto.getNameGenre()));
        }
        if (criteriaDto.getRating() != null) {
            filmSpecification = filmSpecification.and(findByRating(criteriaDto.getRating()));
        }
        if (criteriaDto.getDate() != null) {
            filmSpecification = filmSpecification.and(findByDate(criteriaDto.getDate()));
        }
        return filmSpecification;
    }
}
