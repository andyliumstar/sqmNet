package com.eostek.sqm.net;

import android.os.RemoteException;
import android.util.Log;
import com.eostek.sqm.SQMNotifyImpl;
import com.eostek.sqm.msg.HeartBeatMsg;
import com.eostek.sqm.msg.RegisterBackMsg;
import com.eostek.sqm.msg.RegisterMsg;
import com.eostek.sqm.msg.SecLevelMsg;
import com.eostek.sqm.msg.SqmMsgCallBack;
import com.eostek.sqm.utils.SQMConstantUtil;
import com.eostek.sqm.utils.SQMParserUtil;

public class SQMSession implements SqmMsgCallBack {
    private MQMCClient mMQMCClientInstance = null;
    private PCFClient mPCFClientInstance = null;
    private String TAG = "SQMSession";
    
   // private int mIsVip = 0;
    public void open(String MQMCIP){
        mMQMCClientInstance = MQMCClient.getInstance(); 
        mPCFClientInstance = PCFClient.getInstance();
        
        mMQMCClientInstance.registerCallBack(this);
        mMQMCClientInstance.updateServer(MQMCIP,SQMConstantUtil.MQMCPort);
        mMQMCClientInstance.start();
        
        mPCFClientInstance.registerCallBack(this);
    }
    
    public void close(){
        if(mMQMCClientInstance != null){
            mMQMCClientInstance.closeSocket();
        }
        
        if(mPCFClientInstance != null){
            mPCFClientInstance.closeSocket();
        }
    }
    
    public void register(RegisterMsg regiMsg){
        /*RegisterMsg regiMsg = new RegisterMsg();
        byte[] epgip = new byte[4];
        SQMParserUtil.getBytesFromIP("183.58.12.205", epgip);
        regiMsg.setEPGIP(epgip);
        regiMsg.setPPPoEAccount("DHCP"+'\0');
        regiMsg.setMacAddress("00:07:63:4D:A7:0F"+'\0');
        regiMsg.setSTBId("001002990060201011C7FC232500014F"+'\0');
        byte[] stbip = new byte[4];
        SQMParserUtil.getBytesFromIP("172.23.67.118", stbip);
        regiMsg.setSTBIP(stbip);
        regiMsg.setSTBPort(0);
        regiMsg.setSTBProbeVersion("V100"+'\0');
        regiMsg.setSTBVersion("V100R003C30LGDD05B003"+'\0');
        regiMsg.setUserId("088820140415081"+'\0');*/
        mMQMCClientInstance.sendRegisterMsg(regiMsg);
    }
    
    public void reportHeartBeat(HeartBeatMsg heartBeatMsg){
        /*HeartBeatMsg heartBeatMsg = new HeartBeatMsg();
        heartBeatMsg.setTimeStamp(System.currentTimeMillis());
        heartBeatMsg.setSTBId("001002990060201011C7FC232500014F"+ '\0');
        heartBeatMsg.setUserId("088820140415081"+ '\0');
        heartBeatMsg.setMacAddress("00:07:63:4D:A7:0F"+ '\0');
        
        byte[] stbip = new byte[4];
        SQMParserUtil.getBytesFromIP("172.23.67.118", stbip);
        heartBeatMsg.setSTBIP(stbip);
        heartBeatMsg.setTransmissionType(1);
        
        byte[] ip = new byte[4];
        SQMParserUtil.getBytesFromIP("183.58.12.210", ip);
        heartBeatMsg.setIP(ip);
        heartBeatMsg.setPort(0);
        heartBeatMsg.setSTBPort(0);
        heartBeatMsg.setUrl("rtsp://183.58.12.210:554/PLTV/88888905/224/3221226934/00000100000000060000000000554903_0.smil"+ '\0');
        heartBeatMsg.setStatus(2);
        
        byte[] epgip = new byte[4];
        SQMParserUtil.getBytesFromIP("183.58.12.205", epgip);
        heartBeatMsg.setEPGIP(epgip);
        heartBeatMsg.setStatType(1);
        heartBeatMsg.setChannelPlayDuration(200);
        heartBeatMsg.setChannelAlarmDuration(0);
        heartBeatMsg.setPlayDuration(200);
        heartBeatMsg.setAlarmDuration(0);
        heartBeatMsg.setMinVmosCause(-1);
        heartBeatMsg.setCPUFR(0);
        heartBeatMsg.setSwitchNum(1);
        heartBeatMsg.setSwitchSuccessNum(1);
        heartBeatMsg.setSwitchAvgDelay(0);
        heartBeatMsg.setSwitchDelayCheckOutNum(1);
        heartBeatMsg.setVODNum(1);
        heartBeatMsg.setVODSuccessNum(1);
        heartBeatMsg.setVODAvgDelay(300);
        heartBeatMsg.setVODDelayCheckOutNum(1);
        heartBeatMsg.setBTVNum(1);
        heartBeatMsg.setBTVSuccessNum(1);
        heartBeatMsg.setBTVAvgDelay(500);
        heartBeatMsg.setBTVDelayCheckOutNum(1);
        heartBeatMsg.setEPGNum(1);
        heartBeatMsg.setEPGSuccessNum(1);
        heartBeatMsg.setEPGAvgDelay(1000);
        heartBeatMsg.setEPGDelayCheckOutNum(1);
        heartBeatMsg.setEPGErrorCode("");
        heartBeatMsg.setStopNum(-1);
        heartBeatMsg.setStopDuration(-1);
        heartBeatMsg.setChangeNum(-1);
        
        StatData avgStatData = new StatData();
        StatData maxStatData = new StatData();
        StatData minStatData = new StatData();
        
        avgStatData.setStatDataType(1);
        avgStatData.setMR(2450);
        avgStatData.setVMOS(42);
        avgStatData.setDF(18600);
        avgStatData.setMLR(0);
        avgStatData.setJitter(1802);
        avgStatData.setIPLR(0);
        avgStatData.setTCPRR(0);
        avgStatData.setCPUR(0);
        avgStatData.setPCRBitrate(2500);
        avgStatData.setBufferSize(-1);
        
        
        maxStatData.setStatDataType(2);
        maxStatData.setMR(2502);
        maxStatData.setVMOS(42);
        maxStatData.setDF(28300);
        maxStatData.setMLR(0);
        maxStatData.setJitter(6930);
        maxStatData.setIPLR(0);
        maxStatData.setTCPRR(0);
        maxStatData.setCPUR(0);
        maxStatData.setPCRBitrate(2500);
        maxStatData.setBufferSize(-1);
        
        minStatData.setStatDataType(3);
        minStatData.setMR(2251);
        minStatData.setVMOS(42);
        minStatData.setDF(8915);
        minStatData.setMLR(0);
        minStatData.setJitter(648);
        minStatData.setIPLR(0);
        minStatData.setTCPRR(0);
        minStatData.setCPUR(0);
        minStatData.setPCRBitrate(2500);
        minStatData.setBufferSize(-1);
        
        heartBeatMsg.setAvgStatData(avgStatData);
        heartBeatMsg.setMaxStatData(maxStatData);
        heartBeatMsg.setMinStatData(minStatData);*/
        mPCFClientInstance.sendHeartBeatMsg(heartBeatMsg);
    }

    public void reportSecMsg(SecLevelMsg secLevelMsg){
       /* SecLevelMsg secLevelMsg = new SecLevelMsg();
        secLevelMsg.setTimeStamp(System.currentTimeMillis());
        secLevelMsg.setSTBId("001002990060201011C7FC232500014F"+ '\0');
        secLevelMsg.setTransmissionType(1);
        
        byte[] ip = new byte[4];
        SQMParserUtil.getBytesFromIP("183.58.12.210", ip);
        secLevelMsg.setIP(ip);
        secLevelMsg.setPort(0);
        secLevelMsg.setSTBPort(0);
        secLevelMsg.setUrl("rtsp://183.59.160.61/PLTV/88888905/224/3221226963/00000100000000060000000000554901_0.smil"+ '\0');
        secLevelMsg.setPCRBitrate(2500);
        secLevelMsg.setIdealDF(4211);
        secLevelMsg.setMR(2489);
        secLevelMsg.setVMOS(43);
        secLevelMsg.setDF(10113);
        secLevelMsg.setMLR(0);
        secLevelMsg.setJitter(1091);
        secLevelMsg.setRTPLR(0);
        secLevelMsg.setRTPNum(0);
        secLevelMsg.setRTPResume(-1);
        secLevelMsg.setFECResume(-1);
        secLevelMsg.setRETResume(-1);
        secLevelMsg.setRTPDisorder(-1);
        secLevelMsg.setRTPRepeat(-1);
        secLevelMsg.setRTPLPAvg(-1);
        secLevelMsg.setRTPLPMax(-1);
        secLevelMsg.setRTPLPMin(-1);
        secLevelMsg.setRTPLDAvg(-1);
        secLevelMsg.setRTPLDMax(-1);
        secLevelMsg.setRTPLDMin(-1);
        secLevelMsg.setRTPLPE(-1);
        secLevelMsg.setRTPLDE(-1);
        secLevelMsg.setIPLR(0);
        secLevelMsg.setTCPRR(-1);
        secLevelMsg.setFrameNum(-1);
        secLevelMsg.setResumeFrame(-1);
        secLevelMsg.setResumeRatio(-1);
        secLevelMsg.setIFrameBroken(-1);
        secLevelMsg.setIFrameResume(-1);
        secLevelMsg.setPFrameBroken(-1);
        secLevelMsg.setPFrameResume(-1);
        secLevelMsg.setBFrameBroken(-1);
        secLevelMsg.setBFrameResume(-1);
        secLevelMsg.setBufferSize(-1);
        secLevelMsg.setTr11Num(0);
        secLevelMsg.setTr12Num(0);
        secLevelMsg.setTr13Num(0);
        secLevelMsg.setTr14Num(0);
        secLevelMsg.setTr15Num(0);
        secLevelMsg.setTr16Num(0);
        secLevelMsg.setTr21Num(0);
        secLevelMsg.setTr22Num(0);
        secLevelMsg.setTr23Num(0);
        secLevelMsg.setTr25Num(0);
        secLevelMsg.setPreciseData(-1);
        secLevelMsg.setVmosCause(-1);*/
        mPCFClientInstance.sendSecLevelMsg(secLevelMsg);
    }
    
    @Override
    public void onRegisterDone(RegisterBackMsg registerBackMsg) {
        Log.d(TAG,
                "onRegisterDone===========>" + SQMParserUtil.bytesToIP(registerBackMsg.mMasterPCFIP));
      //  mIsVip = registerBackMsg.mIsVip;
        mPCFClientInstance.updateServer(SQMParserUtil.bytesToIP(registerBackMsg.mMasterPCFIP),
                SQMParserUtil.bytesToIP(registerBackMsg.mSlavePCFIP), SQMConstantUtil.PCFPort);
        mPCFClientInstance.updateLevel(registerBackMsg.mLevel);
        mPCFClientInstance.start();
        try {
            if(registerBackMsg.mLevel != -1)
                SQMNotifyImpl.getInstance().getIPTVCallBack().OnLevelChanged(registerBackMsg.mLevel);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLevelChange(int level) {
        Log.d(TAG, "onLevelChange===========>"+level);
        try {
            SQMNotifyImpl.getInstance().getIPTVCallBack().OnLevelChanged(level);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
