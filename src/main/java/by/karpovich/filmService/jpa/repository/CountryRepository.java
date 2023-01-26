package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.CountryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryModel, Long>, PagingAndSortingRepository<CountryModel, Long> {

    Optional<CountryModel> findByName(String name);

    Page<CountryModel> findAll(Pageable pageable);
}
