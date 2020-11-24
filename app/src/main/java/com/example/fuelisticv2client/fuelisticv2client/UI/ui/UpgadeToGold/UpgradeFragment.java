package com.example.fuelisticv2client.fuelisticv2client.UI.ui.UpgadeToGold;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.UI.HomeActivity;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UpgradeFragment extends Fragment {

    private UpgradeViewModel mViewModel;
    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.btn_upgrade)
    Button btn_upgrade;

//    @BindView(R.id.nav_view)
//    NavigationView navigationView;

    public static UpgradeFragment newInstance() {
        return new UpgradeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_upgrade, container, false);

        unbinder = ButterKnife.bind(this, root);

        return root;
    }



    @OnClick(R.id.btn_upgrade)
    public void upgrade(){
        Toast.makeText(getContext(), "Payment module to be implemented soon!!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Upgrade to Gold")
                .setMessage("Are you sure you want to become a gold member?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                HomeActivity.hideUpgrade();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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