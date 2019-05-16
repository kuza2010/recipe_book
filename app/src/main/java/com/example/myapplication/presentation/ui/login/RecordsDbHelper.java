package com.example.myapplication.presentation.ui.login;

import java.util.HashMap;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

public class RecordsDbHelper {

    //Åäèíñòâåííûé ñòîëáåö â òàáëèöå - äàííûå
    public static final String KEY_DATA = SearchManager.SUGGEST_COLUMN_TEXT_1;

    private static final String TAG = "RecordsDbHelper";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "datas";
    private static final String DATABASE_TABLE = "records";
    private static final int DATABASE_VERSION = 2;

    //Ñöåíàðèé ñîçäàíèÿ ÁÄ
    private static final String DATABASE_CREATE =
            "CREATE VIRTUAL TABLE " + DATABASE_TABLE +
                    " USING fts3 (" + KEY_DATA + ");";

    private static final HashMap<String,String> mColumnMap = buildColumnMap();

    /**
     * Âîçâðàùàåò êóðñîð, óêàçûâàþùèé íà çàïèñü ñ rowId
     * @param rowId id âîçâðàùàåìîé çàïèñè
     * @param columns âîçâðàùàåìûå ñòîëáöû çàïèñè; åñëè null, òî âñå
     * @return êóðñîð, óêàçûâàþùèé íà îïðåäåëåííóþ çàïèñü, null - åñëè íå çàïèñü íå íàéäåíà
     */
    public Cursor getRecord(String rowId, String[] columns) {
        String selection = "rowid = ?";
        String[] selectionArgs = new String[] {rowId};

        return query(selection, selectionArgs, columns);
    }

    public Cursor getRecordMatches(String query, String[] columns) {
        String selection = KEY_DATA + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }


    private static HashMap<String,String> buildColumnMap() {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(KEY_DATA, KEY_DATA);
        map.put(BaseColumns._ID, "rowid AS " +
                BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        return map;
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DATABASE_TABLE);
        builder.setProjectionMap(mColumnMap);

        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    /**
     *Ñîçäàåò/îòêðûâàåò ÁÄ
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS records");
            onCreate(db);
        }
    }

    public RecordsDbHelper(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    /**
     * Äîáàâëÿåò çàïèñü â òàáëèöó
     * @param data äàííûå, ñîõðàíÿåìûå â òàáëèöó
     * @return id çàïèñè, èëè -1, åñëè äîáàâëåíèå íå óäàëîñü
     */
    public long createRecord(String data) {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, data);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

}