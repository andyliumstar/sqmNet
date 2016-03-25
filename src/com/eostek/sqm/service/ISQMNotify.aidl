package com.eostek.sqm.service;

import com.eostek.sqm.msg.HeartBeatMsg;
import com.eostek.sqm.msg.RegisterMsg;
import com.eostek.sqm.msg.SecLevelMsg;
import com.android.iptv.browser.datacollector.service.ICollectorCallBack;

interface ISQMNotify {
    void registerCollectorCallBack(com.android.iptv.browser.datacollector.service.ICollectorCallBack cb);
    void sendRegisterMsg(in com.eostek.sqm.msg.RegisterMsg msg);
    void sendHeartBeatMsg(in com.eostek.sqm.msg.HeartBeatMsg msg);
    void sendSecLevelMsg(in com.eostek.sqm.msg.SecLevelMsg msg);
}