package by.karpovich.filmService.jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "films")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class FilmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating_IMDB", nullable = false)
    private Double ratingIMDB;

    @Column(name = "tag_line", nullable = false)
    private String tagline;

    @Column(name = "release_date", nullable = false)
    private Instant releaseDate;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryModel country;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "director_film",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"))
    private List<DirectorModel> directors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "genre_film",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<GenreModel> genres;

    @Column(name = "age_Limit")
    private String ageLimit;

    @Column(name = "duration_in_minutes", nullable = false)
    private int durationInMinutes;

    @ManyToMany(fetch = FetchType.EAGER)
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
