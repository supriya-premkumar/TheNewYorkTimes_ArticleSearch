package me.supriyapremkumar.thenewyorktimes_articles.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by supriya on 7/31/16.
 */
public class ImgArticleModel implements Serializable {
    String webUrl;
    String articleTitle;
    String articleSnippet;
    String thumbnail;

    public String getWebUrl() {
        return webUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getArticleSnippet(){
        return articleSnippet;
    }
    public ImgArticleModel(JSONObject article, String thumbnail) {
        this.thumbnail = thumbnail;

        try{
            this.webUrl = article.getString("web_url");
            this.articleSnippet = article.getString("snippet");
            this.articleTitle = article.getJSONObject("headline").getString("main");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
