package com.tt.customsocket;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresPermission;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tt.customsocket.interfaces.SocketMessageListner;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class WebSocketClient {
    private Context context;
    private HashMap<String, SocketMessageListner> messageListners;

    private org.java_websocket.client.WebSocketClient mWebSocketClient;

    public WebSocketClient(Context context) {
        this.context = context;
        this.messageListners =  new HashMap<>();
    }
    public WebSocketClient connect(String url){
        webSocketConnnet(url);
        return this;
    }
    public WebSocketClient on(String connectionName,SocketMessageListner listner){
        this.messageListners.put(connectionName,listner);
        return this;
    }

    public WebSocketClient off(String connectionName){
        this.messageListners.remove(connectionName);
        return this;
    }
    public WebSocketClient close(){
        this.messageListners.remove(this.messageListners);
        return this;
    }

    public void setMessage(String connectionName,String message) {
        try {

            JSONObject data = new JSONObject();
            data.put("mobile_number", message);

            JSONObject obj = new JSONObject();
            obj.put("functionName", connectionName);

            obj.put("data",data);
            mWebSocketClient.send(obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void webSocketConnnet(String url){
        URI uri;
        try {
            uri = new URI(url);
            mWebSocketClient = new org.java_websocket.client.WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    String s = "";
                }

                @Override
                public void onMessage(String message) {
                    try {
                        Gson g = new Gson();
                        JSONObject obj = new JSONObject(message);

                        //Object p = g.fromJson(message, Object.class);
                        messageListners.get(obj.getJSONObject("functionName")).onMessage(g.fromJson(message, Object.class));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    String s = "";
                }

                @Override
                public void onError(Exception ex) {
                    String s = "";
                }
            };
            mWebSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

    }

}
