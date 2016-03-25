package com.eostek.sqm.msg;

public interface SqmMsgCallBack {
    public void onRegisterDone(RegisterBackMsg registerBackMsg);
    
    public void onLevelChange(int level);
}
