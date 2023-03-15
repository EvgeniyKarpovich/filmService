package by.karpovich.filmService.jpa.repository;

import by.karpovich.filmService.jpa.model.FilmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepositoryForMapper extends JpaRepository<FilmModel, Long> {

    List<FilmModel> findByActorsId(Long actorId);

    List<FilmModel> findByDirectorsId(Long id);

    List<FilmModel> findByGenresId(Long id);
}
