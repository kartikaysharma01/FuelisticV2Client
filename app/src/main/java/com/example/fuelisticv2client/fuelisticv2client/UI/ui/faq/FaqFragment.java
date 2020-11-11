package com.example.fuelisticv2client.fuelisticv2client.UI.ui.faq;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelisticv2client.R;

import java.util.ArrayList;


public class FaqFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private faqAdapter mAdapter;

    private final ArrayList<String> qList = new ArrayList<>();
    private final ArrayList<String> aList = new ArrayList<>();

    //private FaqViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        FAQresources obj = new FAQresources();
        qList.add(obj.q1);
        qList.add(obj.q2);
        qList.add(obj.q3);
        qList.add(obj.q4);
        qList.add(obj.ques5);
        qList.add(obj.q6);
        qList.add(obj.q7);
        qList.add(obj.q8);
        qList.add(obj.q9);

        aList.add(obj.an1);
        aList.add(obj.an2);
        aList.add(obj.an3);
        aList.add(obj.an4);
        aList.add(obj.an5);
        aList.add(obj.an6);
        aList.add(obj.an7);
        aList.add(obj.an8);
        aList.add(obj.an9);

        Context ctx = view.getContext();
        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new faqAdapter(ctx, qList, aList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));


        /*
        if (view instanceof RecyclerView)
        {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
        }

         */
        return view;
    }
}