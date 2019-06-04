package com.example.myapplication.presentation.ui;

import com.example.myapplication.Utils;

import java.util.LinkedList;

import timber.log.Timber;

public class NavigationToolbarHelperImpl implements NavigationToolbarHelper {

    private LinkedList<String> toolbarHistory = new LinkedList<>();

    public NavigationToolbarHelperImpl() {

    }

    public void addToHistory(String newToolbarTitle) {
        if (newToolbarTitle.equals(toolbarHistory.peek()))
            return;

        if (toolbarHistory.contains(newToolbarTitle)) {
            Timber.d("Attempt to add existing element. History remove %s", newToolbarTitle);
            toolbarHistory.remove(newToolbarTitle);
        }

        Timber.d("Push to toolbarHistory element %s", newToolbarTitle);
        toolbarHistory.push(newToolbarTitle);
    }

    public int getAndNavigateBack() {
        if (toolbarHistory.size() >= 2) {
            popElement();
            return Utils.getResId(toolbarHistory.peek());
        } else {
            Timber.w("Navigation is over. Last Element %s, toolbarHistory size %s", toolbarHistory.peek(), toolbarHistory.size());
            return -1;
        }
    }

    @Override
    public boolean historyIsEmpty() {
        return toolbarHistory.isEmpty();
    }

    @Override
    public void clearHistory() {
        if(!historyIsEmpty()){
            Timber.d("clearHistory: clean history!");
            toolbarHistory.clear();
        }
    }

    private String popElement() {
        Timber.d("popElement %s", toolbarHistory.peek());
        return toolbarHistory.pop();
    }
}
