package com.eostek.sqm.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.eostek.sqm.msg.MsgHeader;
import com.eostek.sqm.msg.SqmMsgCallBack;
import com.eostek.sqm.utils.SQMConstantUtil;
import com.eostek.sqm.utils.SQMParserUtil;

import android.util.Log;

public class SQMServer extends Thread {
    private boolean mBStop = false;
    
    private ServerSocketChannel  mSocketChannel = null;  
    private SocketChannel  mClentSocket = null;
    private SqmMsgCallBack mSqmMsgCallBack;
    
    private Selector mSelector = null;
    @Override
    public void run() {
        Log.d("SQMServer", "start================>");
        createSocket();
        StartReceAndSendThread();
    }

    public void registerCallBack(SqmMsgCallBack callBack){
        mSqmMsgCallBack = callBack;
    }
    
    public void createSocket(){
        try{
            mSelector = Selector.open();
            
            mSocketChannel = ServerSocketChannel.open();
            mSocketChannel.socket().setReuseAddress(true);  
            mSocketChannel.socket().bind(new InetSocketAddress(SQMConstantUtil.STBPort)); 
            mSocketChannel.configureBlocking(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
 
    public void StartReceAndSendThread(){
        new Thread(){

            @Override
            public void run() {
                try{
                    mSocketChannel.register(mSelector, SelectionKey.OP_ACCEPT);
                    while(!mBStop){
                        mSelector.select(1000);
                        Iterator<SelectionKey> it = mSelector.selectedKeys().iterator();  
                        
                        while (it.hasNext()) {  
                            SelectionKey readyKey = it.next();  
                             
                            Log.d("SQMServer", "readyKey================>"+readyKey.readyOps());
                            switch (readyKey.readyOps()) {
                              case SelectionKey.OP_READ:
                                  reciveMsg(mClentSocket);
                                  break;
                              case SelectionKey.OP_ACCEPT:
                                  accept((ServerSocketChannel )readyKey.channel());
                                  break;
                              }
                            it.remove(); 
                        }                                     
                        
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
        }.start();
    }
  
    
    public void reciveMsg(SocketChannel socket){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ByteBuffer buffer = ByteBuffer.allocate(1024); 
        try{
            byte[] bytes;  
            int size = 0; 
            while((size = socket.read(buffer)) > 0){
                buffer.flip();  
                bytes = new byte[size];  
                buffer.get(bytes);  
                baos.write(bytes);  
                buffer.clear(); 
            }
            bytes = baos.toByteArray();  
            if(null != bytes && bytes.length > MsgHeader.MsgHeaderLength){
                parserMsg(bytes);
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
    
    private void parserMsg(byte[] bytesRes){
        switch(SQMParserUtil.parserHeader(bytesRes, null)){
            case SQMConstantUtil.STBLEVELCHANGE:
               if( handleLevelChange(bytesRes) )
               {
                   sendLevelChangeResponse(0);
                   closeClentSocket();
               }
                break;
            default:
                break;
        }
    }
    
    private boolean handleLevelChange(byte[] bytesRes){
        ByteArrayInputStream bins = new ByteArrayInputStream(bytesRes); 
        DataInputStream dins = new DataInputStream(bins);
        int level = -1;
        try {
            dins.reset();
            dins.skipBytes(MsgHeader.MsgHeaderLength);
            level = dins.readInt();
            mSqmMsgCallBack.onLevelChange(level);
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
                      
    }
    
    public void sendLevelChangeResponse(int levelChangeResponse){
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);
        MsgHeader header = SQMParserUtil.createNewHeader(MsgHeader.MsgHeaderLength+4,SQMConstantUtil.STBLEVELCHANGEBACK); 
        try {
            doutput.write(header.command_byte);
            doutput.write(header.checksum);
            doutput.writeInt(levelChangeResponse);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        sendMsg(mClentSocket,boutput.toByteArray());
    }
    
    private void sendMsg(SocketChannel socket, byte[] bytes){
        try{
            ByteBuffer buffer = ByteBuffer.wrap(bytes);  
            socket.write(buffer);    
        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("SQMServer", "sendMsg====>");
    }
    
    public void accept(ServerSocketChannel  socket){
        try{
            mClentSocket = socket.accept(); 
            mClentSocket.configureBlocking(false);
            mClentSocket.register(mSelector,  SelectionKey.OP_READ);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void closeClentSocket(){
        try {
            mClentSocket.close();
            mSelector.selectNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
