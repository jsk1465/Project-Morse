package com.example.projectmorse.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

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
import com.example.projectmorse.ui.settings.SettingsFragment;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class HomeFragment extends Fragment {

    private Boolean pressedDown = false;
    private Date prev = new Date();
    private Queue<GlobalVars> theData = new LinkedList<>();
    private GlobalVars prevV = GlobalVars.dot;
    final static GlobalVarD gvd = GlobalVarD.getInstance();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final MediaController mediaController = root.findViewById(R.id.media_home);
        mediaController.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Date cur = new Date();
                long dif = (cur.getTime() - prev.getTime());
                GlobalVars d = GlobalVars.getType(dif);
                if(gvd.wasShook){
                    return false;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP && pressedDown){
                    if(d == GlobalVars.comm){
//                        GO TO CONVERTER
                        Fragment fragment = new ConversionFragment();
                        replaceFragment(fragment);
                    } else if(d == GlobalVars.set){
//                        GO TO SETTINGS
                        Fragment fragment = new SettingsFragment();
                        replaceFragment(fragment);
                        } else if(d == GlobalVars.dot || d == GlobalVars.dash || d==GlobalVars.space){
                //                        ADD DOT TO DATA
                            theData.add(d);
                        } else if(d == GlobalVars.home){
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
                    prevV = d;
                }
        return false;
        }
        });

        return root;
        }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.media_home, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}