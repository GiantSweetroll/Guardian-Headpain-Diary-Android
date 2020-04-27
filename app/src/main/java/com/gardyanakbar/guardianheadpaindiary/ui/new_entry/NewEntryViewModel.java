package com.gardyanakbar.guardianheadpaindiary.ui.new_entry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewEntryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewEntryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}