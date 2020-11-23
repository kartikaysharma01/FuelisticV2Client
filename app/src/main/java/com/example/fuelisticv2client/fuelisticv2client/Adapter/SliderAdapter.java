package com.example.fuelisticv2client.fuelisticv2client.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fuelisticv2client.R;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;

    }
    //arr
    public int[] slide_images={
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3
    };
    public int[] slide_headings={
           R.string.h1,
            R.string.h2,
            R.string.h3
    };
    public String[] slide_desc={

            "Get fuel at your doorsteps ",
            "With order statistics",
            "click to continue"
    };


    @Override
    public int getCount() {

        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==(ConstraintLayout) o;
    }

    @SuppressLint("ServiceCast")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.slider, container , false);
        ImageView slideImageView= (ImageView) view.findViewById(R.id.p1);
        TextView slideHeading= (TextView) view.findViewById(R.id.slider_heading);
        TextView slideDescription= (TextView) view.findViewById(R.id.slider_desc);

        slideImageView.setImageResource(slide_images [position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
