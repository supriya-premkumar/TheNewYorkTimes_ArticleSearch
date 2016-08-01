package me.supriyapremkumar.thenewyorktimes_articles.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

import me.supriyapremkumar.thenewyorktimes_articles.R;
import me.supriyapremkumar.thenewyorktimes_articles.activities.MainArticleSearchActivity;
import me.supriyapremkumar.thenewyorktimes_articles.adapters.NewsDeskRecyclerViewAdapter;
import me.supriyapremkumar.thenewyorktimes_articles.models.NewsDeskModel;

/**
 * Created by supriya on 7/31/16.
 */
public class FilterSettingsFragment extends DialogFragment {

    private EditText beginDate;
    private EditText endDate;
    NewsDeskRecyclerViewAdapter adapter;


    public FilterSettingsFragment() {

    }

    public static FilterSettingsFragment newInstance(String title) {
        FilterSettingsFragment frag = new FilterSettingsFragment();
        frag.setHasOptionsMenu(true);
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.filter_settings_fragment, container, false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) v.findViewById(R.id.filter_settings_toolbar);
        toolbar.inflateMenu(R.menu.filter_settings_menu);

        beginDate = (EditText) v.findViewById(R.id.begin_date);

        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCalendarFragment(view);
            }
        });

        endDate = (EditText) v.findViewById(R.id.end_date);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCalendarFragment(view);
            }
        });

        Switch sortSelector = (Switch) v.findViewById(R.id.switch_sort);
        sortSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MainArticleSearchActivity.FilterParams.sortOrder = "oldest";
            }
        });

        RecyclerView rvNewsDesk = (RecyclerView) v.findViewById(R.id.news_desk_selector);

        ArrayList<NewsDeskModel> newsDesk = NewsDeskModel.createNewsDeskModelList();
        adapter = new NewsDeskRecyclerViewAdapter(getContext(), newsDesk);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        rvNewsDesk.setLayoutManager(gridLayoutManager);
        gridLayoutManager.scrollToPosition(0);
        rvNewsDesk.setAdapter(adapter);


        /**
         * Handlers for menu buttons.
         */
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        saveFilters();
                        return true;
                    case R.id.clear:
                        dismiss();
                        return true;
                }
                return true;

            }
        });
        toolbar.setTitle("Filter Results");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        this.setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void displayCalendarFragment(final View dateView) {
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        int yy = calendar.get(java.util.Calendar.YEAR);
        int mm = calendar.get(java.util.Calendar.MONTH);
        int dd = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.valueOf(monthOfYear + 1) + " / "
                        + String.valueOf(dayOfMonth) + " / " + String.valueOf(year);
                ((EditText) dateView).setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    private void saveFilters() {

        MainArticleSearchActivity.FilterParams.beginDate = beginDate.getText().toString();
        MainArticleSearchActivity.FilterParams.endDate = endDate.getText().toString();
        MainArticleSearchActivity.FilterParams.newsDesk = NewsDeskRecyclerViewAdapter.selectedNewsDesk;
        dismiss();

    }
}
