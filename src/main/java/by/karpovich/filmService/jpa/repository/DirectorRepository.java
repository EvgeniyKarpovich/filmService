package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.DirectorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorModel, Long>, PagingAndSortingRepository<DirectorModel, Long> {

    Optional<DirectorModel> findByName(String name);

    Page<DirectorModel> findAll(Pageable pageable);
}

