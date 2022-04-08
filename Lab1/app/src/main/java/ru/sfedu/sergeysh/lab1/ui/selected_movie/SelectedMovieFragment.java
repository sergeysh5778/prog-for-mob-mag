package ru.sfedu.sergeysh.lab1.ui.selected_movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.sergeysh.lab1.databinding.FragmentSelectedMovieBinding;

public class SelectedMovieFragment extends Fragment {

    private FragmentSelectedMovieBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SelectedMovieViewModel selectedMovieViewModel =
                new ViewModelProvider(this).get(SelectedMovieViewModel.class);

        binding = FragmentSelectedMovieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSelectedMovie;
        selectedMovieViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
