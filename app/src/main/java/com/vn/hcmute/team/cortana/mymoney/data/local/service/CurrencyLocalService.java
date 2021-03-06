package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/11/17.
 */

public class CurrencyLocalService extends DbContentProvider<Currencies> implements
                                                                        LocalService.CurrencyLocalRepository {
    
    public static final String TAG = CurrencyLocalService.class.getSimpleName();
    private static CurrencyLocalService sInstance;
    private final String TABLE_NAME = "tbl_currency";
    private final String COLUMN_ID = "cur_id";
    private final String COLUMN_NAME = "cur_name";
    private final String COLUMN_SYMBOL = "cur_symbol";
    private final String COLUMN_DISPLAY_TYPE = "cur_display_type";
    private final String COLUMN_CODE = "cur_code";
    
    private CurrencyLocalService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    public static CurrencyLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (BudgetLocalService.class) {
                if (sInstance == null) {
                    sInstance = new CurrencyLocalService(databaseHelper);
                }
            }
        }
        return sInstance;
    }
    
    @Override
    public String[] getAllColumns() {
        return new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_SYMBOL, COLUMN_DISPLAY_TYPE,
                  COLUMN_CODE};
    }
    
    @Override
    protected ContentValues createContentValues(Currencies values) {
        return null;
    }
    
    @Override
    protected List<Currencies> makeListObjectFromCursor(Cursor cursor) {
        List<Currencies> lists = new ArrayList<>();
        
        while (cursor.moveToNext()) {
            Currencies currencies = makeSingleObjectFromCursor(cursor);
            lists.add(currencies);
        }
        cursor.close();
        return lists;
    }
    
    @Override
    protected Currencies makeSingleObjectFromCursor(Cursor cursor) {
        String id = cursor.getString(0);
        String name = cursor.getString(1);
        String symbol = cursor.getString(2);
        String display_type = cursor.getString(3);
        String code = cursor.getString(4);
        
        return new Currencies(id, name, symbol, display_type,
                  code);
    }
    
    @Override
    public Callable<List<Currencies>> getListCurrency() {
        try {
            return new Callable<List<Currencies>>() {
                @Override
                public List<Currencies> call() throws Exception {
                    Cursor cursor = CurrencyLocalService.this
                              .query(TABLE_NAME, getAllColumns(), null, null, null);
                    if (cursor == null) {
                        return null;
                    }
                    
                    return makeListObjectFromCursor(cursor);
                    
                }
            };
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Override
    public Currencies getCurrency(String id) {
        String selection = "cur_id=?";
        String[] selectionArg = new String[]{id};
        Currencies currencies = null;
        Cursor cursor = CurrencyLocalService.this
                  .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            currencies = new Currencies();
            currencies.setCurId(cursor.getString(0));
            currencies.setCurName(cursor.getString(1));
            currencies.setCurSymbol(cursor.getString(2));
            currencies.setCurDisplayType(cursor.getString(3));
            currencies.setCurCode(cursor.getString(4));
        }
        return currencies;
    }
}
