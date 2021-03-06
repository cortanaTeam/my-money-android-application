package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class Currencies implements Parcelable {
    
    public static final Creator<Currencies> CREATOR = new Creator<Currencies>() {
        @Override
        public Currencies createFromParcel(Parcel in) {
            return new Currencies(in);
        }
        
        @Override
        public Currencies[] newArray(int size) {
            return new Currencies[size];
        }
    };
    @SerializedName("cur_id")
    @Expose
    private String curId;
    @SerializedName("code")
    @Expose
    private String curCode;
    @SerializedName("name")
    @Expose
    private String curName;
    @SerializedName("symbol")
    @Expose
    private String curSymbol;
    @SerializedName("display_type")
    @Expose
    private String curDisplayType;
    
    public Currencies() {
        this.curId = "";
        this.curCode = "";
        this.curName = "";
        this.curSymbol = "";
        this.curDisplayType = "";
    }
    
    public Currencies(String curId, String curName, String curSymbol,
              String curDisplayType, String curCode) {
        this.curId = curId;
        this.curCode = curCode;
        this.curName = curName;
        this.curSymbol = curSymbol;
        this.curDisplayType = curDisplayType;
    }
    
    protected Currencies(Parcel in) {
        curId = in.readString();
        curCode = in.readString();
        curName = in.readString();
        curSymbol = in.readString();
        curDisplayType = in.readString();
    }
    
    public String getCurId() {
        return curId;
    }
    
    public void setCurId(String curId) {
        this.curId = curId;
    }
    
    public String getCurCode() {
        return curCode;
    }
    
    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }
    
    public String getCurName() {
        return curName;
    }
    
    public void setCurName(String curName) {
        this.curName = curName;
    }
    
    public String getCurSymbol() {
        return curSymbol;
    }
    
    public void setCurSymbol(String curSymbol) {
        this.curSymbol = curSymbol;
    }
    
    public String getCurDisplayType() {
        return curDisplayType;
    }
    
    public void setCurDisplayType(String curDisplayType) {
        this.curDisplayType = curDisplayType;
    }
    
    @Override
    public String toString() {
        return "Currencies{" +
               "curId='" + curId + '\'' +
               ", curCode='" + curCode + '\'' +
               ", curName='" + curName + '\'' +
               ", curSymbol='" + curSymbol + '\'' +
               ", curDisplayType='" + curDisplayType + '\'' +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(curId);
        dest.writeString(curCode);
        dest.writeString(curName);
        dest.writeString(curSymbol);
        dest.writeString(curDisplayType);
    }
}
