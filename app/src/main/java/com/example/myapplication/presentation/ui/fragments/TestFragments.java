package com.example.myapplication.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class TestFragments extends Fragment {
    public static final String TAG = TestFragments.class.getSimpleName();
    public static final String TAG2 = TestFragments.class.getSimpleName()+"2";
    private String q="SEARCH";

    public TestFragments() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        Bundle b = getArguments();
        if(b!=null && b.containsKey("Q"))
            q = b.getString("Q");

        ((TextView)view.findViewById(R.id.test_id)).setText(q);

        return view;
    }
}
