package com.example.myapplication.presentation.ui;

public interface NavigationToolbarHelper {

    void addToHistory(String newToolbarTitle);

    int getAndNavigateBack();

    boolean historyIsEmpty();

    void clearHistory();
}
