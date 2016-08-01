package me.supriyapremkumar.thenewyorktimes_articles.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.supriyapremkumar.thenewyorktimes_articles.R;
import me.supriyapremkumar.thenewyorktimes_articles.adapters.ArticleRecyclerViewAdapter;
import me.supriyapremkumar.thenewyorktimes_articles.fragments.FilterSettingsFragment;
import me.supriyapremkumar.thenewyorktimes_articles.listeners.EndlessRecyclerViewScrollListener;
import me.supriyapremkumar.thenewyorktimes_articles.models.ArticleModel;

public class MainArticleSearchActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    ArrayList<Object> articles = new ArrayList<>();
    ArticleRecyclerViewAdapter adapter = new ArticleRecyclerViewAdapter(this, articles);
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
        rvArticles.setLayoutManager(gridLayoutManager);

        // Add the scroll listener
        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);

            }
        });
        rvArticles.setAdapter(adapter);


    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int page) {
        fetchArticles(cachedQueryString, page, false, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_search_menu, menu);
        MenuItem searchArticle = menu.findItem(R.id.action_search);
        searchArticle.expandActionView();

        MenuItem filterIcon = menu.findItem(R.id.filter_results);

        filterIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                displayFilterFragment();
                return true;
            }
        });


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchArticle);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cachedQueryString = query;
                articles.clear();
                fetchArticles(query, 0, false, false);
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


    private void fetchArticles(String query, int page, boolean filterParams, final boolean invalidate) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "f595b0766fd9409c9f51626983143c08");
        params.put("page", page);
        params.put("q", query);
        if(filterParams != false){
            params = generateParamsFromFilterSettings(params);
        }

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //articles.clear();
                    if(invalidate == true) {
                        articles.clear();
                    }
                    articles.addAll(ArticleModel.fromJsonArray(articleJsonResults));
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


    private void displayFilterFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FilterSettingsFragment filterSettingsFragment = FilterSettingsFragment.newInstance("Filter Results");
        filterSettingsFragment.show(fm, "filter_settings_fragment");
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        fetchArticles(cachedQueryString, 0, true, true);

    }

    public static class FilterParams {
        public static String beginDate;
        public static String endDate;
        public static List<String> newsDesk;
        public static String sortOrder = "newest";
    }

    private RequestParams generateParamsFromFilterSettings(RequestParams params) {

        if (FilterParams.beginDate != null) {
            String beginDate = DateFormatter(FilterParams.beginDate.replace(" ", ""));
            params.put("begin_date", beginDate);
        }

        if (FilterParams.endDate != null) {
            String endDate = DateFormatter(FilterParams.endDate.replace(" ", ""));
            params.put("begin_date", endDate);
        }

        params.put("sort", FilterParams.sortOrder);

        String newsDeskQuery = generateNewsDeskQuery(FilterParams.newsDesk);

        params.put("fq", newsDeskQuery);

        return params;
    }

    private String DateFormatter(String date){
        String formattedDate =new String();
        String[] dateComponents = date.split("/");
        String yyyy = dateComponents[2];
        String mm = new String();
        String dd = new String();
        if(Integer.valueOf(dateComponents[0]) < 10 ){
            mm = "0" + dateComponents[0];
        } else {
            mm = dateComponents[0];
        }

        if(Integer.valueOf(dateComponents[1]) < 10){
            dd = "0" + dateComponents[1];
        } else {
            dd = dateComponents[1];
        }
        formattedDate = yyyy + mm + dd;
        return formattedDate;
    }

    private String generateNewsDeskQuery(List<String> newsDesk){
        String out = "news_desk:(";
        for (String news_desk: newsDesk){
            out = out + "\"" + news_desk + "\" ";
        }
        out = out + ")";
        return out;
    }

}
