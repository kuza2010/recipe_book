package com.example.myapplication.presentation.ui.fragments.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.presentation.ui.login.LogInActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferencesFragment extends Fragment implements PreferencesAdapter.MenuListener {
    @BindView(R.id.preferences_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RecipesPreferences preferences;

    private PreferencesAdapter adapter;

    public PreferencesFragment() {
        BaseApp.getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        ButterKnife.bind(this, view);

        configureRecyclerView();

        return view;
    }

    public void configureRecyclerView(){
        adapter = new PreferencesAdapter(getPrefList(),this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private List<PreferencesAdapter.Preferences> getPrefList(){
        List<PreferencesAdapter.Preferences> preferences = new ArrayList<>();

        preferences.add(PreferencesAdapter.Preferences.MY_RECIPE);
        preferences.add(PreferencesAdapter.Preferences.FAVORITE);
        preferences.add(PreferencesAdapter.Preferences.LOGOUT);

        return preferences;
    }

    @Override
    public void logout() {
        preferences.logOut();
        getActivity().finish();
        startActivity(new Intent(getContext(), LogInActivity.class));
    }
}
