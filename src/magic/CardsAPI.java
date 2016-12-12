package magic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;

public class CardsAPI {
    private static final String BASE_URL = "http://api.magicthegathering.io/v1/cards";

    static ArrayList<Card> getAllCards() throws MalformedURLException {
        URL url = new URL(BASE_URL);
        String urlBona = url.toString();
        return doCall(urlBona);
    }

    //Obtenir les cartes per raresa o per color
   /* static ArrayList<Card> getCardsByRarityAndOrColor (String color , String rarity) {

        Uri builtUri;

        if (!color.equalsIgnoreCase("None") && !rarity.equalsIgnoreCase("None")) {
            builtUri = Uri.parse(BASE_URL)
                    .buildUpon()
                    .appendQueryParameter("colors" , color)
                    .appendQueryParameter("rarity" , rarity)
                    .build();
        } else if (!color.equalsIgnoreCase("None")) {
            builtUri = Uri.parse(BASE_URL)
                    .buildUpon()
                    .appendQueryParameter("colors" , color)
                    .build();
        } else {
            builtUri = Uri.parse(BASE_URL)
                    .buildUpon()
                    .appendQueryParameter("rarity" , rarity)
                    .build();
        }

        String url = builtUri.toString();
        return doCall(url);
    }*/


    private static ArrayList<Card> doCall(String url) {

        try {
            String JsonResponse = HttpUtils.get(url);
            return processJson(JsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Card> processJson(String jsonResponse) {

        ArrayList<Card> cards = new ArrayList<>();

        try {
            JSONObject data = new JSONObject(jsonResponse);
            JSONArray jsonCards = data.getJSONArray("cards");

            for (int i = 0 ; i < jsonCards.length() ; i++) {
                Card card = new Card();
                JSONObject object = jsonCards.getJSONObject(i);

                if (object.has("name")) {
                    card.setName(object.getString("name"));
                } else {
                    card.setName("");
                }
                if (object.has("rarity")) {
                    card.setRarity(object.getString("rarity"));
                } else {
                    card.setRarity("");
                }
                if (object.has("colors")) {
                    String colors = "";

                    for (int j = 0 ; j < object.getJSONArray("colors").length() ; j++) {
                        if (j != object.getJSONArray("colors").length() - 1) {
                            colors += object.getJSONArray("colors").get(j).toString() + ", ";
                        } else {
                            colors += object.getJSONArray("colors").get(j).toString();
                        }
                    }

                    card.setColor(colors);
                }
                if (object.has("text")) {
                    card.setText(object.getString("text"));
                } else {
                    card.setText("");
                }
                if (object.has("type")) {
                    card.setType(object.getString("type"));
                } else {
                    card.setType("");
                }
                if (object.has("imageUrl")) {
                    card.setImageUrl(object.getString("imageUrl"));
                } else {
                    card.setImageUrl("");
                }

                cards.add(card);
            }

        } catch (JSONException e) {
            e.getMessage();
        }

        return cards;
    }

}
