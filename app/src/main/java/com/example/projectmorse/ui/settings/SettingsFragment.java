package com.example.projectmorse.ui.settings;

import android.os.Bundle;
import android.os.Vibrator;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.content.Context;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectmorse.GlobalVarD;
import com.example.projectmorse.GlobalVars;
import com.example.projectmorse.R;
import com.example.projectmorse.ui.conversion.ConversionFragment;
import com.example.projectmorse.ui.home.HomeFragment;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import static android.content.Context.VIBRATOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private Boolean pressedDown = false;
    private Date prev = new Date();
    private Queue<GlobalVars> theData = new LinkedList<>();
    final static GlobalVarD gvd = GlobalVarD.getInstance();

    private GlobalVars prevV = GlobalVars.dot;
//    final private static GlobalVarsD gvd = GlobalVarsD.getInstance();
    private Vibrator vib;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        settingsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final MediaController mediaController = root.findViewById(R.id.media_setting);
        mediaController.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(false){
                    return false;
                }
                Date cur = new Date();
                long dif = (cur.getTime() - prev.getTime());
                GlobalVars d = GlobalVars.getType(dif);
                if(motionEvent.getAction() == MotionEvent.ACTION_UP && pressedDown){
                    if(d == GlobalVars.comm){
//                        GO TO CONVERTER
                        Fragment fragment = new ConversionFragment();
                        replaceFragment(fragment);
                    } else if(d == GlobalVars.home){
//                        GO TO SETTINGS
                        Fragment fragment = new HomeFragment();
                        replaceFragment(fragment);
                    } else if(d == GlobalVars.dot || d == GlobalVars.dash || d==GlobalVars.space){
//                        ADD DOT TO DATA
                        theData.add(d);
                    } else if(d == GlobalVars.set){
//                          SEND THE DATA TODO
                    } else if(d == GlobalVars.na){
//                          NA DO NOTHING
                    }
                    pressedDown = false;
                } else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !pressedDown){
                    prev = new Date();
                    pressedDown = true;
                    prevV = GlobalVars.dot;
                } else if(pressedDown && prevV!=d){
//                    SEND A LITTLE VIBRATION TODO
                    Context context = view.getContext();
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    prevV = d;
                }
                return false;
            }
        });

        return root;
    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.media_setting, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}