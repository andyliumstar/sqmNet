/*
 * Must ------------------- the value must given
 * Optional --------------- the value can given or not
 * High Optional ---------- the value should given if necessary
 */

package com.eostek.sqm.msg;

import com.eostek.sqm.msg.StatData;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class HeartBeatMsg implements Parcelable {
    
    //Must
    private long mTimeStamp;
    private String mSTBId;
    private String mUserId;
    private String mMacAddress;
    private byte[] mSTBIP = new byte[4];
    private int mTransmissionType;
    private byte[] mIP = new byte[4];
    private int mPort;
    private int mSTBPort;
    private String mUrl;
    private int mStatus;
    private byte[] mEPGIP = new byte[4];
    private int mStatType;
    
    //High Optional
    private int mChannelPlayDuration;
    
    //Optional
    private int mChannelAlarmDuration;
    
    //Must
    private int mPlayDuration;
    
    //Optional
    private int mAlarmDuration;
    
    //Optional
    private int mMinVmosCause;
    private int mCPUFR;
    
    //Must
    private int mSwitchNum;
    private int mSwitchSuccessNum;
    private int mSwitchAvgDelay;
    private int mSwitchDelayCheckOutNum;
    private int mVODNum;
    private int mVODSuccessNum;
    private int mVODAvgDelay;
    private int mVODDelayCheckOutNum;
    private int mBTVNum;
    private int mBTVSuccessNum;
    private int mBTVAvgDelay;
    private int mBTVDelayCheckOutNum;
    
    //High Optional
    private int mEPGNum;
    private int mEPGSuccessNum;
    private int mEPGAvgDelay;
    private int mEPGDelayCheckOutNum;
    
    //Optional
    private String mEPGErrorCode;
    private int mStopNum;
    private int mStopDuration;
    private int mChangeNum;
    
    //Must
    private StatData mAvgStatData;
    private StatData mMaxStatData;
    private StatData mMinStatData;
    
    
    //Get HeartBeatMsg Length
    public int length() {
          return 3 * mMaxStatData.length() + 35 * 4 + mSTBId.length() + mUserId.length() + mMacAddress.length() + 
                mUrl.length() + mEPGErrorCode.length();
    }
    
    //Data Access
    public long getTimeStamp() {
        return mTimeStamp;
    }
    
    public void setTimeStamp(long timeStamp) {
        this.mTimeStamp = timeStamp;
    }
    
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
    
    public int getTransmissionType() {
        return mTransmissionType;
    }
    
    public void setTransmissionType(int transmissionType) {
        this.mTransmissionType = transmissionType;
    }
    
    public byte[] getIP() {
        return mIP;
    }

    public void setIP(byte[] ip) {
        System.arraycopy(ip,0,this.mIP,0,ip.length);
    }
    
    public int getPort() {
        return mPort;
    }
    
    public void setPort(int port) {
        this.mPort = port;
    }
    
    public int getSTBPort() {
        return mSTBPort;
    }
    
    public void setSTBPort(int stbPort) {
        this.mSTBPort = stbPort;
    }
    
    public String getUrl() {
        return mUrl;
    }
    
    public void setUrl(String url) {
        this.mUrl = url;
    }
    
    public int getStatus() {
        return mStatus;
    }
    
    public void setStatus(int status) {
        this.mStatus = status;
    }
    
    public byte[] getEPGIP() {
        return mEPGIP;
    }

    public void setEPGIP(byte[] epgIP) {
        System.arraycopy(epgIP,0,this.mEPGIP,0,epgIP.length);
    }
    
    public int getStatType() {
        return mStatType;
    }
    
    public void setStatType(int statType) {
        this.mStatType = statType;
    }
    
    public int getChannelPlayDuration() {
        return mChannelPlayDuration;
    }
    
    public void setChannelPlayDuration(int channelPlayDuration) {
        this.mChannelPlayDuration = channelPlayDuration;
    }
    
    public int getChannelAlarmDuration() {
        return mChannelAlarmDuration;
    }
    
    public void setChannelAlarmDuration(int channelAlarmDuration) {
        this.mChannelAlarmDuration = channelAlarmDuration;
    }
    
    public int getPlayDuration() {
        return mPlayDuration;
    }
    
    public void setPlayDuration(int playDuration) {
        this.mPlayDuration = playDuration;
    }
    
    public int getAlarmDuration() {
        return mAlarmDuration;
    }
    
    public void setAlarmDuration(int alarmDuration) {
        this.mAlarmDuration = alarmDuration;
    }
    
    public int getMinVmosCause() {
        return mMinVmosCause;
    }
    
    public void setMinVmosCause(int minVmosCause) {
        this.mMinVmosCause = minVmosCause;
    }
    
    public int getCPUFR() {
        return mCPUFR;
    }
    
    public void setCPUFR(int cpuFR) {
        this.mCPUFR = cpuFR;
    }
    
    public int getSwitchNum() {
        return mSwitchNum;
    }
    
    public void setSwitchNum(int switchNum) {
        this.mSwitchNum = switchNum;
    }
    
    public int getSwitchSuccessNum() {
        return mSwitchSuccessNum;
    }
    
    public void setSwitchSuccessNum(int switchSuccessNum) {
        this.mSwitchSuccessNum = switchSuccessNum;
    }
    
    public int getSwitchAvgDelay() {
        return mSwitchAvgDelay;
    }
    
    public void setSwitchAvgDelay(int switchAvgDelay) {
        this.mSwitchAvgDelay = switchAvgDelay;
    }
    
    public int getSwitchDelayCheckOutNum() {
        return mSwitchDelayCheckOutNum;
    }
    
    public void setSwitchDelayCheckOutNum(int switchDelayCheckOutNum) {
        this.mSwitchDelayCheckOutNum = switchDelayCheckOutNum;
    }
    
    public int getVODNum() {
        return mVODNum;
    }
    
    public void setVODNum(int vodNum) {
        this.mVODNum = vodNum;
    }
    
    public int getVODSuccessNum() {
        return mVODSuccessNum;
    }
    
    public void setVODSuccessNum(int vodSuccessNum) {
        this.mVODSuccessNum = vodSuccessNum;
    }
    
    public int getVODAvgDelay() {
        return mVODAvgDelay;
    }
    
    public void setVODAvgDelay(int vodAvgDelay) {
        this.mVODAvgDelay = vodAvgDelay;
    }
    
    public int getVODDelayCheckOutNum() {
        return mVODDelayCheckOutNum;
    }
    
    public void setVODDelayCheckOutNum(int vodDelayCheckOutNum) {
        this.mVODDelayCheckOutNum = vodDelayCheckOutNum;
    }
    
    public int getBTVNum() {
        return mBTVNum;
    }
    
    public void setBTVNum(int btvNum) {
        this.mBTVNum = btvNum;
    }
    
    public int getBTVSuccessNum() {
        return mBTVSuccessNum;
    }
    
    public void setBTVSuccessNum(int btvSuccessNum) {
        this.mBTVSuccessNum = btvSuccessNum;
    }
    
    public int getBTVAvgDelay() {
        return mBTVAvgDelay;
    }
    
    public void setBTVAvgDelay(int btvAvgDelay) {
        this.mBTVAvgDelay = btvAvgDelay;
    }
    
    public int getBTVDelayCheckOutNum() {
        return mBTVDelayCheckOutNum;
    }
    
    public void setBTVDelayCheckOutNum(int btvDelayCheckOutNum) {
        this.mBTVDelayCheckOutNum = btvDelayCheckOutNum;
    }
    
    public int getEPGNum() {
        return mEPGNum;
    }
    
    public void setEPGNum(int epgNum) {
        this.mEPGNum = epgNum;
    }
    
    public int getEPGSuccessNum() {
        return mEPGSuccessNum;
    }
    
    public void setEPGSuccessNum(int epgSuccessNum) {
        this.mEPGSuccessNum = epgSuccessNum;
    }
    
    public int getEPGAvgDelay() {
        return mEPGAvgDelay;
    }
    
    public void setEPGAvgDelay(int epgAvgDelay) {
        this.mEPGAvgDelay = epgAvgDelay;
    }
    
    public int getEPGDelayCheckOutNum() {
        return mEPGDelayCheckOutNum;
    }
    
    public void setEPGDelayCheckOutNum(int epgDelayCheckOutNum) {
        this.mEPGDelayCheckOutNum = epgDelayCheckOutNum;
    }
    
    public String getEPGErrorCode() {
        return mEPGErrorCode;
    }
    
    public void setEPGErrorCode(String epgErrorCode) {
        this.mEPGErrorCode = epgErrorCode;
    }
    
    public int getStopNum() {
        return mStopNum;
    }
    
    public void setStopNum(int stopNum) {
        this.mStopNum = stopNum;
    }
    
    public int getStopDuration() {
        return mStopDuration;
    }
    
    public void setStopDuration(int stopDuration) {
        this.mStopDuration = stopDuration;
    }
    
    public int getChangeNum() {
        return mChangeNum;
    }
    
    public void setChangeNum(int changeNum) {
        this.mChangeNum = changeNum;
    }
    
    public StatData getAvgStatData() {
        return mAvgStatData;
    }
    
    public void setAvgStatData(StatData avgStatData) {
        this.mAvgStatData = avgStatData;
    }
    
    public StatData getMaxStatData() {
        return mMaxStatData;
    }
    
    public void setMaxStatData(StatData maxStatData) {
        this.mMaxStatData = maxStatData;
    }
    
    public StatData getMinStatData() {
        return mMinStatData;
    }
    
    public void setMinStatData(StatData minStatData) {
        this.mMinStatData = minStatData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTimeStamp);
        dest.writeString(mSTBId);
        dest.writeString(mUserId);
        dest.writeString(mMacAddress);
        dest.writeByteArray(mSTBIP);
        dest.writeInt(mTransmissionType);
        dest.writeByteArray(mIP);
        dest.writeInt(mPort);
        dest.writeInt(mSTBPort);
        dest.writeString(mUrl);
        dest.writeInt(mStatus);
        dest.writeByteArray(mEPGIP);
        dest.writeInt(mStatType);
        dest.writeInt(mChannelPlayDuration);
        dest.writeInt(mChannelAlarmDuration);
        dest.writeInt(mPlayDuration);
        dest.writeInt(mAlarmDuration);
        dest.writeInt(mMinVmosCause);
        dest.writeInt(mCPUFR);
        dest.writeInt(mSwitchNum);
        dest.writeInt(mSwitchSuccessNum);
        dest.writeInt(mSwitchAvgDelay);
        dest.writeInt(mSwitchDelayCheckOutNum);
        dest.writeInt(mVODNum);
        dest.writeInt(mVODSuccessNum);
        dest.writeInt(mVODAvgDelay);
        dest.writeInt(mVODDelayCheckOutNum);
        dest.writeInt(mBTVNum);
        dest.writeInt(mBTVSuccessNum);
        dest.writeInt(mBTVAvgDelay);
        dest.writeInt(mBTVDelayCheckOutNum);
        dest.writeInt(mEPGNum);
        dest.writeInt(mEPGSuccessNum);
        dest.writeInt(mEPGAvgDelay);
        dest.writeInt(mEPGDelayCheckOutNum);
        dest.writeString(mEPGErrorCode);
        dest.writeInt(mStopNum);
        dest.writeInt(mStopDuration);
        dest.writeInt(mChangeNum);
        dest.writeParcelable(mAvgStatData, flags);
        dest.writeParcelable(mMaxStatData, flags);
        dest.writeParcelable(mMinStatData, flags);
    }
    
    public static final Parcelable.Creator<HeartBeatMsg> CREATOR = new Creator<HeartBeatMsg>() {

        @Override
        public HeartBeatMsg createFromParcel(Parcel source) {
            Log.i("HeartBeatMsg","createFromParcel");
            
            long timeStamp = source.readLong();
            String stbId = source.readString();
            String userId = source.readString();
            String macAddress = source.readString();
            byte[] stbIP = new byte[4];
            source.readByteArray(stbIP);
            int transmissionType = source.readInt();
            byte[] ip = new byte[4];
            source.readByteArray(ip);
            int port = source.readInt();
            int stbPort = source.readInt();
            String url = source.readString();
            int status = source.readInt();
            byte[] epgIP = new byte[4];
            source.readByteArray(epgIP);
            int statType = source.readInt();
            int channelPlayDuration = source.readInt();
            int channelAlarmDuration = source.readInt();
            int playDuration = source.readInt();
            int alarmDuration = source.readInt();
            int minVmosCause = source.readInt();
            int cpuFR = source.readInt();
            int switchNum = source.readInt();
            int switchSuccessNum = source.readInt();
            int switchAvgDelay = source.readInt();
            int switchDelayCheckOutNum = source.readInt();
            int vodNum = source.readInt();
            int vodSuccessNum = source.readInt();
            int vodAvgDelay = source.readInt();
            int vodDelayCheckOutNum = source.readInt();
            int btvNum = source.readInt();
            int btvSuccessNum = source.readInt();
            int btvAvgDelay = source.readInt();
            int btvDelayCheckOutNum = source.readInt();
            int epgNum = source.readInt();
            int epgSuccessNum = source.readInt();
            int epgAvgDelay = source.readInt();
            int epgDelayCheckOutNum = source.readInt();
            String epgErrorCode = source.readString();
            int stopNum = source.readInt();
            int stopDuration = source.readInt();
            int changeNum = source.readInt();
            StatData avgStatData = source.readParcelable(StatData.class.getClassLoader());
            StatData maxStatData = source.readParcelable(StatData.class.getClassLoader());
            StatData minStatData = source.readParcelable(StatData.class.getClassLoader());
            
            HeartBeatMsg heartBeatMsg = new HeartBeatMsg();
            
            heartBeatMsg.setTimeStamp(timeStamp);
            heartBeatMsg.setSTBId(stbId);
            heartBeatMsg.setUserId(userId);
            heartBeatMsg.setMacAddress(macAddress);
            heartBeatMsg.setSTBIP(stbIP);
            heartBeatMsg.setTransmissionType(transmissionType);
            heartBeatMsg.setIP(ip);
            heartBeatMsg.setPort(port);
            heartBeatMsg.setSTBPort(stbPort);
            heartBeatMsg.setUrl(url);
            heartBeatMsg.setStatus(status);
            heartBeatMsg.setEPGIP(epgIP);
            heartBeatMsg.setStatType(statType);
            heartBeatMsg.setChannelPlayDuration(channelPlayDuration);
            heartBeatMsg.setChannelAlarmDuration(channelAlarmDuration);
            heartBeatMsg.setPlayDuration(playDuration);
            heartBeatMsg.setAlarmDuration(alarmDuration);
            heartBeatMsg.setMinVmosCause(minVmosCause);
            heartBeatMsg.setCPUFR(cpuFR);
            heartBeatMsg.setSwitchNum(switchNum);
            heartBeatMsg.setSwitchSuccessNum(switchSuccessNum);
            heartBeatMsg.setSwitchAvgDelay(switchAvgDelay);
            heartBeatMsg.setSwitchDelayCheckOutNum(switchDelayCheckOutNum);
            heartBeatMsg.setVODNum(vodNum);
            heartBeatMsg.setVODSuccessNum(vodSuccessNum);
            heartBeatMsg.setVODAvgDelay(vodAvgDelay);
            heartBeatMsg.setVODDelayCheckOutNum(vodDelayCheckOutNum);
            heartBeatMsg.setBTVNum(btvNum);
            heartBeatMsg.setBTVSuccessNum(btvSuccessNum);
            heartBeatMsg.setBTVAvgDelay(btvAvgDelay);
            heartBeatMsg.setBTVDelayCheckOutNum(btvDelayCheckOutNum);
            heartBeatMsg.setEPGNum(epgNum);
            heartBeatMsg.setEPGSuccessNum(epgSuccessNum);
            heartBeatMsg.setEPGAvgDelay(epgAvgDelay);
            heartBeatMsg.setEPGDelayCheckOutNum(epgDelayCheckOutNum);
            heartBeatMsg.setEPGErrorCode(epgErrorCode);
            heartBeatMsg.setStopNum(stopNum);
            heartBeatMsg.setStopDuration(stopDuration);
            heartBeatMsg.setChangeNum(changeNum);
            heartBeatMsg.setAvgStatData(avgStatData);
            heartBeatMsg.setMaxStatData(maxStatData);
            heartBeatMsg.setMinStatData(minStatData);
            
            return heartBeatMsg;
        }

        @Override
        public HeartBeatMsg[] newArray(int size) {
            return new HeartBeatMsg[size];
        }
        
    };
}