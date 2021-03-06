package com.vn.hcmute.team.cortana.mymoney.data.local.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

/**
 * Created by infamouSs on 9/11/17.
 */

public abstract class DbContentProvider<T> {
    
    protected DatabaseHelper mDatabaseHelper;
    protected SQLiteDatabase mDatabase;
    public DbContentProvider(DatabaseHelper mDatabaseHelper) {
        this.mDatabaseHelper = mDatabaseHelper;
        this.open();
    }
    
    protected boolean isOpen() {
        if (mDatabase == null) {
            return false;
        }
        return mDatabase.isOpen();
    }
    
    protected synchronized void open() {
        if (!isOpen()) {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
    }
    
    protected void close() {
        mDatabase.close();
    }
    
    protected abstract String[] getAllColumns();
    
    protected abstract ContentValues createContentValues(T values);
    
    protected Cursor query(String tableName, String[] columns,
              String selection, String[] selectionArgs, String sortOrder) {
        
        return mDatabase.query(tableName, columns,
                  selection, selectionArgs, null, null, sortOrder);
    }
    
    protected Cursor query(String tableName, String[] columns,
              String selection, String[] selectionArgs, String sortOrder,
              String limit) {
        
        return mDatabase.query(tableName, columns, selection,
                  selectionArgs, null, null, sortOrder, limit);
    }
    
    protected Cursor query(String tableName, String[] columns,
              String selection, String[] selectionArgs, String groupBy,
              String having, String orderBy, String limit) {
        
        return mDatabase.query(tableName, columns, selection,
                  selectionArgs, groupBy, having, orderBy, limit);
    }
    
    protected int update(String tableName, ContentValues values,
              String selection, String[] selectionArgs) {
        return mDatabase.update(tableName, values, selection,
                  selectionArgs);
    }
    
    protected Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDatabase.rawQuery(sql, selectionArgs);
    }
    
    protected abstract List<T> makeListObjectFromCursor(Cursor cursor);
    
    protected abstract T makeSingleObjectFromCursor(Cursor cursor);
}
