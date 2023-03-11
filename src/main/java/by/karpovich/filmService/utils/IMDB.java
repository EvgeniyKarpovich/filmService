package by.karpovich.filmService.utils;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IMDB {
    private static final String MY_KEY = "k_wdh59dx2";

    public static String getRatingIMDB(String nameFromIMDB) {
        Response response = null;
        String responseJson = "";
        String imDb = "";

        OkHttpClient client = new OkHttpClient();

        String url = "https://imdb-api.com/en/API/Ratings/" + MY_KEY + "/" + nameFromIMDB;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                responseJson = response.body().string();

                Gson g = new Gson();
                IMDBInfoFilm imdbInfoFilm = g.fromJson(responseJson, IMDBInfoFilm.class);

                imDb = imdbInfoFilm.getImDb();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imDb;
    }

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
