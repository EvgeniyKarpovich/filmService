package by.karpovich.filmService.utils;

import lombok.Data;

@Data
public class IMDBInfoFilm {

    private String imDbId;

    private String title;

    private String fullTitle;

    private String type;

    private String year;

    private String imDb;

    private String metacritic;

    private String theMovieDb;

    private String rottenTomatoes;

    private String filmAffinity;

    private String errorMessage;
}