package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.FilmModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<FilmModel, Long>,
        PagingAndSortingRepository<FilmModel, Long>,
        JpaSpecificationExecutor<FilmModel> {

    Optional<FilmModel> findByNameAndDescription(String name, String description);

    Page<FilmModel> findAll(Pageable pageable);

    Page<FilmModel> findByGenresId(Long genreId, Pageable pageable);

    Page<FilmModel> findByActorsId(Long actorId, Pageable pageable);

    Page<FilmModel> findByCountryId(Long countryId, Pageable pageable);

    Page<FilmModel> findByDirectorsId(Long countryId, Pageable pageable);

    Page<FilmModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //    @Query(value = "SELECT f FROM Films f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY f.rating_IMDB ASC", nativeQuery = true)
//    List<FilmModel> getByNameLikeCaseInsensitive(String name);


}
