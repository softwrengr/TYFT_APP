package com.squaresdevelopers.tyft.views.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.savvi.rangedatepicker.CalendarPickerView;
import com.squaresdevelopers.tyft.R;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetTimingFragment extends Fragment {
    View view;
    int mHour, mMinute, mSecond;

    @BindView(R.id.calendar_picker)
    CalendarPickerView calendar;
    @BindView(R.id.ll_start_time)
    LinearLayout layoutStartTime;
    @BindView(R.id.ll_end_time)
    LinearLayout layoutEndTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_done)
    TextView tvDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_set_timing, container, false);
        ButterKnife.bind(this,view);


        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 20);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, 0);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);

        calendar.deactivateDates(list);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault());

        calendar.init(lastYear.getTime(), nextYear.getTime(), simpleDateFormat)
                .withSelectedDate(new Date())
                .withDeactivateDates(list);


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                // showTimeDialog();
                formattedDate(date);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        layoutStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourPicker(true);
            }
        });

        layoutEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourPicker(false);
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void formattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH, month);
        String month_name = month_date.format(calendar.getTime());

        String currentDate = String.valueOf(day) + "-" + String.valueOf(month+1) + "-" + String.valueOf(year);
        Toast.makeText(getActivity(), currentDate, Toast.LENGTH_SHORT).show();

    }

    private void showHourPicker(boolean check) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            Time tme = new Time(view.getHour(),view.getHour(),0);
                            Format formatter;
                            formatter = new SimpleDateFormat("h:mm a");

                            if(check){
                                tvStartTime.setText(formatter.format(tme));
                            }
                            else {
                                tvEndTime.setText(formatter.format(tme));
                            }

                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
