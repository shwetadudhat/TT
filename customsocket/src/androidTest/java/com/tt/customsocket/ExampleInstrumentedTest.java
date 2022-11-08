package com.tt.customsocket;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tt.customsocket.interfaces.SocketMessageListner;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    WebSocketClient webSocketClient;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.tt.customsocket.test", appContext.getPackageName());

        socketTest(appContext);
    }
    public void socketTest(Context context) {
        try {
            webSocketClient = new WebSocketClient(context);

            webSocketClient.on("login", new SocketMessageListner() {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    String s = "";
                    webSocketClient.setMessage("login", "7897845642");
                }

                @Override
                public void onMessage(Object message) {
                    String s = "";
                }

                @Override
                public void onClose(int code, Object reason, boolean remote) {
                    String s = "";
                }

                @Override
                public void onError(Exception ex) {
                    String s = "";
                }
            });
            webSocketClient.connect("url");
        }catch (Exception e){
            System.out.println("error-"+e.getMessage());
        }
    }
    @Test
    public void jsonObjectTest(){
        try {

            JSONObject data = new JSONObject();
            data.put("mobile_number", "7894561230");

            JSONObject obj = new JSONObject();
            obj.put("functionName", "login");
            obj.put("data", data);
            System.out.println("login test-"+ obj.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}