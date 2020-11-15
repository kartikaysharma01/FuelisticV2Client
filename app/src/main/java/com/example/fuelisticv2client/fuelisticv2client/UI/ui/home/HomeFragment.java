package com.example.fuelisticv2client.fuelisticv2client.UI.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {

    @BindView(R.id.diesel_today)
    TextView diesel_today;

    @BindView(R.id.total_order)
    TextView total_order;

    private HomeViewModel homeViewModel;
    Unbinder unbinder;
//    @BindView(R.id.chart)
//    LineChart chart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder= ButterKnife.bind(this,root);
        diesel_today.setText("Rs " + String.valueOf(Common.diesel_price));
        total_order.setText(Common.currentUser.getTotalOrderQuantity() + " LT.");

        return root;
    }


//    private void getDataSet() {
//
//        int[] dataObjects = {50, 40, 34, 7-};
//        List<Entry> entries = new ArrayList<Entry>();
//        for (int data : dataObjects) {
//            entries.add(new Entry(data.getValueX(), data.getValueY()));
//        }
//        ArrayList dataSets = null;
//
//        ArrayList valueSet1 = new ArrayList();
//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
//        valueSet1.add(v1e4);
//        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
//        valueSet1.add(v1e5);
//        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
//        valueSet1.add(v1e6);
//
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
//        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        dataSets = new ArrayList();
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
//        return dataSets;
//
//
//        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
//        dataSet.setColor();
//        dataSet.setValueTextColor(...); // styli\
//
//        LineData data = new LineData(dataSet);
//        chart.setData(data);
//        chart.invalidate();
//        chart.animateXY(2000, 2000);
//    }
//
//    private ArrayList getXAxisValues() {
//        ArrayList xAxis = new ArrayList();
//        xAxis.add("JAN");
//        xAxis.add("FEB");
//        xAxis.add("MAR");
//        xAxis.add("APR");
//        xAxis.add("MAY");
//        xAxis.add("JUN");
//        return xAxis;
//    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}