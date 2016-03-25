package com.eostek.sqm;

import android.os.RemoteException;
import android.util.Log;

import com.android.iptv.browser.datacollector.service.ICollectorCallBack;
import com.eostek.sqm.msg.HeartBeatMsg;
import com.eostek.sqm.msg.RegisterMsg;
import com.eostek.sqm.msg.SecLevelMsg;
import com.eostek.sqm.net.SQMSession;
import com.eostek.sqm.utils.SQMParserUtil;
import com.eostek.sqm.service.ISQMNotify;

public class SQMNotifyImpl extends ISQMNotify.Stub {
    
    private final String TAG = "SQMNotifyImpl";
    private static SQMNotifyImpl mInstance = new SQMNotifyImpl();
    
    private ICollectorCallBack mIPTVCallBack;
    private SQMSession mSession;
    
    public ICollectorCallBack getIPTVCallBack() {
        return mIPTVCallBack;
    }
        
    private SQMNotifyImpl() {
    }
        
    public static SQMNotifyImpl getInstance() { 
        if (mInstance == null)
            mInstance = new SQMNotifyImpl();
        
        return mInstance;
    }
    
    public void setSQMSession(SQMSession s) {
        this.mSession = s;
    }

    @Override
    public void registerCollectorCallBack(ICollectorCallBack cb) throws RemoteException {
        mIPTVCallBack = cb;
    }

    @Override
    public void sendRegisterMsg(RegisterMsg msg) throws RemoteException {
        Log.i(TAG, msg.getSTBId());
        Log.i(TAG, msg.getUserId());
        Log.i(TAG, msg.getMacAddress());
        Log.i(TAG, SQMParserUtil.bytesToIP(msg.getSTBIP()));
        Log.i(TAG, ""+msg.getSTBPort());
        Log.i(TAG, msg.getPPPoEAccount());
        Log.i(TAG, SQMParserUtil.bytesToIP(msg.getEPGIP()));
        Log.i(TAG, msg.getSTBVersion());   
        Log.i(TAG, msg.getSTBProbeVersion());
        mSession.register(msg);
    }

    @Override
    public void sendHeartBeatMsg(HeartBeatMsg msg) throws RemoteException {
        Log.i(TAG, "sendHeartBeatMsg++++>");
        Log.i(TAG, "TimeStamp:"+msg.getTimeStamp());
        Log.i(TAG, "STBId:"+msg.getSTBId());
        Log.i(TAG, "UserId:"+msg.getUserId());
        Log.i(TAG, "MacAddress:"+msg.getMacAddress());
        Log.i(TAG, "STBIP:"+SQMParserUtil.bytesToIP(msg.getSTBIP()));
        Log.i(TAG, "TransmissionType:"+msg.getTransmissionType());
        Log.i(TAG, "IP:"+SQMParserUtil.bytesToIP(msg.getIP()));
        Log.i(TAG, "Port:"+msg.getPort());
        Log.i(TAG, "STBPort:"+msg.getSTBPort());
        Log.i(TAG, "Url:"+msg.getUrl());
        Log.i(TAG, "Status:"+msg.getStatus());
        Log.i(TAG, "EPGIP:"+SQMParserUtil.bytesToIP(msg.getEPGIP()));
        Log.i(TAG, "StatType:"+msg.getStatType());
        Log.i(TAG, "PlayDuration:"+msg.getChannelPlayDuration());
        Log.i(TAG, "ChannelAlarmDuration:"+msg.getChannelAlarmDuration());
        Log.i(TAG, "PlayDuration:"+msg.getPlayDuration());
        Log.i(TAG, "AlarmDuration:"+msg.getAlarmDuration());
        Log.i(TAG, "MinVmosCause:"+msg.getMinVmosCause());
        Log.i(TAG, "CPUFR:"+msg.getCPUFR());
        Log.i(TAG, "SwitchNum:"+msg.getSwitchNum());
        Log.i(TAG, "SwitchSuccessNum:"+msg.getSwitchSuccessNum());
        Log.i(TAG, "SwitchAvgDelay:"+msg.getSwitchAvgDelay());
        Log.i(TAG, "SwitchDelayCheckOutNum:"+msg.getSwitchDelayCheckOutNum());
        Log.i(TAG, "VODNum:"+msg.getVODNum());
        Log.i(TAG, "VODSuccessNum:"+msg.getVODSuccessNum());
        Log.i(TAG, "VODAvgDelay:"+msg.getVODAvgDelay());
        Log.i(TAG, "VODDelayCheckOutNum:"+msg.getVODDelayCheckOutNum());
        Log.i(TAG, "BTVNum:"+msg.getBTVNum());
        Log.i(TAG, "BTVSuccessNum:"+msg.getBTVSuccessNum());
        Log.i(TAG, "BTVAvgDelay:"+msg.getBTVAvgDelay());
        Log.i(TAG, "BTVDelayCheckOutNum:"+msg.getBTVDelayCheckOutNum());
        Log.i(TAG, "EPGNum:"+msg.getEPGNum());
        Log.i(TAG, "EPGSuccessNum:"+msg.getEPGSuccessNum());
        Log.i(TAG, "EPGAvgDelay:"+msg.getEPGAvgDelay());
        Log.i(TAG, "EPGDelayCheckOutNum:"+msg.getEPGDelayCheckOutNum());
        Log.i(TAG, "EPGErrorCode:"+msg.getEPGErrorCode());
        Log.i(TAG, "StopNum:"+msg.getStopNum());
        Log.i(TAG, "StopDuration:"+msg.getStopDuration());
        Log.i(TAG, "ChangeNum:"+msg.getChangeNum());
        Log.i(TAG, "AVGDF:"+msg.getAvgStatData().getDF());
        Log.i(TAG, "AVGMR:"+msg.getAvgStatData().getMR());
        Log.i(TAG, "AVGPCR:"+msg.getAvgStatData().getPCRBitrate());
        Log.i(TAG, "AVGJITTER:"+msg.getAvgStatData().getJitter());
        Log.i(TAG, "MAXDF:"+msg.getMaxStatData().getDF());
        Log.i(TAG, "MAXMR:"+msg.getMaxStatData().getMR());
        Log.i(TAG, "MAXPCR:"+msg.getMaxStatData().getPCRBitrate());
        Log.i(TAG, "MAXJITTER:"+msg.getMaxStatData().getJitter());
        Log.i(TAG, "MINDF:"+msg.getMinStatData().getDF());
        Log.i(TAG, "MINMR:"+msg.getMinStatData().getMR());
        Log.i(TAG, "MINRCR:"+msg.getMinStatData().getPCRBitrate());
        Log.i(TAG, "MINJITTER:"+msg.getMinStatData().getJitter());
        mSession.reportHeartBeat(msg);
    }

    @Override
    public void sendSecLevelMsg(SecLevelMsg msg) throws RemoteException {
        Log.i(TAG, "sendSecLevelMsg++++>");
        Log.i(TAG," TimeStamp = " + msg.getTimeStamp());
        Log.i(TAG," STBId = " + msg.getSTBId());
        Log.i(TAG," TransmissionType = " + msg.getTransmissionType());
        Log.i(TAG," IP = " + SQMParserUtil.bytesToIP(msg.getIP()));
        Log.i(TAG," Port = " + msg.getPort());
        Log.i(TAG," STBPort = " + msg.getSTBPort());
        Log.i(TAG," Url = " + msg.getUrl());
        Log.i(TAG," PCRBitrate = " + msg.getPCRBitrate());
        Log.i(TAG," IdealDF = " + msg.getIdealDF());
        Log.i(TAG," MR = " + msg.getMR());
        Log.i(TAG," VMOS = " + msg.getVMOS());
        Log.i(TAG," DF = " + msg.getDF());
        Log.i(TAG," MLR = " + msg.getMLR());
        Log.i(TAG," Jitter = " + msg.getJitter());
        Log.i(TAG," RTPLR = " + msg.getRTPLR());
        Log.i(TAG," RTPNum = " + msg.getRTPNum());
        Log.i(TAG," IPLR = " + msg.getIPLR());
        Log.i(TAG," BufferSize = " + msg.getBufferSize());
        Log.i(TAG," Tr11Num = " + msg.getTr11Num());
        Log.i(TAG," Tr12Num = " + msg.getTr12Num());
        Log.i(TAG," Tr23Num = " + msg.getTr23Num());
        Log.i(TAG," Tr25Num = " + msg.getTr25Num());
        mSession.reportSecMsg(msg);
    }

}