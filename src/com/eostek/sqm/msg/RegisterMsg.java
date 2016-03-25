package com.eostek.sqm.msg;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class RegisterMsg implements Parcelable {

    //Must
    private String mSTBId;
    private String mUserId;
    private String mMacAddress;
    private byte[] mSTBIP = new byte[4];
    private int mSTBPort;
    private String mPPPoEAccount;
    private byte[] mEPGIP = new byte[4];
    private String mSTBVersion;
    private String mSTBProbeVersion;
    
    //Get RegisterMsg Length
    public int length() {
        return 2 * (Byte.SIZE/8 * 4)
                + 1 * (Integer.SIZE / 8)
                + mSTBId.length()
                + mUserId.length()
                + mMacAddress.length()
                + mPPPoEAccount.length()
                + mSTBVersion.length()
                + mSTBProbeVersion.length();
    }

    //Data Access
    public String getSTBId() {
        return mSTBId;
    }

    public void setSTBId(String stbId) {
        this.mSTBId = stbId;
    }
    
    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }
    
    public String getMacAddress() {
        return mMacAddress;
    }

    public void setMacAddress(String macAddress) {
        this.mMacAddress = macAddress;
    }
    
    public byte[] getSTBIP() {
        return mSTBIP;
    }
    
    public void setSTBIP(byte[] stbIP) {
        System.arraycopy(stbIP,0,this.mSTBIP,0,stbIP.length);
    }
    
    public int getSTBPort() {
        return mSTBPort;
    }
    
    public void setSTBPort(int stbPort) {
        this.mSTBPort = stbPort;
    }
    
    public String getPPPoEAccount() {
        return mPPPoEAccount;
    }

    public void setPPPoEAccount(String pppoeAccount) {
        this.mPPPoEAccount = pppoeAccount;
    }
    
    public byte[] getEPGIP() {
        return mEPGIP;
    }

    public void setEPGIP(byte[] epgIP) {
        System.arraycopy(epgIP,0,this.mEPGIP,0,epgIP.length);
    }
    
    public String getSTBVersion() {
        return mSTBVersion;
    }

    public void setSTBVersion(String stbVersion) {
        this.mSTBVersion = stbVersion;
    }
    
    public String getSTBProbeVersion() {
        return mSTBProbeVersion;
    }
    
    public void setSTBProbeVersion(String stbProbeVersion) {
        mSTBProbeVersion = stbProbeVersion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSTBId);
        dest.writeString(mUserId);
        dest.writeString(mMacAddress);
        dest.writeByteArray(mSTBIP);
        dest.writeInt(mSTBPort);
        dest.writeString(mPPPoEAccount);
        dest.writeByteArray(mEPGIP);
        dest.writeString(mSTBVersion);
        dest.writeString(mSTBProbeVersion);
    }

    public static final Parcelable.Creator<RegisterMsg> CREATOR = new Creator<RegisterMsg>() {

        @Override
        public RegisterMsg[] newArray(int size) {
            return new RegisterMsg[size];
        }

        @Override
        public RegisterMsg createFromParcel(Parcel source) {
            Log.i("RegisterMsg","createFromParcel");
            
            String stbId = source.readString();
            String userId = source.readString();
            String macAddress = source.readString();
            byte[] stbIP = new byte[4];
            source.readByteArray(stbIP);
            int stbPort = source.readInt();
            String pppoeAccount = source.readString();
            byte[] epgIP = new byte[4];
            source.readByteArray(epgIP);
            String stbVersion = source.readString();
            String stbProbeVersion = source.readString();
            
            RegisterMsg regMessage = new RegisterMsg();
            
            regMessage.setSTBId(stbId);
            regMessage.setUserId(userId);
            regMessage.setMacAddress(macAddress);
            regMessage.setSTBIP(stbIP);
            regMessage.setSTBPort(stbPort);
            regMessage.setPPPoEAccount(pppoeAccount);
            regMessage.setEPGIP(epgIP);
            regMessage.setSTBVersion(stbVersion);
            regMessage.setSTBProbeVersion(stbProbeVersion);

            return regMessage;
        }
    };

}  