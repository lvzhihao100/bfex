package com.sskj.bfex.common.rxbus2;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lvzhihao on 17-7-25.
 */

public class SubscriberMethod {
    public Method method;
    public ThreadMode threadMode;
    public Class<?> eventType;
    public Object subscriber;
    public int code;

    public SubscriberMethod(Object subscriber, Method method, Class<?> eventType, int code, ThreadMode threadMode) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.subscriber = subscriber;
        this.code = code;
    }

    public void invoke(Object o) {
        try {
            Class[] e = this.method.getParameterTypes();
            if (e != null && e.length == 1) {
                this.method.invoke(this.subscriber, new Object[]{o});
            } else if (e == null || e.length == 0) {
                this.method.invoke(this.subscriber, new Object[0]);
            }
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
        } catch (InvocationTargetException var4) {
            var4.printStackTrace();
        }

    }
}
