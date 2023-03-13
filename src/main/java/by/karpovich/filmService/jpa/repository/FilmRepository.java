package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.FilmModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<FilmModel, Long>,
        PagingAndSortingRepository<FilmModel, Long>,
        JpaSpecificationExecutor<FilmModel> {

    Optional<FilmModel> findByNameAndDescription(String name, String description);

    Page<FilmModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<FilmModel> findAll(Pageable pageable);

//    List<FilmModel> findAllByOrderByReleaseDateDesc();

    Page<FilmModel> findByGenresId(Long genreId, Pageable pageable);

    Page<FilmModel> findByActorsId(Long actorId, Pageable pageable);

    List<FilmModel> findByActorsId(Long actorId);

    List<FilmModel> findByDirectorsId(Long id);

    List<FilmModel> findByGenresId(Long id);

    Page<FilmModel> findByCountryId(Long countryId, Pageable pageable);

    Page<FilmModel> findByDirectorsId(Long countryId, Pageable pageable);
}
