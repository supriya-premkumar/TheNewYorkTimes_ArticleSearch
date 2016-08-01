package me.supriyapremkumar.thenewyorktimes_articles.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by supriya on 7/26/16.
 */
public class ArticleModel implements Serializable{
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
                    ImgArticleModel imgArticleModel = new ImgArticleModel(article, thumbnail);
                    webUrl = imgArticleModel.getWebUrl();
                    results.add(imgArticleModel);
                }
                else {
                    TxtArticleModel txtArticleModel = new TxtArticleModel(article);
                    webUrl = txtArticleModel.getWebUrl();
                    results.add(txtArticleModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;


    }

}
