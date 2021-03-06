package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerTransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.TransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.FragmentTransactionByTime;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 10/24/2017.
 */

public class TransactionBudgetActivity extends BaseActivity implements TransactionContract.View {
    
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    
    @Inject
    TransactionPresenter mTransactionPresenter;
    private Budget mBudget;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_list_transaction;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getApplication())
                  .getAppComponent();
        TransactionComponent transactionComponent = DaggerTransactionComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .transactionModule(new TransactionModule())
                  .build();
        
        transactionComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mTransactionPresenter;
        mTransactionPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
    
    }
    
    @Override
    protected void initialize() {
        MyLogger.d("langthangkhongnha");
        getData();
        //get list transaction by event
        final String[] arr = mBudget.getRangeDate().split("/");
        mTransactionPresenter.getTransactionByBudget(arr[0], arr[1], mBudget.getCategory().getId(),
                  mBudget.getWallet().getWalletid());
        
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTransactionPresenter
                          .getTransactionByBudget(arr[0], arr[1], mBudget.getCategory().getId(),
                                    mBudget.getWallet().getWalletid());
            }
        });
        
    }
    
    @Override
    protected void onDestroy() {
        mTransactionPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        MyLogger.d("langthangkhongnha");
        MyLogger.d("something",list.size());
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        FragmentTransactionByTime mFragmentTransactionByTime = new FragmentTransactionByTime();
        mFragmentTransactionByTime.setDataTransaction(list);
        getSupportFragmentManager().beginTransaction()
                  .replace(R.id.view_fragment, mFragmentTransactionByTime).commit();
        
    }
    
    @Override
    public void onFailure(String message) {
       MyLogger.d("FAIL",message);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void loading(boolean isLoading) {
    
    }
    
    /*Area onClick*/
    @OnClick(R.id.ic_cancel)
    public void onClickCancel(View view) {
        finish();
    }
    
    /*Area funcction*/
    public void getData() {
        mBudget = new Budget();
        Intent intent = this.getIntent();
        if (intent.getParcelableExtra("budget") != null) {
            mBudget = intent.getParcelableExtra("budget");
        }
    }
}
