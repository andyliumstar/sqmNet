package com.eostek.sqm.msg;

public class MsgHeader {
    public static final int MsgHeaderLength = 28;
    public int command_length;//4
    public short command_version;//2
    public short command_id;//2
    public int sequence_number;//4
    public byte[] checksum = new byte[16];//16
    
    public byte[] command_byte;//12,command_length,command_version,command_id,sequence_number
}
