package by.karpovich.filmService.api.dto.directorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDtoForFindAll {

    private String name;

    private byte[] avatar;
}
