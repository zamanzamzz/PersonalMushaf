package com.example.personalmushaf.thirteenlinepage;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.personalmushaf.R;
import com.squareup.picasso.Picasso;



/**
 * A simple {@link Fragment} subclass.
 */
public class ThirteenLinePageFragment extends Fragment {

    private ImageView imageView;
    private Context context;
    private Resources resources;
    private String packageName;

    public ThirteenLinePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (context == null)
            context = getContext();

        if (resources == null)
            resources = context.getResources();

        if (packageName == null)
            packageName = context.getPackageName();

        View view = inflater.inflate(R.layout.fragment_page, container, false);
        imageView = view.findViewById(R.id.page);
        Picasso.get().load(resources.getIdentifier("pg_" + getArguments().getString("page_number"),
                "drawable", packageName)).centerInside().fit().into(imageView);
        return view;
    }

}
