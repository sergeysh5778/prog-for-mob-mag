package ru.sfedu.sergeysh.lab1.ui.selected_movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectedMovieViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SelectedMovieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Фрагмент «Выбранный фильм»");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
