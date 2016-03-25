package com.eostek.sqm.msg;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class StatData implements Parcelable {
    
    //Must
    private int mStatDataType;
    private int mMR;
    
    //High Optional
    private int mVMOS;
    
    //Must
    private int mDF;
    private int mMLR;
    private int mJitter;
    private int mIPLR;
    
    //High Optional
    private int mTCPRR;
    private int mCPUR;
    
    //Must
    private int mPCRBitrate;
    
    //Optional
    private int mBufferSize;
    
    //Get Message Length
    public int length() {
        return 11 * (Integer.SIZE / 8);
    }
    
    //Data Access
    public int getStatDataType() {
        return mStatDataType;
    }
    
    public void setStatDataType(int statDataType) {
        this.mStatDataType = statDataType;
    }
    
    public int getMR() {
        return mMR;
    }
    
    public void setMR(int mr) {
        this.mMR = mr;
    }
    
    public int getVMOS() {
        return mVMOS;
    }
    
    public void setVMOS(int vmos) {
        this.mVMOS = vmos;
    }
    
    public int getDF() {
        return mDF;
    }
    
    public void setDF(int df) {
        this.mDF = df;
    }
    
    public int getMLR() {
        return mMLR;
    }
    
    public void setMLR(int mlr) {
        this.mMLR = mlr;
    }
    
    public int getJitter() {
        return mJitter;
    }
    
    public void setJitter(int jitter) {
        this.mJitter = jitter;
    }
    
    public int getIPLR() {
        return mIPLR;
    }
    
    public void setIPLR(int iplr) {
        this.mIPLR = iplr;
    }
    
    public int getTCPRR() {
        return mTCPRR;
    }
    
    public void setTCPRR(int tcprr) {
        this.mTCPRR = tcprr;
    }
    
    public int getCPUR() {
        return mCPUR;
    }
    
    public void setCPUR(int cpur) {
        this.mCPUR = cpur;
    }
    
    public int getPCRBitrate() {
        return mPCRBitrate;
    }
    
    public void setPCRBitrate(int pcrBitrate) {
        this.mPCRBitrate = pcrBitrate;
    }
    
    public int getBufferSize() {
        return mBufferSize;
    }
    
    public void setBufferSize(int bufferSize) {
        this.mBufferSize = bufferSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mStatDataType);
        dest.writeInt(mMR);
        dest.writeInt(mVMOS);
        dest.writeInt(mDF);
        dest.writeInt(mMLR);
        dest.writeInt(mJitter);
        dest.writeInt(mIPLR);
        dest.writeInt(mTCPRR);
        dest.writeInt(mCPUR);
        dest.writeInt(mPCRBitrate);
        dest.writeInt(mBufferSize);
    }
    
    public static final Parcelable.Creator<StatData> CREATOR = new Creator<StatData>() {

        @Override
        public StatData createFromParcel(Parcel source) {
            Log.i("StatData","createFromParcel");
            
            int statDataType = source.readInt();
            int mr = source.readInt();
            int vmos = source.readInt();
            int df = source.readInt();
            int mlr = source.readInt();
            int jitter = source.readInt();
            int iplr = source.readInt();
            int tcprr = source.readInt();
            int cpur = source.readInt();
            int pcrBitrate = source.readInt();
            int bufferSize = source.readInt();
            
            StatData statData = new StatData();
            
            statData.setStatDataType(statDataType);
            statData.setMR(mr);
            statData.setVMOS(vmos);
            statData.setDF(df);
            statData.setMLR(mlr);
            statData.setJitter(jitter);
            statData.setIPLR(iplr);
            statData.setTCPRR(tcprr);
            statData.setCPUR(cpur);
            statData.setPCRBitrate(pcrBitrate);
            statData.setBufferSize(bufferSize);
            
            return statData;
        }

        @Override
        public StatData[] newArray(int size) {
            return new StatData[size];
        } 
        
    };
    
}