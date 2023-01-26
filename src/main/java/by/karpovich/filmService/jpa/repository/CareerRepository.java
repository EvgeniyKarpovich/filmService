package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.CareerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<CareerModel, Long>, PagingAndSortingRepository<CareerModel, Long> {

    Optional<CareerModel> findByName(String name);

    Page<CareerModel> findAll(Pageable pageable);
}
