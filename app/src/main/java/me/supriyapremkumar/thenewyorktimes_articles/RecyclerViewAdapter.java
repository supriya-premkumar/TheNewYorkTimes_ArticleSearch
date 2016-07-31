package me.supriyapremkumar.thenewyorktimes_articles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.supriyapremkumar.thenewyorktimes_articles.model.Article;

/**
 * Created by supriya on 7/29/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    private List<Article> mArticles;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    private Context getmContext() {
        return mContext;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item_article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Article article = mArticles.get(position);

        TextView textView = viewHolder.tvTitle;
        textView.setText(article.getArticleTitle());

        ImageView imageView = viewHolder.ivThumbnail;
        imageView.setImageResource(0);

        String thumbnail = article.getThumbnail();

        if (!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getmContext()).load(thumbnail).into(imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}


//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//        //get the data item for position
//        Article article = this.getItem(position);
//
//        //check to see if existing view being reused
//        //not using a recycled view -> inflate the layout
//        if(convertView == null){
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.recycler_item_article, parent, false);
//        }
//        //find the image view
//        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivImage);
//
//        //clear out recycled image from convertView from last time
//        imageView.setImageResource(0);
//
//        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
//        tvTitle.setText(article.getArticleTitle());
//
//        //populate the thumbnail image
//        //remote download the image in the background
//
//        String thumbnail = article.getThumbnail();
//
//        if(!TextUtils.isEmpty(thumbnail)){
//            Picasso.with(getContext()).load(thumbnail).into(imageView);
//        }
//        return convertView;
//    }

