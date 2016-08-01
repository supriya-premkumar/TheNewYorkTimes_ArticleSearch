package me.supriyapremkumar.thenewyorktimes_articles.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.supriyapremkumar.thenewyorktimes_articles.R;
import me.supriyapremkumar.thenewyorktimes_articles.models.NewsDeskModel;

/**
 * Created by supriya on 7/31/16.
 */
public class NewsDeskRecyclerViewAdapter extends
        RecyclerView.Adapter<NewsDeskRecyclerViewAdapter.ViewHolder> {

    public static List<String> selectedNewsDesk = new ArrayList<>();
    //public static boolean toggleState = false;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView newsDeskView;
        public boolean toggleState = false;

        public ViewHolder(View itemView) {

            super(itemView);

            this.newsDeskView = (TextView) itemView.findViewById(R.id.news_desk);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            toggleState = toggleSelection(view, position, toggleState);

        }
    }


    private static List<NewsDeskModel> mNewsDesk;

    private Context mContext;


    public NewsDeskRecyclerViewAdapter(Context context, List<NewsDeskModel> newsDesk) {
        mNewsDesk = newsDesk;
        mContext = context;
    }

    @Override
    public NewsDeskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View newsDeskView = inflater.inflate(R.layout.recycler_item_news_desk, parent, false);

        ViewHolder viewHolder = new ViewHolder(newsDeskView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsDeskRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        NewsDeskModel newsDesk = mNewsDesk.get(position);
        final TextView textView = viewHolder.newsDeskView;
        textView.setText(newsDesk.getNewsDeskName());

    }

    @Override
    public int getItemCount() {
        return mNewsDesk.size();
    }


    private Context getmContext() {
        return mContext;
    }

    private static boolean toggleSelection(View v, int position, boolean toggleState) {
        String news_desk = mNewsDesk.get(position).getNewsDeskName();

        if (toggleState == false) {
            selectedNewsDesk.add(news_desk);
            v.setBackgroundColor(Color.parseColor("#aa2200"));
            return true;
        }
        else {
            selectedNewsDesk.remove(news_desk);

            return false;
        }

    }
}