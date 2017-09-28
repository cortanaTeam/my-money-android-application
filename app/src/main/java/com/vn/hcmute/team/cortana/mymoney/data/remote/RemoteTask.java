package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface RemoteTask {
    
    public interface UserTask {
        
        Observable<User> login(UserCredential userCredential);
        
        Observable<User> login(User user);
    
        Observable<String> isExistFacebookAccount(User user);
        
        Observable<String> register(User user);
        
        Observable<String> forgetPassword(String email);
        
        Observable<String> changePassword(String userid, String token, String oldPassword,
                  String newPassword);
        
        Observable<String> changeProfile(String userid, String token, User user);
    }
    
    public interface ImageTask {
        
        Observable<List<Image>> getImage(String userid, String token);
        
        Observable<Image> getImageByid(String userid, String token, String imageid);
        
        Observable<String> removeImage(String userid, String token, String imageid);
        
        Observable<String> uploadImage(RequestBody userid, RequestBody token, RequestBody detail,
                  MultipartBody.Part file);
        
        Observable<String> updateImage(String userid, String token, String imageid);
    }
    
    interface WalletTask {
        
        Observable<String> createWallet(Wallet wallet, String userid, String token);
        
        Observable<String> updateWallet(Wallet wallet, String userid, String token);
        
        Observable<String> deleteWallet(String userid, String token, String idwallet);
        
        Observable<List<Wallet>> getAllWallet(String userid, String token);
        
        Observable<String> moveWallet(String userid, String token, String wallet1, String wallet2,
                  String money);
    }
    
    interface CurrenciesTask {
        
        Observable<List<Currencies>> getCurrencies();
        
        Observable<ResultConvert> convertCurrency(String amount, String from, String to);
        
        Observable<RealTimeCurrency> getRealTimeCurrency();
    }
    
    interface EventTask {
        
        Observable<List<Event>> getEvent(String uerid, String token);
        
        Observable<String> createEvent(Event event, String userid, String token);
        
        Observable<String> updateEvent(Event event, String userid, String token);
        
        Observable<String> deleteEvent(String userid, String token, String idEvent);
        
    }
    
    interface SavingTask {
        
        Observable<List<Saving>> getSaving(String userid, String token);
        
        Observable<String> createSaving(Saving saving, String userid, String token);
        
        Observable<String> updateSaving(Saving saving, String userid, String token);
        
        Observable<String> deleteSaving(String userid, String token, String idSaving);
        
        Observable<String> takeInSaving(String userid, String token, String idWallet,
                  String idSaving, String money);
        
        Observable<String> takeOutSaving(String userid, String token, String idWallet,
                  String idSaving, String money);
    }
    
    interface PersonTask {
        
        Observable<List<Person>> getPerson(String userid, String token);
        
        Observable<String> addPerson(Person person, String userid, String token);
        
        Observable<String> removePerson(String userid, String token, String personid);
        
        Observable<String> updatePerson(Person person, String userid, String token);
        
        Observable<String> syncPerson(List<Person> persons, String userid, String token);
    }
    
    interface BudgetTask {
        
        Observable<List<Budget>> getBudget(String userid, String token);
        
        Observable<String> createBudget(Budget budget, String userid, String token);
        
        Observable<String> updateBudget(Budget budget, String userid, String token);
        
        Observable<String> deleteBudget(String userid, String token, String budgetId);
    }
    
    interface CategoryTask {
        
        Observable<List<Category>> getListCategory(String userid, String token, String type);
        
        Observable<List<Category>> getListCategoryByType(String userid, String token, String type,
                  String transType);
        
        Observable<String> createCategory(String userid, String token, String parentId,
                  Category category);
        
        Observable<String> updateCategory(String userid, String token, String oldParentId,
                  String newParentId, Category category);
        
        Observable<String> deleteCategory(String userid, String token, String parentId,
                  Category category);
    }
}
