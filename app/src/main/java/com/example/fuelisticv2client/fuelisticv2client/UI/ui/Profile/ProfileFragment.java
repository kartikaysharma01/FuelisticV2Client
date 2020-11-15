package com.example.fuelisticv2client.fuelisticv2client.UI.ui.Profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment
{
    TextView userName;
    //private ProfileViewModel mViewModel;

    private RecyclerView mRecycleview;
    private List<ItemAdapter> mList = new ArrayList<>();
    private ProfileListAdapter mAdapter;
    private Context ctx;

    public static ProfileFragment newInstance()
    {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        userName = userName.findViewById(R.id.userName);
        userName.setText(Common.currentUser.getUsername());

        ItemAdapter itemAdapter = new ItemAdapter();
        itemAdapter.setInputDesc("Full Name");
        itemAdapter.setInputValue(Common.currentUser.getFullName());
        itemAdapter.setImg(R.drawable.ic_baseline_perm_identity_24);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setInputDesc("Email");
        itemAdapter.setInputValue(Common.currentUser.getEmail());
        itemAdapter.setImg(R.drawable.ic_baseline_email_24);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setInputDesc("Phone Number");
        itemAdapter.setInputValue(Common.currentUser.getPhoneNo());
        itemAdapter.setImg(R.drawable.ic_baseline_phone_24);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setInputDesc("Address");
        itemAdapter.setInputValue(Common.currentUser.getAddress());
        itemAdapter.setImg(R.drawable.ic_baseline_home_work_24);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setInputDesc("DOB");
        itemAdapter.setInputValue(Common.currentUser.getDateOfBirth());
        itemAdapter.setImg(R.drawable.ic_baseline_date_range_24);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setInputDesc("Gender");
        itemAdapter.setInputValue(Common.currentUser.getGender());
        itemAdapter.setImg(R.drawable.ic_baseline_how_to_reg_24);
        mList.add(itemAdapter);

        ctx = view.getContext();
        mRecycleview = mRecycleview.findViewById(R.id.profileRecycler);
        mAdapter = new ProfileListAdapter(mList, ctx);
        mRecycleview.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}