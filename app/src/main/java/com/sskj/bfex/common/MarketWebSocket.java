package com.sskj.bfex.common;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * @author Hey
 * @update 新增连接失败重试
 * created at 2018/4/9 13:03
 */

public class MarketWebSocket extends WebSocketListener {
    private String message;
    private WebSocket mWebSocket;
    private MarketWebSocketListener mListener;
    private String tag;
    private String url;
    //连接失败重试次数
    private int retryCount = 0;
    //最大重试次数
    private int maxTryCount = 10;

    private boolean isUserCancel = false;

    private boolean isLog = true;



    Handler retryHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            init(url);
        }
    };

    public MarketWebSocket(String url, String tag, String message) {
        this.url = url;
        init(url);
        this.tag = tag;
        this.message = message;
    }


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        mWebSocket = webSocket;
        if (response.code() == 101) {
            if (isLog) {
                Log.e(tag + "WebSocket>>:success", "连接成功");
            }

            if (mWebSocket.send(message)) {
                if (isLog) {
                    Log.e(tag + "WebSocket>>:send", message);
                }
            }

            if (retryHandler != null) {
                retryHandler.removeCallbacks(runnable);
            }

        }
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        if (mListener != null) {
            mListener.onMessage(text);
        }
        if (isLog) {
            Log.e(tag + "WebSocket>>Msg>>", text);
        }

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        mWebSocket.cancel();
        if (isLog) {
            Log.e(tag + "WebSocket>>Close:", "code" + code + reason);
        }

    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (!isUserCancel) {
            if (retryCount < maxTryCount) {
                retryHandler.postDelayed(runnable, retryCount * 5000);
                if (isLog) {
                    Log.e(tag + "WebSocket>>retry:", retryCount + "");
                }
                retryCount++;
            }
            if (webSocket != null) {
                webSocket.cancel();
            }
        }
        if (isLog) {
            Log.e(tag + "WebSocket>>Fail:", t.toString());
        }

    }


    /**
     * 初始化WebSocket服务器
     *
     * @param url 链接地址
     */
    private void init(String url) {
        isUserCancel = false;
        try {
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(13, TimeUnit.SECONDS).build();
            Request request = new Request.Builder().url(url).build();
            client.newWebSocket(request, this);
            client.dispatcher().executorService().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @return 是否成功
     */
    public boolean sendMessage(String message) {
        if (mWebSocket != null) {
            return mWebSocket.send(message);
        } else {
            return false;
        }
    }

    /**
     * 关闭
     */
    public void closeWebSocket() {
        if (mWebSocket != null) {
            isUserCancel = true;
            mWebSocket.close(1000, "主动关闭");
        }
        retryHandler.removeCallbacks(runnable);
    }

    public void setListener(MarketWebSocketListener listener) {
        this.mListener = listener;
    }


    public interface MarketWebSocketListener {
        void onMessage(String message);
    }
}
