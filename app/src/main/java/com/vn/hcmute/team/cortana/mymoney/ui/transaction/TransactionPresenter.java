package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract.AddUpdateView;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract.DeleteView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase.ImageRequest;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase.TransactionRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/28/17.
 */

public class TransactionPresenter extends BasePresenter<TransactionContract.View> implements
                                                                                  TransactionContract.Presenter {
    
    public static final String TAG = TransactionPresenter.class.getSimpleName();
    
    private TransactionUseCase mTransactionUseCase;
    private ImageUseCase mImageUseCase;
    
    @Inject
    public TransactionPresenter(TransactionUseCase transactionUseCase, ImageUseCase imageUseCase) {
        this.mTransactionUseCase = transactionUseCase;
        this.mImageUseCase = imageUseCase;
    }
    
    @Override
    public void addTransaction(final Transaction transaction, List<ImageGallery> galleryList) {
        final TransactionRequest request = new TransactionRequest(Action.ACTION_ADD_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddUpdateView) getView())
                                    .onAddSuccessTransaction(transaction, (String) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          MyLogger.d(throwable.getMessage());
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, transaction, null, TypeRepository.LOCAL);
        
        if (galleryList != null && !galleryList.isEmpty()) {
            List<String> paths = new ArrayList<>();
            for (ImageGallery img : galleryList) {
                paths.add(img.getPath());
            }
            ImageRequest imageRequest = new ImageRequest(Action.ACTION_UPLOAD_IMAGE,
                      new BaseCallBack<Object>() {
                          @Override
                          public void onSuccess(Object value) {
                              getView().loading(false);
                              List<Image> images = (List<Image>) value;
                              transaction.setImage(images);
                              
                              mTransactionUseCase.subscribe(request);
                          }
                          
                          @Override
                          public void onFailure(Throwable throwable) {
                              getView().loading(false);
                          }
                          
                          @Override
                          public void onLoading() {
                              getView().loading(true);
                          }
                      }, new String[]{"detail"}, paths.toArray(new String[paths.size()]));
            mImageUseCase.subscribe(imageRequest);
        } else {
            mTransactionUseCase.subscribe(request);
        }
        
    }
    
    @Override
    public void updateTransaction(final Transaction transaction) {
        
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_UPDATE_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddUpdateView) getView())
                                    .onUpdateSuccessTransaction(transaction, (String) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, transaction, null, TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
    }
    
    @Override
    public void getAllTransaction() {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_ALL_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          List<Transaction> list = (List<Transaction>) value;
                          if (list == null) {
                              getView().onFailure("Cannot get transactions");
                              return;
                          }
                          getView().showAllListTransaction(list);
                          
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, null, TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
    }
    
    @Override
    public void getTransactionByEvent(String eventId) {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_TRANSACTION_BY_EVENT,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          MyLogger.d("GET_BY_EVENT_TRUE", ((List<Transaction>) value).size());
                          getView().showAllListTransaction((List<Transaction>) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          MyLogger.d("GET_BY_EVENT", "false");
                          
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, new String[]{eventId}, TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
    }
    
    @Override
    public void getTransactionByBudget(String startDate, String endDate, String categoryId,
              String walletId) {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_TRANSACTION_BY_BUDGET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().showAllListTransaction((List<Transaction>) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, new String[]{startDate, endDate, categoryId, walletId},
                  TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
        
    }
    
    @Override
    public void getTransactionBySaving(String savingId) {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_TRANSACTION_BY_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().showAllListTransaction((List<Transaction>) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, new String[]{savingId}, TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
    }
    
    public void deleteTransaction(Transaction transaction) {
        TransactionRequest request = new TransactionRequest(Action.ACTION_DELETE_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((DeleteView) getView()).onDeleteSuccessTransaction((String) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, transaction, null, TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
        
    }
    
    @Override
    public void getTransactionByTime(String startDate, String endDate, String idWallet) {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_TRANSACTION_BY_TIME,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          List<Transaction> list = (List<Transaction>) value;
                          if (list == null) {
                              getView().onFailure("Cannot get transactions");
                              MyLogger.d("langthang", "sdkjfds");
                              return;
                          }
                          MyLogger.d("langthang", list.size());
                          getView().showAllListTransaction(list);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          MyLogger.d("langthang", throwable.getMessage());
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, new String[]{startDate, endDate, idWallet},
                  TypeRepository.LOCAL);
        
        mTransactionUseCase.subscribe(request);
    }
    
    public void transferMoney(String walletFrom, String walletTo, String amount) {
    
    }
    
    @Override
    public void unSubscribe() {
        mTransactionUseCase.unSubscribe();
    }
}
