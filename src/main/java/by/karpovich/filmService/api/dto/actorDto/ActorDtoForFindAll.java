package by.karpovich.filmService.api.dto.actorDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDtoForFindAll {

    private String name;

    private byte[] avatar;
}
