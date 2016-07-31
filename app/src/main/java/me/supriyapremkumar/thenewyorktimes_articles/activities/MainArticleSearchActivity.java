package me.supriyapremkumar.thenewyorktimes_articles.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.supriyapremkumar.thenewyorktimes_articles.R;
import me.supriyapremkumar.thenewyorktimes_articles.RecyclerViewAdapter;
import me.supriyapremkumar.thenewyorktimes_articles.model.Article;

public class MainArticleSearchActivity extends AppCompatActivity {
    EditText etQuery;
    Button btnSearch;
    //RecyclerView rvArticles;

    ArrayList<Object> articles = new ArrayList<>();
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, articles);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_article_search_activity);

        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(gridLayoutManager);
        rvArticles.setAdapter(adapter);


        //etQuery = (EditText) findViewById(R.id.etquery);
        //btnSearch = (Button) findViewById(R.id.btnSearch);

//
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fetchArticles(etQuery.getText().toString());
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_search_menu, menu);
        MenuItem searchArticle = menu.findItem(R.id.action_search);
        searchArticle.expandActionView();
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchArticle);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                fetchArticles(query);
                // Fire off a fragment with trending articles.

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void fetchArticles(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "f595b0766fd9409c9f51626983143c08");
        params.put("page", 0);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.clear();
                    articles.addAll(Article.fromJsonArray(articleJsonResults));
                    adapter.notifyDataSetChanged();
                    Log.d("onSuccess: ", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }





}
