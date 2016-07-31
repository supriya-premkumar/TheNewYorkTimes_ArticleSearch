package me.supriyapremkumar.thenewyorktimes_articles.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by supriya on 7/26/16.
 */
public class Article implements Serializable{
    public static String webUrl;

    public static String getWebUrl(){
        return webUrl;
    }
    public static ArrayList<Object> fromJsonArray(JSONArray array) {
        ArrayList<Object> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                JSONObject article = array.getJSONObject(x);
                JSONArray multimedia = article.getJSONArray("multimedia");
                if (multimedia.length() > 0) {
                    JSONObject multimediaJson = multimedia.getJSONObject(0);
                    String thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
                    ImgArticle imgArticle = new ImgArticle(article, thumbnail);
                    webUrl = imgArticle.getWebUrl();
                    results.add(imgArticle);
                }
                else {
                    TxtArticle txtArticle = new TxtArticle(article);
                    webUrl = txtArticle.getWebUrl();
                    results.add(txtArticle);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;


    }

}
