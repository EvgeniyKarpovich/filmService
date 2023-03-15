package by.karpovich.filmService.api.dto.directorDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDtoForFindAll {

    private String name;

    private byte[] avatar;
}
