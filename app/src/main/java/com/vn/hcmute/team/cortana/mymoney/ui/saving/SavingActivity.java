package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;

/**
 * Created by infamouSs on 8/28/2017.
 */

public class SavingActivity extends BaseActivity{
    
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add_saving)
    FloatingActionButton btn_add_saving;
    
    private PagerAdapter mPagerAdapter;
 
    @Override
    public int getLayoutId() {
        return R.layout.activity_saving;
    }
    
    @Override
    protected void initializeDagger() {
     
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    
        mPagerAdapter=new PagerAdapter(getSupportFragmentManager(),0);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
        
            @Override
            public void onTabUnselected(Tab tab) {
            
            }
        
            @Override
            public void onTabReselected(Tab tab) {
            
            }
        });
        
        initTabLayout();
    
    }
    @OnClick(R.id.btn_add_saving)
    public void onClickAddSaving(View view){
        
        Intent intent =new Intent(this,AddSavingActivity.class);
        startActivityForResult(intent,12);
        
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==12){
            if(resultCode == Activity.RESULT_OK){
                Saving saving=data.getParcelableExtra("resultAdd");
                if(saving!=null){
                   
                }
            }
        }
    }
    
    @Override
    protected void onDestroy() {
     
        super.onDestroy();
    }
    
    @Override
    protected void initializePresenter() {
      
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
       
    }
    private void initTabLayout(){
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
