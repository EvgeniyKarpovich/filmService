package by.karpovich.filmService.jpa.model;

import by.karpovich.filmService.jpa.converters.CareerForModelConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directors")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = "films")
@AllArgsConstructor
@NoArgsConstructor
public class DirectorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "careers")
    @Convert(converter = CareerForModelConverter.class)
    private List<Career> careers = new ArrayList<>();

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryModel country;

    @ManyToMany
    @JoinTable(name = "director_film",
            joinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    private List<FilmModel> films = new ArrayList<>();

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;
}
