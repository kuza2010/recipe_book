package com.example.myapplication.presentation.ui.login;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.content_provider.SearchProvider;

public class Main extends ListActivity {

    private EditText text;
    private Button add;
    private RecordsDbHelper mDbHelper;

    SearchView searchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mDbHelper = new RecordsDbHelper(this);

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())){
            Intent recordIntent = new Intent(this, RecordActivity.class);
            recordIntent.setData(intent.getData());
            startActivity(recordIntent);
            finish();
        }

        add = (Button) findViewById(R.id.add);
        text = (EditText) findViewById(R.id.text);
        searchView =(SearchView) findViewById(R.id.search_view);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String data = text.getText().toString();
                if (!data.equals("")) {
                    saveTask(data);
                    text.setText("");
                }
            }
        });

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

    }

    private void saveTask(String data) {
        mDbHelper.createRecord(data);
    }

    private void showResults(String query) {
        Cursor cursor = managedQuery(SearchProvider.CONTENT_URI, null, null,
                new String[] {query}, null);
        if (cursor == null) {
            Toast.makeText(this, "There are no results", Toast.LENGTH_SHORT).show();
        } else {
            String[] from = new String[] { RecordsDbHelper.KEY_DATA };
            int[] to = new int[] { R.id.text1 };
            SimpleCursorAdapter records = new SimpleCursorAdapter(this, R.layout.recoed_layout, cursor, from, to);
            getListView().setAdapter(records);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_record:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}