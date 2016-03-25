package com.eostek.sqm.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
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

import android.os.Handler;
import android.util.Log;

import com.eostek.sqm.msg.HeartBeatMsg;
import com.eostek.sqm.msg.MsgHeader;
import com.eostek.sqm.msg.SecLevelMsg;
import com.eostek.sqm.msg.SqmMsgCallBack;
import com.eostek.sqm.utils.SQMConstantUtil;
import com.eostek.sqm.utils.SQMParserUtil;

public class PCFClient {
    private static PCFClient mPCFClient;
    private String TAG = "PCFClient";
    
    private SqmMsgCallBack mSqmMsgCallBack;
    private Handler mMsgHandler;
    private int mReportLevel = 1;//0,no need report,1,heart beat,4 maio ji
    private boolean mBStart = false;
    private Queue<byte[]> mSendMsgQueue = new LinkedList<byte[]>();
    
    private SocketChannel mSocketChannel = null;  
    private SocketAddress  mServerAddress = null;
    private String mServerIP;
    private String mServerBackUpIP;
    private int mServerPort;
    
    private Selector mSelector = null;
    private final int SelectorTimeOut = 1000;//ms
    private final int MIXTryConnectTimes = 3;
    private  int TryConnectDuration = 2000;//ms
    private int mTryConnectTimes = 0;
    
    private PCFClient() {
        mMsgHandler = new Handler();
    }

    public static PCFClient getInstance(){
        if(null == mPCFClient){
            mPCFClient = new PCFClient();
        }
        return mPCFClient;
    }
    
    public void registerCallBack(SqmMsgCallBack callBack){
        mSqmMsgCallBack = callBack;
    }
    
    public void updateServer(String serverIP, String serverBackUpIP, int serverPort) {
        mServerIP = serverIP;
        mServerBackUpIP = serverBackUpIP;
        mServerPort = serverPort;
    }
    
    public void updateLevel(int level){
        Log.d(TAG,"updateLevel======>" + level);
        mReportLevel = level;
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
            mSocketChannel.configureBlocking(false);
            if (mSocketChannel.connect(mServerAddress)){
                Log.d(TAG, "connectToServer===>");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private Runnable tryConnectRun = new Runnable() {
        
        @Override
        public void run() {
            TryConnectDuration = TryConnectDuration * 2;
            if(++mTryConnectTimes >= MIXTryConnectTimes){
                if (!mServerIP.equals(mServerBackUpIP)){
                    mServerIP = mServerBackUpIP;
                    mTryConnectTimes = 0;
                    start();
                }
            }else{
                start();
            }
        }
    }; 
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
                
                mSocketChannel.finishConnect();
                if(mSocketChannel.isConnected()){
                    mTryConnectTimes = 0;
                    sendMsg(mSocketChannel);
                }else{
                    Log.d(TAG, "reConnect=============>");
                    closeSocket();
                    mMsgHandler.postDelayed(tryConnectRun, TryConnectDuration);
                }
            }
        }catch(Exception e){
            closeSocket();
            e.printStackTrace();
        }
    }
    
    public void sendHeartBeatMsg(HeartBeatMsg heartBeatMsg){
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);
        MsgHeader header = SQMParserUtil.createNewHeader(MsgHeader.MsgHeaderLength+heartBeatMsg.length(),SQMConstantUtil.STBHEARTBEAT); 
        try {
            //header
            doutput.write(header.command_byte);
            doutput.write(header.checksum);
            //body
            doutput.writeLong(heartBeatMsg.getTimeStamp());
            doutput.write(heartBeatMsg.getSTBId().getBytes());
            doutput.write(heartBeatMsg.getUserId().getBytes());
            doutput.write(heartBeatMsg.getMacAddress().getBytes());
            doutput.write(heartBeatMsg.getSTBIP());
            doutput.writeInt(heartBeatMsg.getTransmissionType());
            doutput.write(heartBeatMsg.getIP());
            doutput.writeInt(heartBeatMsg.getPort());
            doutput.writeInt(heartBeatMsg.getSTBPort());
            doutput.write(heartBeatMsg.getUrl().getBytes());
            doutput.writeInt(heartBeatMsg.getStatus());
            doutput.write(heartBeatMsg.getEPGIP());
            doutput.writeInt(heartBeatMsg.getStatType());
            doutput.writeInt(heartBeatMsg.getChannelPlayDuration() );
            doutput.writeInt(heartBeatMsg.getChannelAlarmDuration());
            doutput.writeInt(heartBeatMsg.getPlayDuration());
            doutput.writeInt(heartBeatMsg.getAlarmDuration());
            doutput.writeInt(heartBeatMsg.getMinVmosCause());
            doutput.writeInt(heartBeatMsg.getCPUFR());
            doutput.writeInt(heartBeatMsg.getSwitchNum());
            doutput.writeInt(heartBeatMsg.getSwitchSuccessNum());
            doutput.writeInt(heartBeatMsg.getSwitchAvgDelay());
            doutput.writeInt(heartBeatMsg.getSwitchDelayCheckOutNum());
            doutput.writeInt(heartBeatMsg.getVODNum());
            doutput.writeInt(heartBeatMsg.getVODSuccessNum());
            doutput.writeInt(heartBeatMsg.getVODAvgDelay());
            doutput.writeInt(heartBeatMsg.getVODDelayCheckOutNum());
            doutput.writeInt(heartBeatMsg.getBTVNum());
            doutput.writeInt(heartBeatMsg.getBTVSuccessNum());
            doutput.writeInt(heartBeatMsg.getBTVAvgDelay());
            doutput.writeInt(heartBeatMsg.getBTVDelayCheckOutNum());
            doutput.writeInt(heartBeatMsg.getEPGNum());
            doutput.writeInt(heartBeatMsg.getEPGSuccessNum());
            doutput.writeInt(heartBeatMsg.getEPGAvgDelay());
            doutput.writeInt(heartBeatMsg.getEPGDelayCheckOutNum());
            doutput.write(heartBeatMsg.getEPGErrorCode().getBytes());
            doutput.writeInt(heartBeatMsg.getStopNum());
            doutput.writeInt(heartBeatMsg.getStopDuration());
            doutput.writeInt(heartBeatMsg.getChangeNum());
            
            doutput.writeInt(heartBeatMsg.getAvgStatData().getStatDataType());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getMR());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getVMOS());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getDF());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getMLR());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getJitter());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getIPLR());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getTCPRR());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getCPUR());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getPCRBitrate());
            doutput.writeInt(heartBeatMsg.getAvgStatData().getBufferSize());
            
            doutput.writeInt(heartBeatMsg.getMaxStatData().getStatDataType());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getMR());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getVMOS());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getDF());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getMLR());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getJitter());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getIPLR());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getTCPRR());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getCPUR());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getPCRBitrate());
            doutput.writeInt(heartBeatMsg.getMaxStatData().getBufferSize());
            
            doutput.writeInt(heartBeatMsg.getMinStatData().getStatDataType());
            doutput.writeInt(heartBeatMsg.getMinStatData().getMR());
            doutput.writeInt(heartBeatMsg.getMinStatData().getVMOS());
            doutput.writeInt(heartBeatMsg.getMinStatData().getDF());
            doutput.writeInt(heartBeatMsg.getMinStatData().getMLR());
            doutput.writeInt(heartBeatMsg.getMinStatData().getJitter());
            doutput.writeInt(heartBeatMsg.getMinStatData().getIPLR());
            doutput.writeInt(heartBeatMsg.getMinStatData().getTCPRR());
            doutput.writeInt(heartBeatMsg.getMinStatData().getCPUR());
            doutput.writeInt(heartBeatMsg.getMinStatData().getPCRBitrate());
            doutput.writeInt(heartBeatMsg.getMinStatData().getBufferSize());
            queueMsg(boutput.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendSecLevelMsg(SecLevelMsg secLevelMsg){
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);
        MsgHeader header = SQMParserUtil.createNewHeader(MsgHeader.MsgHeaderLength+secLevelMsg.length(),SQMConstantUtil.STBSECLEVEL); 
        try {
            //header
            doutput.write(header.command_byte);
            doutput.write(header.checksum);
            //body
            doutput.writeLong(secLevelMsg.getTimeStamp());
            doutput.write(secLevelMsg.getSTBId().getBytes());
            doutput.writeInt(secLevelMsg.getTransmissionType());
            doutput.write(secLevelMsg.getIP());
            doutput.writeInt(secLevelMsg.getPort());
            doutput.writeInt(secLevelMsg.getSTBPort());
            doutput.write(secLevelMsg.getUrl().getBytes());
            doutput.writeInt(secLevelMsg.getPCRBitrate());
            doutput.writeInt(secLevelMsg.getIdealDF() );
            doutput.writeInt(secLevelMsg.getMR());
            doutput.writeInt(secLevelMsg.getVMOS());
            doutput.writeInt(secLevelMsg.getDF());
            doutput.writeInt(secLevelMsg.getMLR());
            doutput.writeInt(secLevelMsg.getJitter());
            doutput.writeInt(secLevelMsg.getRTPLR());
            doutput.writeInt(secLevelMsg.getRTPNum());
            doutput.writeInt(secLevelMsg.getRTPResume());
            doutput.writeInt(secLevelMsg.getFECResume());
            doutput.writeInt(secLevelMsg.getRETResume());
            doutput.writeInt(secLevelMsg.getRTPDisorder());
            doutput.writeInt(secLevelMsg.getRTPRepeat());
            doutput.writeInt(secLevelMsg.getRTPLPAvg());
            doutput.writeInt(secLevelMsg.getRTPLPMax());
            
            doutput.writeInt(secLevelMsg.getRTPLPMin());
            doutput.writeInt(secLevelMsg.getRTPLDAvg());
            doutput.writeInt(secLevelMsg.getRTPLDMax());
            doutput.writeInt(secLevelMsg.getRTPLDMin());
            doutput.writeInt(secLevelMsg.getRTPLPE());
            doutput.writeInt(secLevelMsg.getRTPLDE());
            doutput.writeInt(secLevelMsg.getIPLR());
            doutput.writeInt(secLevelMsg.getTCPRR());
            doutput.writeInt(secLevelMsg.getFrameNum());
            doutput.writeInt(secLevelMsg.getResumeFrame());
            doutput.writeInt(secLevelMsg.getResumeRatio());
            doutput.writeInt(secLevelMsg.getIFrameBroken());
            doutput.writeInt(secLevelMsg.getIFrameResume());
            doutput.writeInt(secLevelMsg.getPFrameBroken());
            doutput.writeInt(secLevelMsg.getPFrameResume());
            
            doutput.writeInt(secLevelMsg.getBFrameBroken());
            doutput.writeInt(secLevelMsg.getBFrameResume());
            doutput.writeInt(secLevelMsg.getBufferSize());
            doutput.writeInt(secLevelMsg.getTr11Num());
            doutput.writeInt(secLevelMsg.getTr12Num());
            doutput.writeInt(secLevelMsg.getTr13Num());
            doutput.writeInt(secLevelMsg.getTr14Num());
            doutput.writeInt(secLevelMsg.getTr15Num());
            doutput.writeInt(secLevelMsg.getTr16Num());
            doutput.writeInt(secLevelMsg.getTr21Num());
            doutput.writeInt(secLevelMsg.getTr22Num());
            doutput.writeInt(secLevelMsg.getTr23Num());
            doutput.writeInt(secLevelMsg.getTr25Num());
            doutput.writeInt(secLevelMsg.getPreciseData());
            doutput.writeInt(secLevelMsg.getVmosCause());
           
            queueMsg(boutput.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    
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
            while( (size = socket.read(buffer)) > 0){
                buffer.flip();  
                bytes = new byte[size];  
                buffer.get(bytes);  
                baos.write(bytes);  
                buffer.clear(); 
            }
            bytes = baos.toByteArray();  
            Log.d(TAG,"Msg==="+bytes.length);
            
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
        }else{
            closeSocket();
        }
    }
    
    private void parserResponse(byte[] bytesRes){
        switch(SQMParserUtil.parserHeader(bytesRes, null)){
            case SQMConstantUtil.STBHEARTBEATBACK:
                handleHeartBeatRes(bytesRes);
                break;
            default:
                break;
        }
    }
    
    private void handleHeartBeatRes(byte[] bytesRes){
        Log.d(TAG,"handleHeartBeatRes===========>");
        ByteArrayInputStream bins = new ByteArrayInputStream(bytesRes); 
        DataInputStream dins = new DataInputStream(bins);
        int level = -1;
        try {
            dins.reset();
            dins.skipBytes(MsgHeader.MsgHeaderLength);
            level = dins.readInt();
            Log.d(TAG,"level==="+level);
            if(mReportLevel != level && level != -1){
                updateLevel(level);
                mSqmMsgCallBack.onLevelChange(mReportLevel);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
