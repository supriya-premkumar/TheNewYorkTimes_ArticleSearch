package me.supriyapremkumar.thenewyorktimes_articles.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by supriya on 7/31/16.
 */
public class TxtArticleModel implements Serializable {

    String webUrl;
    String articleTitle;
    String articleSnippet;

    public String getArticleTitle(){
        return articleTitle;
    }

    public String getArticleSnippet(){
        return articleSnippet;
    }

    public String getWebUrl(){
        return webUrl;
    }
    public TxtArticleModel(JSONObject article) {
        try{
            this.webUrl = article.getString("web_url");
            this.articleSnippet = article.getString("snippet");
            this.articleTitle = article.getJSONObject("headline").getString("main");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
