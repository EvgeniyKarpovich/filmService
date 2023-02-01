package by.karpovich.filmService.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final String PATTERN_FORMAT = "dd MMMM yyyy";

    public static String mapStringFromInstant(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        Instant date = Instant.parse(instant.toString());

        return formatter.format(date);
    }

    public static Instant mapInstantFromString(String date) {
        String fullDate = date + "T00:00:00.000+00:00";

        return Instant.parse(fullDate);
    }

}
