package by.karpovich.filmService.api.dto.actorDto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDtoForFindAll {

    private String name;

    private byte[] avatar;
}
