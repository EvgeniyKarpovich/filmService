package by.karpovich.filmService.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IMDB {
    private static final String MY_KEY = "k_wdh59dx2";

    public static String getRating(String nameFromIMDB) {
        HttpURLConnection connection = null;
        String ratings = "";
        try {
            URL url = new URL("https://imdb-api.com/en/API/Ratings/" + MY_KEY + "/" + nameFromIMDB);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder responce = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                responce.append(line);
                responce.append("\r");
            }
            reader.close();
            String result = responce.toString();
            System.out.print(result);

            JSONObject object = new JSONObject(result);

            ratings = object.getString("imDb");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ratings;
    }
}
