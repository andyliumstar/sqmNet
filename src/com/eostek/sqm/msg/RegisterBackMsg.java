package com.eostek.sqm.msg;

public class RegisterBackMsg {
    public int mResult = -1;
    public int mLevel = -1;
    public int mMonitorPoint = -1;
    public int mFrameAnalyse = -1;
    public int mSignalFlag = -1;
    public byte[] mMasterPCFIP = new byte[4];
    public byte[] mSlavePCFIP = new byte[4];
    public int mIndexCount = -1;
    public int mIndexID = -1;
    public int mCategory = -1;
    public int mAvailable = -1;
    public int mThreshold = -1;
    public int mDetectType = -1;
    public int mInterval = -1;
    public int mContAlarm = -1;
    public int mContClear = -1;
    public int mAcmAlarm = -1;
    public int mHBinterval = -1;
    public int mIsVip = -1;
    public int mCPURMaxBadValue = -1;
    public int mEpgMaxBadValue = -1;
    public int mEpgMaxReqDelay = -1;
}
