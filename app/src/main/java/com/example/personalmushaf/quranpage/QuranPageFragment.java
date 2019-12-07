package com.example.personalmushaf.quranpage;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.personalmushaf.R;
import com.example.personalmushaf.widgets.QuranSinglePage;

public class QuranPageFragment extends Fragment {
    public QuranPageFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        int pageNumber = getArguments().getInt("page_number");

        ImageView imageView = v.findViewById(R.id.page1);

        QuranSinglePage quranSinglePage = new QuranSinglePage(imageView, pageNumber);
        quranSinglePage.init();

        return v;
    }
}
