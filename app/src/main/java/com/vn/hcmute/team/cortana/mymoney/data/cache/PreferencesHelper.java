package com.vn.hcmute.team.cortana.mymoney.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;

/**
 * Created by infamouSs on 8/21/17.
 */

public class PreferencesHelper {
    
    public static final String TAG = PreferencesHelper.class.getSimpleName();
    
    private static final String PREF_NAME = "my_money_pref";
    
    public static PreferencesHelper sInstance;
    private final String PREF_USER_ID = "PREF_USER_ID";
    private final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private final String PREF_CURRENT_USER = "PREF_CURRENT_USER";
    private final String PREF_CURRENT_WALLET = "PREF_CURRENT_WALLET";
    private final String PREF_CURRENT_REAL_TIME_CURRENCY = "PREF_CURRENT_REAL_TIME_CURRENCY";
    private final String PREF_FACEBOOK_ACCESS_TOKEN = "PREF_FACEBOOK_ACCESS_TOKEN";
    private final String PREF_TRANSACTION_VIEW_BY = "PREF_TRANSACTION_VIEW_BY";
    private final String PREF_TIME_RANGE_TRANSACTION = "PREF_TIME_RANGE_TRANSACTION";
    private final String PREF_START_DATE_AND_END_DATE = "PREF_START_DATE_AND_END_DATE";
    private final String PREF_LANGUAGE = "PREF_LANGUAGE";
    
    private SharedPreferences mSharedPreferences;
    private Gson mGson;
    
    private PreferencesHelper(Context context) {
        this.mSharedPreferences = context.getApplicationContext()
                  .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mGson = new Gson();
    }
    
    public static PreferencesHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PreferencesHelper.class) {
                sInstance = new PreferencesHelper(context.getApplicationContext());
            }
        }
        return sInstance;
    }
    
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
    
    public void putUserId(String userid) {
        mSharedPreferences.edit().putString(PREF_USER_ID, userid).apply();
    }
    
    public void putUserToken(String token) {
        mSharedPreferences.edit().putString(PREF_USER_TOKEN, token).apply();
    }
    
    public void putUser(User user) {
        String _user = mGson.toJson(user, User.class);
        mSharedPreferences.edit().putString(PREF_CURRENT_USER, _user).apply();
    }
    
    public String getUserId() {
        return mSharedPreferences.getString(PREF_USER_ID, "");
    }
    
    public String getUserToken() {
        return mSharedPreferences.getString(PREF_USER_TOKEN, "");
    }
    
    public User getCurrentUser() {
        String currentUser = mSharedPreferences.getString(PREF_CURRENT_USER, "");
        if (TextUtils.isEmpty(currentUser)) {
            return null;
        }
        return mGson.fromJson(currentUser, User.class);
    }
    
    public Wallet getCurrentWallet() {
        String currentWallet = mSharedPreferences.getString(PREF_CURRENT_WALLET, "");
        if (TextUtils.isEmpty(currentWallet)) {
            return null;
        }
        return mGson.fromJson(currentWallet, Wallet.class);
    }
    
    public void putCurrentWallet(Wallet wallet) {
        String _wallet = mGson.toJson(wallet, Wallet.class);
        mSharedPreferences.edit().putString(PREF_CURRENT_WALLET, _wallet).apply();
    }
    
    public void putRealTimeCurrency(RealTimeCurrency realTimeCurrency) {
        String _realTimeCurrency = mGson.toJson(realTimeCurrency, RealTimeCurrency.class);
        mSharedPreferences.edit().putString(PREF_CURRENT_REAL_TIME_CURRENCY, _realTimeCurrency)
                  .apply();
    }
    
    public RealTimeCurrency getRealTimeCurrency() {
        String realTimeCurrency = mSharedPreferences.getString(PREF_CURRENT_REAL_TIME_CURRENCY, "");
        if (TextUtils.isEmpty(realTimeCurrency)) {
            return null;
        }
        return mGson.fromJson(realTimeCurrency, RealTimeCurrency.class);
    }
    
    public void putFacebookAccessToken(String token) {
        mSharedPreferences.edit().putString(PREF_FACEBOOK_ACCESS_TOKEN, token)
                  .apply();
    }
    
    public String getFacebookAccessToken() {
        return mSharedPreferences.getString(PREF_FACEBOOK_ACCESS_TOKEN, "");
    }
    
    public void clearFacebookAccessToken() {
        putFacebookAccessToken("");
    }
    
    public void putTransactionViewBy(int value) {
        //0: transaction
        //1: category
        mSharedPreferences.edit().putInt(PREF_TRANSACTION_VIEW_BY, value).apply();
    }
    
    public int getTransactionViewBy() {
        return mSharedPreferences.getInt(PREF_TRANSACTION_VIEW_BY, 0);
    }
    
    public void putTransactionTimeRange(String str) {
        mSharedPreferences.edit().putString(PREF_TIME_RANGE_TRANSACTION, str).apply();
    }
    
    public String getTransactionTimeRange() {
        return mSharedPreferences.getString(PREF_TIME_RANGE_TRANSACTION, "date");
    }
    
    public void putLastStartDateAndEndDate(String data) {
        mSharedPreferences.edit().putString(PREF_START_DATE_AND_END_DATE, data).apply();
    }
    
    public String getLastStartDateAndEndDate() {
        return mSharedPreferences.getString(PREF_START_DATE_AND_END_DATE, "");
    }
    
    public void putLanguage(String code) {
        mSharedPreferences.edit().putString(PREF_LANGUAGE, code).apply();
    }
    
    public String getLanguage() {
        return mSharedPreferences.getString(PREF_LANGUAGE, "en");
    }
}
