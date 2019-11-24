package com.squaresdevelopers.tyft.views.truck.ui;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.locationDataModel.SellerLocationModel;
import com.squaresdevelopers.tyft.utilities.AlertUtils;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TruckTimingFragment extends Fragment {
    AlertDialog alertDialog;
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
    @BindView(R.id.tv_show_start)
    TextView tvShowStartTime;
    @BindView(R.id.tv_show_end)
    TextView tvShowEndTime;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.tv_back)
    TextView tvBack;

    private DatabaseReference databaseReference;
    private int sellerID;
    private String strDate, strStartTime, strEndTime, AM_PM;

    SimpleDateFormat formatter,currentDate;
    private boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_set_timing, container, false);
        ButterKnife.bind(this, view);
        FirebaseApp.initializeApp(getActivity());
        sellerID = GeneralUtils.getSellerId(getActivity());
        showUserTime();

        //setting time format
        formatter = new SimpleDateFormat("HH:mm:ss");
        currentDate = new SimpleDateFormat("dd-MM-yyyy");

        Date c = Calendar.getInstance().getTime();
        strDate = currentDate.format(c);
        showCalendar();


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
                if(validate()){
                    alertDialog = AlertUtils.createProgressDialog(getActivity());
                    alertDialog.show();
                    saveSellerTiming();
                }
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void showCalendar() {
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
    }

    private void formattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.set(Calendar.MONTH, month);

        strDate = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year);


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

                            if (hourOfDay >= 0 && hourOfDay < 12) {
                                AM_PM = "AM";
                            } else {
                                if (hourOfDay == 12) {
                                    AM_PM = "PM";
                                } else {
                                    hourOfDay = hourOfDay - 12;
                                    AM_PM = "PM";
                                }
                            }

                            Time tme = new Time(view.getHour(), view.getMinute(), 0);

                            if (check) {
                                strStartTime = formatter.format(tme);
                                tvStartTime.setText(strStartTime + " " + AM_PM);
                                tvShowStartTime.setText(strStartTime + " " + AM_PM);
                            } else {
                                strEndTime = formatter.format(tme);
                                tvEndTime.setText(strEndTime+  " " + AM_PM);
                                tvShowEndTime.setText(strEndTime+  " " + AM_PM);
                            }

                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void saveSellerTiming() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Seller_Location").child(String.valueOf(sellerID));
        SellerLocationModel model = new SellerLocationModel(
                String.valueOf(sellerID),
                strDate,
                strStartTime,
                strEndTime,
                GeneralUtils.getUserLatitude(getActivity()),
                GeneralUtils.getUserLongitude(getActivity()),
                GeneralUtils.getUserImage1(getActivity()),
                GeneralUtils.getUserImage2(getActivity()),
                GeneralUtils.getUser2Text(getActivity()));

        databaseReference.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                alertDialog.dismiss();
                GeneralUtils.putStringValueInEditor(getActivity(),"startTime",strStartTime + " " + AM_PM);
                GeneralUtils.putStringValueInEditor(getActivity(),"endTime",strEndTime + " " + AM_PM);
                showSweetDialog(SweetAlertDialog.SUCCESS_TYPE,"Your Time is updated successfully");
            }
        });
    }

    private void showUserTime(){
        tvShowStartTime.setText(GeneralUtils.getStartTime(getActivity()));
        tvShowEndTime.setText(GeneralUtils.getEndTime(getActivity()));
    }


    private void showSweetDialog(int dialodType,String message) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), dialodType);
        sweetAlertDialog.setConfirmText("Go to Home")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        getActivity().onBackPressed();
                    }
                })
                .setTitleText("Time Schedule")
                .setContentText(message)
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        getActivity().finish();
                        return false;
                    }
                });

        sweetAlertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
               sweetAlertDialog.dismissWithAnimation();
            }
        });
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    private boolean validate(){

        if(strDate==null || strDate.isEmpty()){
            Toast.makeText(getActivity(), "please select your date", Toast.LENGTH_SHORT).show();
            check = false;
        }
        else {
            check = true;
        }

        if(strStartTime==null || strStartTime.isEmpty()){
            Toast.makeText(getActivity(), "please select your start time", Toast.LENGTH_SHORT).show();
            check = false;
        }
        else {
            check = true;
        }

        if(strEndTime==null || strEndTime.isEmpty()){
            check = false;
            Toast.makeText(getActivity(), "please select your end time", Toast.LENGTH_SHORT).show();
        }
        else  {
            try {
                Date date_from = formatter.parse(strStartTime);
                Date date_to = formatter.parse(strEndTime);

                if(date_from.equals(date_to)){
                    check = false;
                    showSweetDialog(SweetAlertDialog.ERROR_TYPE,
                            "Your Start Time and End Time should not be similar");
                }
                else {
                    check = true;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return check;
    }

}
