package com.tt.customsocket.interfaces;

import org.java_websocket.handshake.ServerHandshake;

public interface SocketMessageListner {
    void onOpen(ServerHandshake handshakedata);
    void onMessage(Object message);
    void onClose(int code, Object reason, boolean remote);
    public void onError(Exception ex);
}
