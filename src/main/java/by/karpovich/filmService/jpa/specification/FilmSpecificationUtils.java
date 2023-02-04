package by.karpovich.filmService.jpa.specification;

import by.karpovich.filmService.api.dto.criteriaDto.FilmDtoCriteria;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecificationUtils {

    public static Specification<FilmModel> findByGenre(String genre) {
        return (root, query, criteriaBuilder) -> {
            Join<GenreModel, FilmModel> filmGenre = root.join("genres");
            return criteriaBuilder.equal(filmGenre.get("name"), genre);
        };
    }

    public static Specification<FilmModel> findByRating(Double rating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("ratingIMDB"), rating);

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
        return filmSpecification;
    }
}
