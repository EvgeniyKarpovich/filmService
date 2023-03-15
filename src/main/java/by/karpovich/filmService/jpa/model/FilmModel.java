package by.karpovich.filmService.jpa.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "films")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = {"directors", "genres", "actors"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_movie_imdb", nullable = false)
    private String nameFilmFromImdb;

    @Column(name = "poster")
    private String poster;

    @Column(name = "rating_IMDB", nullable = false)
    private String ratingIMDB;

    @Column(name = "tag_line", nullable = false)
    private String tagline;

    @Column(name = "release_date", nullable = false)
    private Instant releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryModel country;

    @ManyToMany
    @JoinTable(name = "director_film",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"))
    private List<DirectorModel> directors;

    @ManyToMany
    @JoinTable(name = "GENRE_FILM",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<GenreModel> genres;

    @Column(name = "age_Limit")
    private String ageLimit;

    @Column(name = "duration_in_minutes", nullable = false)
    private int durationInMinutes;

    @ManyToMany
    @JoinTable(name = "actor_film",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"))
    private List<ActorModel> actors = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;
}
