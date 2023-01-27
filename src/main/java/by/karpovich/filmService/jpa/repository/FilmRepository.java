package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.FilmModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<FilmModel, Long>, PagingAndSortingRepository<FilmModel, Long> {

    Optional<FilmModel> findByNameAndDescription(String name, String description);

    Page<FilmModel> findAll(Pageable pageable);
}
