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
@Table(name = "genres")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = "films")
@AllArgsConstructor
@NoArgsConstructor
public class GenreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "GENRE_FILM",
            joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    private List<FilmModel> films = new ArrayList<>();

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;

}
