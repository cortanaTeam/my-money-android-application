package com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by kunsubin on 9/1/2017.
 */

public class CalculatorActivity extends BaseActivity implements DialogCallback {
    
    @BindView(R.id.txt_input)
    TextView txt_input;
    @BindView(R.id.txt_currency)
    TextView txt_currency;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.image_check)
    ImageView image_check;
    @BindView(R.id.linear_currencies_big)
    LinearLayout linear_currencies_big;
    Currencies mCurrencies;
    private boolean mFlag = false;
    private DialogFragmentTransferCurrencies mDialogFragmentTransferCurrencies;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_calculator;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        txt_input.setMovementMethod(new ScrollingMovementMethod());
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        showData();
        
        if (mCurrencies == null) {
            mDialogFragmentTransferCurrencies = new DialogFragmentTransferCurrencies("VND");
        } else {
            mDialogFragmentTransferCurrencies = new DialogFragmentTransferCurrencies(
                      mCurrencies.getCurCode());
        }
        mDialogFragmentTransferCurrencies.setCallBack(this);
    }
    
    @Override
    public void getResults(String results) {
        txt_input.setText(results);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 50) {
            mDialogFragmentTransferCurrencies.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                txt_input.setText("0");
                break;
            case R.id.btn_remove:
                if (txt_input.length() > 0) {
                    if (txt_input.length() == 1) {
                        txt_input.setText("0");
                    } else {
                        StringBuilder tmp = new StringBuilder(txt_input.getText().toString());
                        tmp.delete(tmp.length() - 1, tmp.length());
                        txt_input.setText(tmp.toString());
                    }
                } else {
                    txt_input.setText("0");
                }
                break;
            case R.id.image_remove:
                if (txt_input.length() > 0) {
                    if (txt_input.length() == 1) {
                        txt_input.setText("0");
                    } else {
                        StringBuilder tmp = new StringBuilder(txt_input.getText().toString());
                        tmp.delete(tmp.length() - 1, tmp.length());
                        txt_input.setText(tmp.toString());
                    }
                } else {
                    txt_input.setText("0");
                }
                break;
            case R.id.btn_equal:
                try {
                    Double.parseDouble(txt_input.getText().toString().trim());
                    resultAndFinish();
                } catch (Exception ex) {
                    try {
                        String tmp = txt_input.getText().toString().replace("x", "*");
                        tmp = tmp.replace("÷", "/");
                        Expression e = new ExpressionBuilder(tmp).build();
                        double result = e.evaluate();
                        txt_input.setText(TextUtil.doubleToString(result));
                    } catch (Exception e) {
                        alertDiaglog(this.getString(R.string.txt_invalid_input));
                    }
                }
                
                break;
            default:
                Button button = (Button) view;
                if (txt_input.getText().toString().equals("0")) {
                    txt_input.setText("");
                }
                txt_input.append(button.getText());
                break;
        }
    }
    
    @OnClick(R.id.check_box)
    public void onClickCheck(View view) {
        if (mFlag) {
            finish();
        }
        try {
            Double.parseDouble(txt_input.getText().toString().trim());
            
            if (!txt_input.getText().toString().equals("")) {
                resultAndFinish();
            } else {
                alertDiaglog(this.getString(R.string.txt_invalid_input));
            }
        } catch (Exception ex) {
            alertDiaglog(this.getString(R.string.txt_invalid_input));
        }
        
    }
    
    @OnClick(R.id.linear_currencies)
    public void onClickCurrencies(View view) {
        mDialogFragmentTransferCurrencies.show(getSupportFragmentManager(), "");
    }
    
    public void showData() {
        Intent intent = getIntent();
        String goalMoney = intent.getStringExtra("goal_money");
        mCurrencies = intent.getParcelableExtra("currencies");
        mFlag = intent.getBooleanExtra("flag", false);
        if (goalMoney != null) {
            txt_input.setText(goalMoney);
        } else {
            txt_input.setText("0");
        }
        
        if (mCurrencies != null) {
            txt_currency.setText(mCurrencies.getCurSymbol());
        }
        if (mFlag) {
            linear_currencies_big.setVisibility(View.GONE);
            txt_title.setText(getString(R.string.txt_calculator));
            image_check.setImageResource(R.drawable.ic_back);
        }
        
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
    
    public void resultAndFinish() {
        if (txt_input.length() > 15) {
            alertDiaglog(getString(R.string.max15digit));
            return;
        }
        if (Double.parseDouble(txt_input.getText().toString().trim()) < 0) {
            alertDiaglog(getString(R.string.erro_negative));
            return;
        }
        if (!mFlag) {
            Intent returnIntent = new Intent();
            String amount = txt_input.getText().toString();
            returnIntent.putExtra("result_view",
                      NumberUtil.formatAmount(amount, txt_currency.getText().toString().trim()));
            returnIntent.putExtra("result", amount);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
    
    
}
