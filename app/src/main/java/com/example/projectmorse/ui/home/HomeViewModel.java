package com.example.projectmorse.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    protected MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("I am going to use this phone to communicate with you.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}