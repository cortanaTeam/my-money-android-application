package com.vn.hcmute.team.cortana.mymoney.utils.validate;

import static com.vn.hcmute.team.cortana.mymoney.utils.ObjectUtil.requireNotNull;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import java.util.regex.Pattern;

/**
 * Created by infamouSs on 8/11/17.
 */

public class UserValidate {
    
    
    public static final String TAG = UserValidate.class.getSimpleName();
    
    private static final String NAME_PATTERN = "^[a-zA-Z0-9]{4,15}$";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_@]{4,100}$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_@]{4,100}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    
    
    public static UserValidate sInstance = new UserValidate();
    
    private Pattern mPatternUsername;
    private Pattern mPatternPassword;
    private Pattern mPatternName;
    private Pattern mPatternEmail;
    
    private UserValidate() {
        mPatternUsername = Pattern.compile(USERNAME_PATTERN);
        mPatternPassword = Pattern.compile(PASSWORD_PATTERN);
        mPatternName = Pattern.compile(NAME_PATTERN);
        mPatternEmail = Pattern.compile(EMAIL_PATTERN);
    }
    
    public static UserValidate getInstance() {
        return sInstance;
    }
    
    public boolean validateUser(User user) {
        requireNotNull(user);
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        
        if (username == null || password == null || email == null) {
            return false;
        }
        
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            return false;
        }
        
        if (!mPatternUsername.matcher(username).matches()) {
            return false;
        }
        
        if (!mPatternPassword.matcher(password).matches()) {
            return false;
        }
        if (!mPatternEmail.matcher(email).matches()) {
            return false;
        }
        
        return true;
    }
    
    public boolean validateUser(UserCredential userCredential) {
        requireNotNull(userCredential);
        
        String username = userCredential.getUsername();
        String password = userCredential.getPassword();
        
        if (username == null || password == null) {
            return false;
        }
        
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        
        if (!mPatternUsername.matcher(username).matches()) {
            return false;
        }
        
        if (!mPatternPassword.matcher(password).matches()) {
            return false;
        }
        
        return true;
    }
}
