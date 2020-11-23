package com.example.fuelisticv2client.fuelisticv2client.UI.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment
{

    @BindView(R.id.diesel_today)
    TextView diesel_today;

    @BindView(R.id.total_order)
    TextView total_order;

    private HomeViewModel homeViewModel;
    Unbinder unbinder;
    LineChart lineChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder= ButterKnife.bind(this,root);
        diesel_today.setText("Rs " + String.valueOf(Common.diesel_price));
        total_order.setText(Common.currentUser.getTotalOrderQuantity() + " LT.");

        lineChart = root.findViewById(R.id.linechart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        LineDataSet lineDataSet1 = new LineDataSet(getVals(),"Prices");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(R.color.red);

        ArrayList<ILineDataSet> arr = new ArrayList<>();
        arr.add(lineDataSet1);

        //LineData data = new LineData(arr);
        lineChart.setData(new LineData(arr));
        lineChart.invalidate();

        lineChart.setVisibleXRangeMaximum(65f);


        return root;
    }

    private ArrayList<Entry> getVals()
    {
        ArrayList<Entry> vals = new ArrayList<>();
        vals.add(new Entry(1,Float.parseFloat("77.4")));
        vals.add(new Entry(2,Float.parseFloat("77.4")));
        vals.add(new Entry(3,Float.parseFloat("77.4")));
        vals.add(new Entry(4,Float.parseFloat("77.4")));
        vals.add(new Entry(5,Float.parseFloat("77")));
        vals.add(new Entry(6,Float.parseFloat("76.4")));
        vals.add(new Entry(7,Float.parseFloat("75.13")));
        vals.add(new Entry(8,Float.parseFloat("74.48")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        vals.add(new Entry(8,Float.parseFloat("74.32")));
        return vals;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause()   {
        super.onPause();
    }
}