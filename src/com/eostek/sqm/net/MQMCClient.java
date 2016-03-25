package com.eostek.sqm.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.eostek.sqm.msg.MsgHeader;
import com.eostek.sqm.msg.RegisterBackMsg;
import com.eostek.sqm.msg.RegisterMsg;
import com.eostek.sqm.msg.SqmMsgCallBack;
import com.eostek.sqm.utils.SQMConstantUtil;
import com.eostek.sqm.utils.SQMParserUtil;

import android.os.Handler;
import android.util.Log;

public class MQMCClient {
    private static MQMCClient mMQMCClient;
    private String TAG = "MQMCClient";
    private SqmMsgCallBack mSqmMsgCallBack;
    
    ///*// register msg
    private Handler mMsgHandler;
    private int mDelaySendRegister = 0;
    private RegisterMsg mRegisterMsg = null;
    private final int mFirstDelayMs = 4000;
    private final int mRegisteOK = 0;
    //*/
    private boolean mBStart = false;
    private Queue<byte[]> mSendMsgQueue = new LinkedList<byte[]>();
    
    private SocketChannel mSocketChannel = null;  
    private SocketAddress  mServerAddress = null;
    private String mServerIP;
    private int mServerPort;
    
    private Selector mSelector = null;
    private final int SelectorTimeOut = 1000;//ms
    
    private MQMCClient() {
        mMsgHandler = new Handler();
    }

    public static MQMCClient getInstance(){
        if(null == mMQMCClient){
            mMQMCClient = new MQMCClient();
        }
        return mMQMCClient;
    }
    
    public void registerCallBack(SqmMsgCallBack callBack){
        mSqmMsgCallBack = callBack;
    }
    
    public void updateServer(String serverIP, int serverPort) {
        mServerIP = serverIP;
        mServerPort = serverPort;
    }
    
    public void start() {
        if(mBStart){
            return;
        }
        new Thread(){

            @Override
            public void run() {
                createSocket();
                connectToServer();
                StartReceAndSendLoop();
            }
        }.start();
        mBStart = true;
    }
    
    private void createSocket(){
        try{
            Log.d(TAG, "createSocket===>"+mServerIP+" "+mServerPort);
            
            mSocketChannel = SocketChannel.open();
            mServerAddress = new InetSocketAddress(mServerIP,mServerPort); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void connectToServer(){
        try{
            mSocketChannel.configureBlocking(true);
           if( mSocketChannel.connect(mServerAddress) ){
               Log.d(TAG, "connectToServer=======>");
               mSocketChannel.configureBlocking(false);
           }else{
               Log.d(TAG, "connectToServer=======>failed");
           }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void StartReceAndSendLoop(){
        try{
            mSelector = Selector.open();
            mSocketChannel.register(mSelector, SelectionKey.OP_READ);
            while(mBStart){
                mSelector.select(SelectorTimeOut);
                
                Iterator<SelectionKey> it = mSelector.selectedKeys().iterator();  
                while (it.hasNext()) {  
                    SelectionKey readyKey = it.next();  
                    it.remove();  
                    Log.d(TAG, "readyKey================>"+readyKey.readyOps()); 
                    switch (readyKey.readyOps()) {
                      case SelectionKey.OP_READ:
                          receiveMsg((SocketChannel)readyKey.channel());
                          break;
                     }
                } 
                sendMsg(mSocketChannel);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void sendRegisterMsg(RegisterMsg body){
        Log.d(TAG, "createRegisterMsg=======>");
        mRegisterMsg = body;
        mMsgHandler.postDelayed(mRegisterRun, mDelaySendRegister);
    }

    private Runnable mRegisterRun = new Runnable() {
        
        @Override
        public void run() {
            
            ByteArrayOutputStream boutput = new ByteArrayOutputStream();
            DataOutputStream doutput = new DataOutputStream(boutput);
            MsgHeader header = SQMParserUtil.createNewHeader(MsgHeader.MsgHeaderLength+mRegisterMsg.length(),SQMConstantUtil.STBREGISTE); 
            try {
                //header
                doutput.write(header.command_byte);
                doutput.write(header.checksum);
                //body
                doutput.write(mRegisterMsg.getSTBId().getBytes());
                doutput.write(mRegisterMsg.getUserId().getBytes());
                doutput.write(mRegisterMsg.getMacAddress().getBytes());
                doutput.write(mRegisterMsg.getSTBIP());
                doutput.writeInt(mRegisterMsg.getSTBPort());
                doutput.write(mRegisterMsg.getPPPoEAccount().getBytes());
                doutput.write(mRegisterMsg.getEPGIP());
                doutput.write(mRegisterMsg.getSTBVersion().getBytes());
                doutput.write(mRegisterMsg.getSTBProbeVersion().getBytes());
                
                queueMsg(boutput.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            mDelaySendRegister = (mDelaySendRegister == 0 )? mFirstDelayMs : mDelaySendRegister*2;
            mMsgHandler.postDelayed(mRegisterRun, mDelaySendRegister);
        }
    };
    
    public void queueMsg(byte[] msg){
        start();
        synchronized(mSendMsgQueue){
            mSendMsgQueue.offer(msg);
        }
    }
    
    public byte[] dequeueMsg(){
        start();
        synchronized(mSendMsgQueue){
            return mSendMsgQueue.poll();
        } 
    }
    
    public boolean hasMsgToSend(){
        synchronized(mSendMsgQueue){
            return !(mSendMsgQueue.isEmpty());
        } 
    }
    
    public void closeSocket(){
        try {
            mBStart = false;
            mSocketChannel.close();
            mSelector.selectNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void receiveMsg(SocketChannel socket){
        Log.d(TAG,"recieveMsg===========>");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ByteBuffer buffer = ByteBuffer.allocate(1024); 
        try{
            byte[] bytes;  
            int size = 0; 
            while( (size = socket.read(buffer)) > 0 ){
                buffer.flip();  
                bytes = new byte[size];  
                buffer.get(bytes);  
                baos.write(bytes);  
                buffer.clear(); 
            }
            bytes = baos.toByteArray();  
            Log.d(TAG,"Msg==="+bytes.length);
            if(size == -1){
                closeSocket();
            }
            
            if(null != bytes && bytes.length > MsgHeader.MsgHeaderLength){
                parserResponse(bytes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {  
            try {  
                baos.close();  
            } catch(Exception ex) {
                ex.printStackTrace();
            }  
        }  
    }
    
    public void sendMsg(SocketChannel socket){
        if(hasMsgToSend()){
            if(!mBStart){
                start();
                return;
            }
            
            try{
                if (socket.isConnected()){
                    byte[] bytes = dequeueMsg();
                    Log.d(TAG,"sendMsg===========>"+ bytes.length);
                    ByteBuffer buffer = ByteBuffer.wrap(bytes); 
                    while(buffer.hasRemaining()){
                        socket.write(buffer);  
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void parserResponse(byte[] bytesRes){
        switch(SQMParserUtil.parserHeader(bytesRes, null)){
            case SQMConstantUtil.STBREGISTEBACK:
                handleRegisterRes(bytesRes);
                break;
            default:
                break;
        }
    }
    
    private void handleRegisterRes(byte[] bytesRes){
        RegisterBackMsg registerBackMsg = new RegisterBackMsg();
        SQMParserUtil.parserRegister(bytesRes, registerBackMsg);
        if(registerBackMsg.mResult == mRegisteOK){
            mMsgHandler.removeCallbacks(mRegisterRun);
            mSqmMsgCallBack.onRegisterDone(registerBackMsg);
        }
    }
}
