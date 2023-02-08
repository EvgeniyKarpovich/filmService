package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.ActorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<ActorModel, Long>, PagingAndSortingRepository<ActorModel, Long> {

    Optional<ActorModel> findByName(String name);

    Page<ActorModel> findAll(Pageable pageable);

    Page<ActorModel> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
