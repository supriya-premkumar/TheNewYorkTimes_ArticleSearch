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

import me.supriyapremkumar.thenewyorktimes_articles.model.ImgArticle;
import me.supriyapremkumar.thenewyorktimes_articles.model.TxtArticle;

/**
 * Created by supriya on 7/29/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvSnippet;

        public ViewHolder1(View itemView) {
            super(itemView);

            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivImage1);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle1);
            tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet1);
        }

        public ImageView getivThumbnail() {
            return ivThumbnail;
        }

        public void setIvThumbnail(ImageView ivThumbnail) {
            this.ivThumbnail = ivThumbnail;
        }

        public TextView gettvTitle() {
            return tvTitle;
        }

        public void settvTitle(TextView tvTitle) {
            this.tvTitle = tvTitle;
        }

        public TextView getTvSnippet() {
            return tvSnippet;
        }

        public void setTvSnippet() {
            this.tvSnippet = tvSnippet;
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvSnippet;

        public ViewHolder2(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle2);
            tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet2);
        }

        public TextView gettvTitle() {
            return tvTitle;
        }

        public void settvTitle(TextView tvTitle) {
            this.tvTitle = tvTitle;
        }

        public TextView getTvSnippet() {
            return tvSnippet;
        }

        public void setTvSnippet(TextView tvSnippet) {
            this.tvSnippet = tvSnippet;
        }
    }


    private List<Object> mArticles;
    private final int IMG_ARTICLE = 1, TXT_ARTICLE = 2;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Object> articles) {
        mArticles = articles;
        mContext = context;
    }

    private Context getmContext() {
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case IMG_ARTICLE:
                View v1 = inflater.inflate(R.layout.recycler_item_article_type1, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case TXT_ARTICLE:
                View v2 = inflater.inflate(R.layout.recycler_item_article_type2, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
            //default:

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case IMG_ARTICLE:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                configureViewHolder(vh1, position);
                break;
            case TXT_ARTICLE:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder(vh2, position);
                break;
        }

    }

    private void configureViewHolder(ViewHolder1 vh1, int position) {
        ImgArticle imgArticle = (ImgArticle) mArticles.get(position);
        if (imgArticle != null) {
            vh1.gettvTitle().setText(imgArticle.getArticleTitle());
            vh1.getTvSnippet().setText(imgArticle.getArticleSnippet());
            vh1.getivThumbnail().setImageResource(0);

            String thumbnail = imgArticle.getThumbnail();
            if (!TextUtils.isEmpty(thumbnail)) {
                Picasso.with(getmContext()).load(thumbnail).into(vh1.getivThumbnail());
            }
        }
    }

    private void configureViewHolder(ViewHolder2 vh2, int position) {
        TxtArticle txtArticle = (TxtArticle) mArticles.get(position);
        if (txtArticle != null) {
            vh2.gettvTitle().setText(txtArticle.getArticleTitle());
            vh2.getTvSnippet().setText(txtArticle.getArticleSnippet());
        }
    }


    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mArticles.get(position) instanceof ImgArticle) {
            return IMG_ARTICLE;
        } else if (mArticles.get(position) instanceof TxtArticle) {
            return TXT_ARTICLE;
        }
        return -1;
    }
}
