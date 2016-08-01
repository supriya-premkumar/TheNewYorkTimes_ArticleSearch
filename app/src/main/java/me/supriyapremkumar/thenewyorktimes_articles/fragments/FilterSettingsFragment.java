package me.supriyapremkumar.thenewyorktimes_articles.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import me.supriyapremkumar.thenewyorktimes_articles.R;

/**
 * Created by supriya on 7/31/16.
 */
public class FilterSettingsFragment extends DialogFragment {

    private EditText beginDate;
    private EditText endDate;

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



        /**
         * Handlers for menu buttons.
         */
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        //saveTaskToDB();
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

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);

        super.onResume();
    }

    private void displayCalendarFragment(final View dateView){
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        int yy = calendar.get(java.util.Calendar.YEAR);
        int mm = calendar.get(java.util.Calendar.MONTH);
        int dd = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.valueOf(monthOfYear + 1) + " / "
                        + String.valueOf(dayOfMonth) + " / " + String.valueOf(year);
                ((EditText)dateView).setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();

    }

}
