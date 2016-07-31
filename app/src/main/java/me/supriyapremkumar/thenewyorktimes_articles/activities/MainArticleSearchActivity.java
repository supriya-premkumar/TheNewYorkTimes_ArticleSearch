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
    ArrayList<Object> articles = new ArrayList<>();
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, articles);
    public String cachedQueryString = "";
    //public int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_article_search_activity);

        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);


        //LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rvArticles.setLayoutManager(gridLayoutManager);

//        RecyclerView.ItemDecoration itemDecorationHorixontal = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
//        RecyclerView.ItemDecoration itemDecorationVertical = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
//        rvArticles.addItemDecoration(itemDecorationHorixontal);
//        rvArticles.addItemDecoration(itemDecorationVertical);
        // Add the scroll listener
        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //page += 1;
                customLoadMoreDataFromApi(page);

            }
        });
        rvArticles.setAdapter(adapter);



    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int page) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        //  --> Deserialize API response and then construct new objects to append to the adapter
        //  --> Notify the adapter of the changes
        Log.d("MU HU Ha ha ha", "Page: " + String.valueOf(page));
        //Toast.makeText(MainArticleSearchActivity.this, "Loading page " +  String.valueOf(offset), Toast.LENGTH_SHORT).show();
        //int curSize = adapter.getItemCount();
        fetchArticles(cachedQueryString, page);
        //adapter.notifyItemRangeInserted(curSize,articles.size() - 1 );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_search_menu, menu);
        MenuItem searchArticle = menu.findItem(R.id.action_search);
        searchArticle.expandActionView();

        MenuItem filterIcon = menu.findItem(R.id.filter_results);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchArticle);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cachedQueryString = query;
                articles.clear();
                fetchArticles(query, 0);
                //adapter.notifyDataSetChanged();
                searchView.clearFocus();

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


    private void fetchArticles(String query, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "f595b0766fd9409c9f51626983143c08");
        params.put("page", page);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //articles.clear();
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
