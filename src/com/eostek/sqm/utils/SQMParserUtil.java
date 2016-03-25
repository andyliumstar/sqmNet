package com.eostek.sqm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.eostek.sqm.msg.MsgHeader;
import com.eostek.sqm.msg.RegisterBackMsg;

public class SQMParserUtil {
    private static byte[] shaEncryToken = {
            (byte) 0xb9, (byte) 0xcf, (byte) 0xbc, (byte) 0xa2, (byte) 0x8c, (byte) 0x0e,
            (byte) 0xa6, (byte) 0xa1, (byte) 0xdd, (byte) 0xce, (byte) 0x0a, (byte) 0xb7,
            (byte) 0x9d, (byte) 0xa3, (byte) 0x99, (byte) 0x8b
    };
 
    private static int mSequenceNumber = 0; //0x00000001-0x7FFFFFFF
    private final static int MAXSEQUENCE = 0x7FFFFFFF;
    
  //  private static String TAG = "SQMParserUtil";
    
    public static MsgHeader createNewHeader(int command_length,short command_id){
        MsgHeader header = new MsgHeader();
        header.command_length = command_length;
        header.command_version = 9;
        header.command_id = command_id;
        header.sequence_number = getSequenceNumber();
        
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);
        try {
            doutput.writeInt(header.command_length);
            doutput.writeShort(header.command_version);
            doutput.writeShort(header.command_id);
            doutput.writeInt(header.sequence_number);
            header.command_byte = boutput.toByteArray();
            SHA256(header.command_byte,header.checksum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return header;
    }
    
    public static int getSequenceNumber(){
        if(++mSequenceNumber > MAXSEQUENCE){
            mSequenceNumber = 0;
        }
        return mSequenceNumber;
    }
    
    public static void getBytesFromIP(String IP,byte[] byteIP){
        if(checkIP(IP)){
            String str1,str2,str3,str4;
            int index1,index2,index3;
            
            index1 = IP.indexOf(".");
            index2 = IP.indexOf(".",index1+1);
            index3 = IP.indexOf(".",index2+1);
            
            str1 = IP.substring(0, index1);
            str2 = IP.substring(index1+1, index2);
            str3 = IP.substring(index2+1, index3);
            str4 = IP.substring(index3+1);
            
            byteIP[0] = (byte) Integer.parseInt(str1);
            byteIP[1] = (byte) Integer.parseInt(str2);
            byteIP[2] = (byte) Integer.parseInt(str3);
            byteIP[3] = (byte) Integer.parseInt(str4);
        }
    }
    
    public static String bytesToIP(byte[] byteIP){
        int val1,val2,val3,val4;
        val1 = ((int) byteIP[0]) & 0xff;
        val2 = ((int) byteIP[1]) & 0xff;
        
        val3 = ((int) byteIP[2]) & 0xff;
        val4 = ((int) byteIP[3]) & 0xff;
        return val1 + "." + val2 +"." +val3 +"." + val4;
    }
    
    public static boolean checkIP(String IP){
        if(null != IP){
            String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(IP);
            return matcher.matches();
        }
        return false;
    }
    
    public static void SHA256(byte[] bytesIn, byte[] byteOut){
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sha256.update(bytesIn);
        sha256.update(shaEncryToken);
        
        byte[] shaBytes = sha256.digest();
        
        for (int i = 0; i < byteOut.length; i++) {
            byteOut[i] = shaBytes[i];
        }
        
    }
    
    public static int parserHeader(byte[] msgIn, MsgHeader header){
        ByteArrayInputStream bins = new ByteArrayInputStream(msgIn); 
        DataInputStream dins = new DataInputStream(bins);
        int value = 0;
        try {
            dins.reset();
            if(null == header){
                dins.skipBytes(6);
                value= dins.readShort();
            }else{
                header.command_length = dins.readInt();
                header.command_version = dins.readShort();
                header.command_id = dins.readShort();
                header.sequence_number = dins.readInt();
                dins.read(header.checksum); 
                value = header.command_id;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return value;
    }
    
    public static int parserRegister(byte[] msgIn, RegisterBackMsg register){
        ByteArrayInputStream bins = new ByteArrayInputStream(msgIn); 
        DataInputStream dins = new DataInputStream(bins);
        int value = -1;
        try {
            dins.reset();
            dins.skipBytes(MsgHeader.MsgHeaderLength);
            if(null == register){
                value = dins.readInt();
            }else{
                register.mResult = dins.readInt();
                register.mLevel = dins.readInt();
                register.mMonitorPoint = dins.readInt();
                register.mFrameAnalyse = dins.readInt();
                register.mSignalFlag = dins.readInt();
                dins.read(register.mMasterPCFIP);
                dins.read(register.mSlavePCFIP);
                register.mIndexCount = dins.readInt();
                
                dins.reset();
                dins.skipBytes(msgIn.length-4*4);
                register.mIsVip = dins.readInt();
                /*register.result = dins.readInt();
                register.result = dins.readInt();
                register.result = dins.readInt();
                register.result = dins.readInt();
                register.result = dins.readInt();
                register.result = dins.readInt();
                register.result = dins.readInt();
                register.result = dins.readInt();*/
                value = register.mResult;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return value;
    }
}
