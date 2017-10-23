package com.vn.hcmute.team.cortana.mymoney.ui.event.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kunsubin on 10/20/2017.
 */

public class TransactionEventAdapter extends BaseExpandableListAdapter {
    
    private Context mContext;
    private List<DateObjectTransaction> mListDataHeader;
    private HashMap<DateObjectTransaction, List<Transaction>> mListDataChild;
    
    
    public TransactionEventAdapter(Context context, List<DateObjectTransaction> listDataHeader,
              HashMap<DateObjectTransaction, List<Transaction>> listChildData) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
    }
    
    @Override
    public int getGroupCount() {
        return mListDataHeader.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataHeader.get(groupPosition)).size();
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return mListDataHeader.get(groupPosition);
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
    }
    
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
              ViewGroup parent) {
        
        DateObjectTransaction headerTitle = (DateObjectTransaction) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_parent_transaction_event, null);
        }
        
        ViewGroupHoder viewGroupHoder = new ViewGroupHoder(convertView);
        viewGroupHoder.bindView(headerTitle);
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
              View convertView, ViewGroup parent) {
        
        Transaction transaction = (Transaction) getChild(groupPosition, childPosition);
        
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_children_transaction_event, null);
        }
        
        ViewChildHoder viewChildHoder = new ViewChildHoder(convertView);
        viewChildHoder.bindView(transaction);
        
        return convertView;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    public class ViewGroupHoder {
        
        @BindView(R.id.txt_mon)
        TextView txt_mon;
        @BindView(R.id.txt_day)
        TextView txt_day;
        @BindView(R.id.txt_month_year)
        TextView txt_month_year;
        @BindView(R.id.txt_money)
        TextView txt_money;
        
        public ViewGroupHoder(View view) {
            ButterKnife.bind(this, view);
        }
        
        public void bindView(DateObjectTransaction dateObjectTransaction) {
            txt_mon.setText(dateObjectTransaction.getDayOfWeek());
            txt_day.setText(dateObjectTransaction.getDayOfMonth());
            txt_month_year.setText(dateObjectTransaction.getMonthOfYear() + " " +
                                   dateObjectTransaction.getYear());
            txt_money.setText("-" + NumberUtil.formatAmount(dateObjectTransaction.getMoney(),
                      dateObjectTransaction.getCurrencies()));
            
        }
    }
    
    public class ViewChildHoder {
        
        @BindView(R.id.image_category)
        ImageView image_category;
        @BindView(R.id.txt_category_name)
        TextView txt_category_name;
        @BindView(R.id.txt_money)
        TextView txt_money;
        @BindView(R.id.txt_note)
        TextView txt_note;
        
        public ViewChildHoder(View view) {
            ButterKnife.bind(this, view);
        }
        
        private void bindView(Transaction transaction) {
            
            txt_category_name.setText(transaction.getCategory().getName());
            txt_money.setText("-" + NumberUtil.formatAmount(transaction.getAmount(),
                      transaction.getWallet().getCurrencyUnit().getCurSymbol()));
            
            txt_note.setText(transaction.getNote());
            
            GlideApp.with(mContext)
                      .load(DrawableUtil.getDrawable(mContext, transaction.getCategory().getIcon()))
                      .placeholder(R.drawable.folder_placeholder)
                      .error(R.drawable.folder_placeholder)
                      .dontAnimate()
                      .into(image_category);
            
        }
    }
}
