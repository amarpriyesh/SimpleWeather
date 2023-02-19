package com.example.android.simpleweather;




import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Rest {


    public Rest() {
    }


    public List<String> openConnection() throws FileNotFoundException {
        String uri = "https://images-api.nasa.gov/search?q=clouds";
        List<String> hrefs = new ArrayList<>();

        try {
            // make the GET request
            URLConnection request = new URL(uri).openConnection();
            request.connect();
            InputStreamReader inputStreamReader = new InputStreamReader((InputStream) request.getContent());

            // map to GSON objects
            JsonElement root = new JsonParser().parse(inputStreamReader);

            // traverse the JSON data
            JsonArray items = root
                    .getAsJsonObject()
                    .get("collection").getAsJsonObject()
                    .get("items").getAsJsonArray();

            // flatten nested arrays
            JsonArray links = new JsonArray();

            for(JsonElement ob:items) {
                hrefs.add(ob.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject().get("center").getAsString());

            }
          /*  items.forEach(item -> links.addAll(item
                    .getAsJsonObject()
                    .get("links")
                    .getAsJsonArray()));

            // filter links with "href" properties
            links.forEach(link -> {
                JsonObject linkObject = link.getAsJsonObject();
                String relString = linkObject.get("rel").getAsString();
                if ("preview".equals(relString)) {
                    hrefs.add(linkObject.get("href").getAsString());
                }
            });*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hrefs;

    }

}
