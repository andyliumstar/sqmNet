package com.eostek.sqm.msg;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SecLevelMsg implements Parcelable {
    //Must
    private long mTimeStamp;
    private String mSTBId;
    private int mTransmissionType;
    private byte[] mIP = new byte[4];
    private int mPort;
    private int mSTBPort;
    private String mUrl;
    private int mPCRBitrate;
    private int mIdealDF;
    private int mMR;
    private int mVMOS;
    private int mDF;
    private int mMLR;
    private int mJitter;
    private int mRTPLR;
    private int mRTPNum;
    
    //Optional
    private int mRTPResume;
    private int mFECResume;
    private int mRETResume;
    private int mRTPDisorder;
    private int mRTPRepeat;
    private int mRTPLPAvg;
    private int mRTPLPMax;
    private int mRTPLPMin;
    private int mRTPLDAvg;
    private int mRTPLDMax;
    private int mRTPLDMin;
    private int mRTPLPE;
    private int mRTPLDE;
    
    //Must
    private int mIPLR;
    
    //Optional
    private int mTCPRR;
    private int mFrameNum;
    private int mResumeFrame;
    private int mResumeRatio;
    private int mIFrameBroken;
    private int mIFrameResume;
    private int mPFrameBroken;
    private int mPFrameResume;
    private int mBFrameBroken;
    private int mBFrameResume;
    
    //Must
    private int mBufferSize;
    private int mTr11Num;
    private int mTr12Num;
    private int mTr13Num;
    private int mTr14Num;
    private int mTr15Num;
    private int mTr16Num;
    private int mTr21Num;
    private int mTr22Num;
    private int mTr23Num;
    private int mTr25Num;
    
    //Optional
    private int mPreciseData;
    private int mVmosCause;
    
    public int length() {
        return 1 * (Byte.SIZE / 8) * 4
                + 49 * (Integer.SIZE / 8)
                + 1 * (Long.SIZE / 8)
                + mSTBId.length()
                + mUrl.length();
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
        this.mSTBPort = stbPort                                                                                      ;
    }
    
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
    
    public int getPCRBitrate() {
        return mPCRBitrate;
    }

    public void setPCRBitrate(int pcrBitrate) {
        this.mPCRBitrate = pcrBitrate;
    }
    
    public int getIdealDF() {
        return mIdealDF;
    }

    public void setIdealDF(int idealDF) {
        this.mIdealDF = idealDF;
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
    
    public int getRTPLR() {
        return mRTPLR;
    }

    public void setRTPLR(int rtpLR) {
        this.mRTPLR = rtpLR;
    }
    
    public int getRTPNum() {
        return mRTPNum;
    }

    public void setRTPNum(int rtpNum) {
        this.mRTPNum = rtpNum;
    }
    
    public int getRTPResume() {
        return mRTPResume;
    }

    public void setRTPResume(int rtpResume) {
        this.mRTPResume = rtpResume;
    }
    
    public int getFECResume() {
        return mFECResume;
    }

    public void setFECResume(int fecResume) {
        this.mFECResume = fecResume;
    }
    
    public int getRETResume() {
        return mRETResume;
    }

    public void setRETResume(int retResume) {
        this.mRETResume = retResume;
    }
    
    public int getRTPDisorder() {
        return mRTPDisorder;
    }

    public void setRTPDisorder(int rtpDisorder) {
        this.mRTPDisorder = rtpDisorder;
    }
    
    public int getRTPRepeat() {
        return mRTPRepeat;
    }

    public void setRTPRepeat(int rtpRepeat) {
        this.mRTPRepeat = rtpRepeat;
    }
    
    public int getRTPLPAvg() {
        return mRTPLPAvg;
    }

    public void setRTPLPAvg(int rtpLPAvg) {
        this.mRTPLPAvg = rtpLPAvg;
    }
    
    public int getRTPLPMax() {
        return mRTPLPMax;
    }

    public void setRTPLPMax(int rtpLPMax) {
        this.mRTPLPMax = rtpLPMax;
    }
    
    public int getRTPLPMin() {
        return mRTPLPMin;
    }

    public void setRTPLPMin(int rtpLPMin) {
        this.mRTPLPMin = rtpLPMin;
    }
    
    public int getRTPLDAvg() {
        return mRTPLDAvg;
    }

    public void setRTPLDAvg(int rtpLDAvg) {
        this.mRTPLDAvg = rtpLDAvg;
    }
    
    public int getRTPLDMax() {
        return mRTPLDMax;
    }

    public void setRTPLDMax(int rtpLDMax) {
        this.mRTPLDMax = rtpLDMax;
    }
    
    public int getRTPLDMin() {
        return mRTPLDMin;
    }

    public void setRTPLDMin(int rtpLDMin) {
        this.mRTPLDMin = rtpLDMin;
    }
    
    public int getRTPLPE() {
        return mRTPLPE;
    }

    public void setRTPLPE(int rtpLPE) {
        this.mRTPLPE = rtpLPE;
    }
    
    public int getRTPLDE() {
        return mRTPLDE;
    }

    public void setRTPLDE(int rtpLDE) {
        this.mRTPLDE = rtpLDE;
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
    
    public int getFrameNum() {
        return mFrameNum;
    }

    public void setFrameNum(int frameNum) {
        this.mFrameNum = frameNum;
    }
    
    public int getResumeFrame() {
        return mResumeFrame;
    }

    public void setResumeFrame(int resumeFrame) {
        this.mResumeFrame = resumeFrame;
    }
    
    public int getResumeRatio() {
        return mResumeRatio;
    }

    public void setResumeRatio(int resumeRatio) {
        this.mResumeRatio = resumeRatio;
    }
    
    public int getIFrameBroken() {
        return mIFrameBroken;
    }

    public void setIFrameBroken(int iframeBroken) {
        this.mIFrameBroken = iframeBroken;
    }
    
    public int getIFrameResume() {
        return mIFrameResume;
    }

    public void setIFrameResume(int iframeResume) {
        this.mIFrameResume = iframeResume;
    }
    
    public int getPFrameBroken() {
        return mPFrameBroken;
    }

    public void setPFrameBroken(int pframeBroken) {
        this.mPFrameBroken = pframeBroken;
    }
    
    public int getPFrameResume() {
        return mPFrameResume;
    }

    public void setPFrameResume(int pframeResume) {
        this.mPFrameResume = pframeResume;
    }
    
    public int getBFrameBroken() {
        return mBFrameBroken;
    }

    public void setBFrameBroken(int bframeBroken) {
        this.mBFrameBroken = bframeBroken;
    }
    
    public int getBFrameResume() {
        return mBFrameResume;
    }

    public void setBFrameResume(int bframeResume) {
        this.mBFrameResume = bframeResume;
    }
    
    public int getBufferSize() {
        return mBufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.mBufferSize = bufferSize;
    }
    
    public int getTr11Num() {
        return mTr11Num;
    }

    public void setTr11Num(int tr11Num) {
        this.mTr11Num = tr11Num;
    }
    
    public int getTr12Num() {
        return mTr12Num;
    }

    public void setTr12Num(int tr12Num) {
        this.mTr12Num = tr12Num;
    }
    
    public int getTr13Num() {
        return mTr13Num;
    }

    public void setTr13Num(int tr13Num) {
        this.mTr13Num = tr13Num;
    }
    
    public int getTr14Num() {
        return mTr14Num;
    }

    public void setTr14Num(int tr14Num) {
        this.mTr14Num = tr14Num;
    }
    
    public int getTr15Num() {
        return mTr15Num;
    }

    public void setTr15Num(int tr15Num) {
        this.mTr15Num = tr15Num;
    }
    
    public int getTr16Num() {
        return mTr16Num;
    }

    public void setTr16Num(int tr16Num) {
        this.mTr16Num = tr16Num;
    }
    
    public int getTr21Num() {
        return mTr21Num;
    }

    public void setTr21Num(int tr21Num) {
        this.mTr21Num = tr21Num;
    }
    
    public int getTr22Num() {
        return mTr22Num;
    }

    public void setTr22Num(int tr22Num) {
        this.mTr22Num = tr22Num;
    }
    
    public int getTr23Num() {
        return mTr23Num;
    }

    public void setTr23Num(int tr23Num) {
        this.mTr23Num = tr23Num;
    }
    
    public int getTr25Num() {
        return mTr25Num;
    }

    public void setTr25Num(int tr25Num) {
        this.mTr25Num = tr25Num;
    }
    
    public int getPreciseData() {
        return mPreciseData;
    }

    public void setPreciseData(int preciseData) {
        this.mPreciseData = preciseData;
    }
    
    public int getVmosCause() {
        return mVmosCause;
    }

    public void setVmosCause(int vmosCause) {
        this.mVmosCause = vmosCause;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTimeStamp);
        dest.writeString(mSTBId);
        dest.writeInt(mTransmissionType);
        dest.writeByteArray(mIP);
        dest.writeInt(mPort);
        dest.writeInt(mSTBPort);
        dest.writeString(mUrl);
        dest.writeInt(mPCRBitrate);
        dest.writeInt(mIdealDF);
        dest.writeInt(mMR);
        dest.writeInt(mVMOS);
        dest.writeInt(mDF);
        dest.writeInt(mMLR);
        dest.writeInt(mJitter);
        dest.writeInt(mRTPLR);
        dest.writeInt(mRTPNum);
        dest.writeInt(mRTPResume);
        dest.writeInt(mFECResume);
        dest.writeInt(mRETResume);
        dest.writeInt(mRTPDisorder);
        dest.writeInt(mRTPRepeat);
        dest.writeInt(mRTPLPAvg);
        dest.writeInt(mRTPLPMax);
        dest.writeInt(mRTPLPMin);
        dest.writeInt(mRTPLDAvg);
        dest.writeInt(mRTPLDMax);
        dest.writeInt(mRTPLDMin);
        dest.writeInt(mRTPLPE);
        dest.writeInt(mRTPLDE);
        dest.writeInt(mIPLR);
        dest.writeInt(mTCPRR);
        dest.writeInt(mFrameNum);
        dest.writeInt(mResumeFrame);
        dest.writeInt(mResumeRatio);
        dest.writeInt(mIFrameBroken);
        dest.writeInt(mIFrameResume);
        dest.writeInt(mPFrameBroken);
        dest.writeInt(mPFrameResume);
        dest.writeInt(mBFrameBroken);
        dest.writeInt(mBFrameResume);
        dest.writeInt(mBufferSize);
        dest.writeInt(mTr11Num);
        dest.writeInt(mTr12Num);
        dest.writeInt(mTr13Num);
        dest.writeInt(mTr14Num);
        dest.writeInt(mTr15Num);
        dest.writeInt(mTr16Num);
        dest.writeInt(mTr21Num);
        dest.writeInt(mTr22Num);
        dest.writeInt(mTr23Num);
        dest.writeInt(mTr25Num);
        dest.writeInt(mPreciseData);
        dest.writeInt(mVmosCause);
    }
    
    public static final Parcelable.Creator<SecLevelMsg> CREATOR = new Creator<SecLevelMsg>() {

        @Override
        public SecLevelMsg createFromParcel(Parcel source) {
            Log.i("SecLevelMsg","createFromParcel");
            
            long timeStamp = source.readLong();
            String stbId = source.readString();
            int transmissionType = source.readInt();
            byte[] ip = new byte[4];
            source.readByteArray(ip);
            int port = source.readInt();
            int stbPort = source.readInt();
            String url = source.readString();
            int pcrBitrate = source.readInt();
            int idealDF = source.readInt();
            int mr = source.readInt();
            int vmos = source.readInt();
            int df = source.readInt();
            int mlr = source.readInt();
            int jitter = source.readInt();
            int rtpLR = source.readInt();
            int rtpNum = source.readInt();
            int rtpResume = source.readInt();
            int fecResume = source.readInt();
            int retResume = source.readInt();
            int rtpDisorder = source.readInt();
            int rtpRepeat = source.readInt();
            int rtpLPAvg = source.readInt();
            int rtpLPMax = source.readInt();
            int rtpLPMin = source.readInt();
            int rtpLDAvg = source.readInt();
            int rtpLDMax = source.readInt();
            int rtpLDMin = source.readInt();
            int rtpLPE = source.readInt();
            int rtpLDE = source.readInt();
            int iplr = source.readInt();
            int tcprr = source.readInt();
            int frameNum = source.readInt();
            int resumeFrame = source.readInt();
            int resumeRatio = source.readInt();
            int iframeBroken = source.readInt();
            int iframeResume = source.readInt();
            int pframeBroken = source.readInt();
            int pframeResume = source.readInt();
            int bframeBroken = source.readInt();
            int bframeResume = source.readInt();
            int bufferSize = source.readInt();
            int tr11Num = source.readInt();
            int tr12Num = source.readInt();
            int tr13Num = source.readInt();
            int tr14Num = source.readInt();
            int tr15Num = source.readInt();
            int tr16Num = source.readInt();
            int tr21Num = source.readInt();
            int tr22Num = source.readInt();
            int tr23Num = source.readInt();
            int tr25Num = source.readInt();
            int preciseData = source.readInt();
            int vmosCause = source.readInt();
            
            SecLevelMsg secLevelMsg = new SecLevelMsg();
            
            secLevelMsg.setTimeStamp(timeStamp);
            secLevelMsg.setSTBId(stbId);
            secLevelMsg.setTransmissionType(transmissionType);
            secLevelMsg.setIP(ip);
            secLevelMsg.setPort(port);
            secLevelMsg.setSTBPort(stbPort);
            secLevelMsg.setUrl(url);
            secLevelMsg.setPCRBitrate(pcrBitrate);
            secLevelMsg.setIdealDF(idealDF);
            secLevelMsg.setMR(mr);
            secLevelMsg.setVMOS(vmos);
            secLevelMsg.setDF(df);
            secLevelMsg.setMLR(mlr);
            secLevelMsg.setJitter(jitter);
            secLevelMsg.setRTPLR(rtpLR);
            secLevelMsg.setRTPNum(rtpNum);
            secLevelMsg.setRTPResume(rtpResume);
            secLevelMsg.setFECResume(fecResume);
            secLevelMsg.setRETResume(retResume);
            secLevelMsg.setRTPDisorder(rtpDisorder);
            secLevelMsg.setRTPRepeat(rtpRepeat);
            secLevelMsg.setRTPLPAvg(rtpLPAvg);
            secLevelMsg.setRTPLPMax(rtpLPMax);
            secLevelMsg.setRTPLPMin(rtpLPMin);
            secLevelMsg.setRTPLDAvg(rtpLDAvg);
            secLevelMsg.setRTPLDMax(rtpLDMax);
            secLevelMsg.setRTPLDMin(rtpLDMin);
            secLevelMsg.setRTPLPE(rtpLPE);
            secLevelMsg.setRTPLDE(rtpLDE);
            secLevelMsg.setIPLR(iplr);
            secLevelMsg.setTCPRR(tcprr);
            secLevelMsg.setFrameNum(frameNum);
            secLevelMsg.setResumeFrame(resumeFrame);
            secLevelMsg.setResumeRatio(resumeRatio);
            secLevelMsg.setIFrameBroken(iframeBroken);
            secLevelMsg.setIFrameResume(iframeResume);
            secLevelMsg.setPFrameBroken(pframeBroken);
            secLevelMsg.setPFrameResume(pframeResume);
            secLevelMsg.setBFrameBroken(bframeBroken);
            secLevelMsg.setBFrameResume(bframeResume);
            secLevelMsg.setBufferSize(bufferSize);
            secLevelMsg.setTr11Num(tr11Num);
            secLevelMsg.setTr12Num(tr12Num);
            secLevelMsg.setTr13Num(tr13Num);
            secLevelMsg.setTr14Num(tr14Num);
            secLevelMsg.setTr15Num(tr15Num);
            secLevelMsg.setTr16Num(tr16Num);
            secLevelMsg.setTr21Num(tr21Num);
            secLevelMsg.setTr22Num(tr22Num);
            secLevelMsg.setTr23Num(tr23Num);
            secLevelMsg.setTr25Num(tr25Num);
            secLevelMsg.setPreciseData(preciseData);
            secLevelMsg.setVmosCause(vmosCause);
            
            return secLevelMsg;
        }

        @Override
        public SecLevelMsg[] newArray(int size) {
            return new SecLevelMsg[size];
        }
        
    };
}