package com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.welcome.WelcomeActivity;
import java.util.List;

/**
 * Created by infamouSs on 8/30/17.
 */

public class SelectWalletView extends RelativeLayout {
    
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SelectWalletAdapter mSelectWalletAdapter;
    private EmptyAdapter mEmptyAdapter;
    private Context mContext;
    
    
    public SelectWalletView(Context context) {
        super(context, null);
        initializeView(context);
    }
    
    public SelectWalletView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initializeView(context);
    }
    
    public SelectWalletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }
    
    private void initializeView(Context context) {
        mContext = context;
        inflate(context, R.layout.layout_content_select_wallet, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        
        mSelectWalletAdapter = new SelectWalletAdapter(context, null);
        mEmptyAdapter = new EmptyAdapter(context, "");
        
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));
    }
    
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
    
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    
    public void setData(List<Wallet> wallets) {
        if (wallets != null) {
            
            if (wallets.isEmpty()) {
                mRecyclerView.setAdapter(mEmptyAdapter);
                refreshApplication();
                return;
            }
            
            mSelectWalletAdapter.setData(wallets);
            mRecyclerView.setAdapter(mSelectWalletAdapter);
        }
        
    }
    
    private void refreshApplication() {
        PreferencesHelper.getInstance(mContext).putCurrentWallet(null);
        Intent intent = new Intent(mContext, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }
    
    public void setEmptyAdapter(String message) {
        mRecyclerView.setAdapter(mEmptyAdapter);
        mEmptyAdapter.setMessage(message);
    }
    
    public void setSelectWalletListener(SelectWalletListener listener) {
        mSelectWalletAdapter.setSelectWalletListener(listener);
    }
    
    public void removeWallet(Wallet wallet) {
        mSelectWalletAdapter.removeWallet(wallet);
        if (mSelectWalletAdapter.isEmptyData()) {
            setEmptyAdapter(mContext.getString(R.string.txt_no_data));
            refreshApplication();
        }
    }
    
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    public void updateArchiveWallet(int position, Wallet wallet) {
        mSelectWalletAdapter.updateArchiveWallet(position, wallet);
    }
    
    public void showTotal(boolean shouldShowTotal) {
        mSelectWalletAdapter.setShouldShowTotal(shouldShowTotal);
    }
    
    public void showFooter(boolean shouldShowFooter) {
        mSelectWalletAdapter.setShouldShowFooter(shouldShowFooter);
    }
    
    public void addWallet(Wallet wallet) {
        if (mRecyclerView.getAdapter() instanceof EmptyAdapter) {
            mRecyclerView.setAdapter(mSelectWalletAdapter);
        }
        mSelectWalletAdapter.addWallet(wallet);
    }
    
    public void updateWallet(Wallet wallet) {
        mSelectWalletAdapter.updateWallet(wallet);
    }
    
    public void showMenuWallet(boolean shouldMenuWallet) {
        mSelectWalletAdapter.setShouldShowMenuWallet(shouldMenuWallet);
    }
    
    public void notifyDataSetChanged() {
        mSelectWalletAdapter.notifyDataSetChanged();
    }
}
