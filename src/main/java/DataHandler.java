import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.TreeMap;



public class DataHandler {
    private final TreeMap<String, MtgCard> cardsByName = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void UpdateData() {
        try {
            //Get bulk data from scryfall
            Gson gson = new Gson();
            String resp = getJson("https://api.scryfall.com/bulk-data");
            JsonObject json = gson.fromJson(resp, JsonObject.class);
            JsonArray data = (JsonArray) json.get("data");

            //Find "oracle_cards" download link in bulk data
            String url = null;
            for(JsonElement j: data){
                if(j.getAsJsonObject().get("type").getAsString().equals("oracle_cards")){
                    url = j.getAsJsonObject().get("download_uri").getAsString();
                    break;
                }
            }
            if(url==null){
                System.out.println("Oracle Cards not found!");
                throw new Exception();
            }

            //Get updated oracle_cards data from scryfall, parse it to Card objects
            resp = getJson(url);
            MtgCard[] cards = gson.fromJson(resp, MtgCard[].class);

            //Put all cards in a map with case-insensitive keys, for later search by Name
            for(MtgCard card:cards){
                cardsByName.put(card.name(),card);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Sends HTTP request to given URL and returns response string
    private String getJson(String url) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        String resp = client.execute(request, response -> EntityUtils.toString(response.getEntity(), "UTF-8"));
        client.close();
        return resp;
    }

    //Downloads PNG image of a card with given name and saves it in a sub-folder given name
    public void downloadImage(String folder, String name) throws IOException {
        MtgCard card = cardsByName.get(name);
        if(card!=null){
            String url = card.image_uris().png();
            String cname = card.name().replaceAll("\\s*//\\s*","+");
            String file = "images/" + folder + "/" + cname + ".png";
            FileUtils.copyURLToFile(new URL(url), new File(file));
        }
        else{
            System.out.println("[!] Card not found: "+name);
        }
    }
}
