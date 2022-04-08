package ru.sfedu.sergeysh.lab1.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoviesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MoviesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Фрагмент «Фильмы»");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
