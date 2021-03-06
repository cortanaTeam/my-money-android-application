package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/16/2017.
 */

public class EditBudgetActivity extends BaseActivity implements BudgetContract.View,
                                                                OnDateSetListener {
    
    @BindView(R.id.image_view_icon_budget)
    ImageView image_view_icon_budget;
    @BindView(R.id.edit_text_name_budget)
    TextView edit_text_name_budget;
    @BindView(R.id.txt_goal_money)
    TextView txt_goal_money;
    @BindView(R.id.txt_date_budget)
    TextView txt_date_budget;
    @BindView(R.id.txt_wallet_budget)
    TextView txt_wallet_budget;
    @Inject
    BudgetPresenter mBudgetPresenter;
    private Budget mBudget;
    private Wallet mWallet;
    private Category mCategory;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int yearEnd;
    private int monthOfYearEnd;
    private int dayOfMonthEnd;
    private String mGoalMoney;
    private ProgressDialog mProgressDialog;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_budget;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .budgetModule(new BudgetModule())
                  .build();
        budgetComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mBudgetPresenter;
        mBudgetPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void onDestroy() {
        mBudgetPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void initialize() {
        getData();
        showData();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
    }
    
    
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
              int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        this.year = year;
        this.monthOfYear = monthOfYear + 1;
        this.dayOfMonth = dayOfMonth;
        this.yearEnd = yearEnd;
        this.monthOfYearEnd = monthOfYearEnd + 1;
        this.dayOfMonthEnd = dayOfMonthEnd;
        
        txt_date_budget
                  .setText(this.dayOfMonth + "/" + this.monthOfYear + "/" + this.year + " - " +
                           this.dayOfMonthEnd +
                           "/" + this.monthOfYearEnd + "/" +
                           this.yearEnd);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 37) {
            if (resultCode == Activity.RESULT_OK) {
                mCategory = data.getParcelableExtra("category");
                showData();
            }
        }
        if (requestCode == 38) {
            if (resultCode == Activity.RESULT_OK) {
                mGoalMoney = data.getStringExtra("result");
                mBudget.setMoneyGoal(mGoalMoney);
                showData();
            }
        }
        if (requestCode == 39) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                showData();
            }
        }
    }
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("budget", mBudget);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public void onSuccessDeleteBudget(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        alertDiaglog(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
    
    /*Area OnClick*/
    @OnClick(R.id.ic_cancel_budget)
    public void onClickCancel(View view) {
        finish();
    }
    
    @OnClick(R.id.linear_select_category)
    public void onClickSelectCateogory(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivityForResult(intent, 37);
    }
    
    @OnClick(R.id.linear_goal_money)
    public void onClickGoalMoney(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", mGoalMoney);
        intent.putExtra("currencies", mWallet.getCurrencyUnit());
        startActivityForResult(intent, 38);
        
    }
    
    @OnClick(R.id.linear_range_date)
    public void onClickSelectDate(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                  EditBudgetActivity.this,
                  now.get(Calendar.YEAR),
                  now.get(Calendar.MONTH),
                  now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    
    @OnClick(R.id.linear_select_wallet)
    public void onClickSelectWallet(View view) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, 39);
    }
    
    @OnClick(R.id.txt_edit_budget)
    public void onClickSave(View view) {
        if (mGoalMoney.equals("0")) {
            alertDiaglog(getString(R.string.select_money));
            return;
        }
        if (!checkRangeDate()) {
            alertDiaglog(getString(R.string.txt_error_range_date));
            return;
        }
        setData();
        mBudgetPresenter.updateBudget(mBudget);
    }
    
    /*Area function*/
    public void getData() {
        Intent intent = getIntent();
        mBudget = intent.getParcelableExtra("budget");
        mCategory = mBudget.getCategory();
        mWallet = mBudget.getWallet();
        //set value date
        String[] arr = mBudget.getRangeDate().split("/");
        setDate(arr[0], arr[1]);
        //set money
        mGoalMoney = mBudget.getMoneyGoal();
        
        
    }
    
    public void setDate(String startDate, String endDate) {
        String[] start = DateUtil.convertTimeMillisToDate(startDate).split("/");
        this.year = Integer.parseInt(start[2]);
        this.monthOfYear = Integer.parseInt(start[1]);
        this.dayOfMonth = Integer.parseInt(start[0]);
        String[] end = DateUtil.convertTimeMillisToDate(endDate).split("/");
        this.yearEnd = Integer.parseInt(end[2]);
        this.monthOfYearEnd = Integer.parseInt(end[1]);
        this.dayOfMonthEnd = Integer.parseInt(end[0]);
    }
    
    public void showData() {
        GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mCategory.getIcon()),
                  image_view_icon_budget);
        
        edit_text_name_budget.setText(mCategory.getName());
        txt_goal_money.setText("+" + NumberUtil.formatAmount(mGoalMoney,
                  mWallet.getCurrencyUnit().getCurSymbol()));
        txt_date_budget.setText(getRangeDate());
        txt_wallet_budget.setText(mWallet.getWalletName());
        
    }
    
    public String getRangeDate() {
        String[] arr = mBudget.getRangeDate().split("/");
        String startDate = DateUtil.convertTimeMillisToDate(arr[0]);
        String endDate = DateUtil.convertTimeMillisToDate(arr[1]);
        return startDate + " - " + endDate;
    }
    
    public void setData() {
        mBudget.setCategory(mCategory);
        mBudget.setMoneyGoal(mGoalMoney);
        mBudget.setWallet(mWallet);
        mBudget.setRangeDate(DateUtil.getLongAsDate(dayOfMonth, monthOfYear, year) + "/" +
                             DateUtil.getLongAsDate(dayOfMonthEnd, monthOfYearEnd, yearEnd));
    }
    
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_ok),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();
                      }
                  });
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
        
    }
    
    public boolean checkRangeDate() {
        return DateUtil.getLongAsDate(dayOfMonth, monthOfYear, year) >
               DateUtil.getLongAsDate(dayOfMonthEnd, monthOfYearEnd, yearEnd) ? false : true;
    }
}
