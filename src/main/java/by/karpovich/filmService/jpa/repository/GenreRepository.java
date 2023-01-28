package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.GenreModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, Long>, PagingAndSortingRepository<GenreModel, Long> {

    Optional<GenreModel> findByName(String name);

    Page<GenreModel> findAll(Pageable pageable);
}
