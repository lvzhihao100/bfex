package com.sskj.bfex.common.rxbus2;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by lvzhihao on 17-7-25.
 */

public class RxBus {
    public static final String LOG_BUS = "RXBUS_LOG";
    private static volatile RxBus defaultInstance;
    private Map<Class, List<Disposable>> subscriptionsByEventType = new HashMap();
    private Map<Object, List<Class>> eventTypesBySubscriber = new HashMap();
    private Map<Class, List<SubscriberMethod>> subscriberMethodByEventType = new HashMap();
    private final Subject<Object> bus = PublishSubject.create().toSerialized();

    private RxBus() {
    }

    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if(defaultInstance == null) {
            Class var1 = RxBus.class;
            synchronized(RxBus.class) {
                rxBus = defaultInstance;
                if(defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }

        return rxBus;
    }

    private <T> Flowable<T> toObservable(Class<T> eventType) {
        return this.bus.toFlowable(BackpressureStrategy.BUFFER).ofType(eventType);
    }

    private <T> Flowable<T> toObservable(final int code, final Class<T> eventType) {
        return this.bus.toFlowable(BackpressureStrategy.BUFFER).ofType(RxBus.Message.class).filter(new Predicate<Message >() {

            public boolean test(RxBus.Message o) throws Exception {
                return o.getCode() == code && eventType.isInstance(o.getObject());
            }
        }).map(new Function<Message,Object>() {
            public Object apply(RxBus.Message o) throws Exception {
                return o.getObject();
            }
        }).cast(eventType);
    }

    public void register(Object subscriber) {
        Class subClass = subscriber.getClass();
        Method[] methods = subClass.getDeclaredMethods();
        Method[] var4 = methods;
        int var5 = methods.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if(method.isAnnotationPresent(Subscribe.class)) {
                Class[] parameterType = method.getParameterTypes();
                Class eventType;
                Subscribe sub;
                int code;
                ThreadMode threadMode;
                SubscriberMethod subscriberMethod;
                if(parameterType != null && parameterType.length == 1) {
                    eventType = parameterType[0];
                    this.addEventTypeToMap(subscriber, eventType);
                    sub = (Subscribe)method.getAnnotation(Subscribe.class);
                    code = sub.code();
                    threadMode = sub.threadMode();
                    subscriberMethod = new SubscriberMethod(subscriber, method, eventType, code, threadMode);
                    this.addSubscriberToMap(eventType, subscriberMethod);
                    this.addSubscriber(subscriberMethod);
                } else if(parameterType == null || parameterType.length == 0) {
                    eventType = BusData.class;
                    this.addEventTypeToMap(subscriber, eventType);
                    sub = (Subscribe)method.getAnnotation(Subscribe.class);
                    code = sub.code();
                    threadMode = sub.threadMode();
                    subscriberMethod = new SubscriberMethod(subscriber, method, eventType, code, threadMode);
                    this.addSubscriberToMap(eventType, subscriberMethod);
                    this.addSubscriber(subscriberMethod);
                }
            }
        }

    }

    private void addEventTypeToMap(Object subscriber, Class eventType) {
        Object eventTypes = (List)this.eventTypesBySubscriber.get(subscriber);
        if(eventTypes == null) {
            eventTypes = new ArrayList();
            this.eventTypesBySubscriber.put(subscriber, (List<Class>) eventTypes);
        }

        if(!((List)eventTypes).contains(eventType)) {
            ((List)eventTypes).add(eventType);
        }

    }

    private void addSubscriberToMap(Class eventType, SubscriberMethod subscriberMethod) {
        Object subscriberMethods = (List)this.subscriberMethodByEventType.get(eventType);
        if(subscriberMethods == null) {
            subscriberMethods = new ArrayList();
            this.subscriberMethodByEventType.put(eventType, (List<SubscriberMethod>) subscriberMethods);
        }

        if(!((List)subscriberMethods).contains(subscriberMethod)) {
            ((List)subscriberMethods).add(subscriberMethod);
        }

    }

    private void addSubscriptionToMap(Class eventType, Disposable disposable) {
        Object disposables = (List)this.subscriptionsByEventType.get(eventType);
        if(disposables == null) {
            disposables = new ArrayList();
            this.subscriptionsByEventType.put(eventType, (List<Disposable>) disposables);
        }

        if(!((List)disposables).contains(disposable)) {
            ((List)disposables).add(disposable);
        }

    }

    private void addSubscriber(final SubscriberMethod subscriberMethod) {
        Flowable flowable;
        if(subscriberMethod.code == -1) {
            flowable = this.toObservable(subscriberMethod.eventType);
        } else {
            flowable = this.toObservable(subscriberMethod.code, subscriberMethod.eventType);
        }

        Disposable subscription = this.postToObservable(flowable, subscriberMethod).subscribe(new Consumer() {
            public void accept(Object o) throws Exception {
                RxBus.this.callEvent(subscriberMethod, o);
            }
        });
        this.addSubscriptionToMap(subscriberMethod.subscriber.getClass(), subscription);
    }

    private Flowable postToObservable(Flowable observable, SubscriberMethod subscriberMethod) {
        Scheduler scheduler;
        switch(subscriberMethod.threadMode.ordinal()) {
            case 1:
                scheduler = AndroidSchedulers.mainThread();
                break;
            case 2:
                scheduler = Schedulers.newThread();
                break;
            case 3:
                scheduler = Schedulers.trampoline();
                break;
            default:
                throw new IllegalStateException("Unknown thread mode: " + subscriberMethod.threadMode);
        }

        return observable.observeOn(scheduler);
    }

    private void callEvent(SubscriberMethod method, Object object) {
        Class eventClass = object.getClass();
        List methods = (List)this.subscriberMethodByEventType.get(eventClass);
        if(methods != null && methods.size() > 0) {
            Iterator var5 = methods.iterator();

            while(var5.hasNext()) {
                SubscriberMethod subscriberMethod = (SubscriberMethod)var5.next();
                Subscribe sub = (Subscribe)subscriberMethod.method.getAnnotation(Subscribe.class);
                int c = sub.code();
                if(c == method.code && method.subscriber.equals(subscriberMethod.subscriber) && method.method.equals(subscriberMethod.method)) {
                    subscriberMethod.invoke(object);
                }
            }
        }

    }

    public synchronized boolean isRegistered(Object subscriber) {
        return this.eventTypesBySubscriber.containsKey(subscriber);
    }

    public void unregister(Object subscriber) {
        List subscribedTypes = (List)this.eventTypesBySubscriber.get(subscriber);
        if(subscribedTypes != null) {
            Iterator var3 = subscribedTypes.iterator();

            while(var3.hasNext()) {
                Class eventType = (Class)var3.next();
                this.unSubscribeByEventType(subscriber.getClass());
                this.unSubscribeMethodByEventType(subscriber, eventType);
            }

            this.eventTypesBySubscriber.remove(subscriber);
        }

    }

    private void unSubscribeByEventType(Class eventType) {
        List disposables = (List)this.subscriptionsByEventType.get(eventType);
        if(disposables != null) {
            Iterator iterator = disposables.iterator();

            while(iterator.hasNext()) {
                Disposable disposable = (Disposable)iterator.next();
                if(disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    iterator.remove();
                }
            }
        }

    }

    private void unSubscribeMethodByEventType(Object subscriber, Class eventType) {
        List subscriberMethods = (List)this.subscriberMethodByEventType.get(eventType);
        if(subscriberMethods != null) {
            Iterator iterator = subscriberMethods.iterator();

            while(iterator.hasNext()) {
                SubscriberMethod subscriberMethod = (SubscriberMethod)iterator.next();
                if(subscriberMethod.subscriber.equals(subscriber)) {
                    iterator.remove();
                }
            }
        }

    }

    public void send(int code, Object o) {
        this.bus.onNext(new RxBus.Message(code, o));
    }

    public void post(Object o) {
        this.bus.onNext(o);
    }

    public void send(int code) {
        this.bus.onNext(new RxBus.Message(code, new BusData()));
    }

    private class Message {
        private int code;
        private Object object;
        private Message(int code,  Object o) {
            this.code = code;
            this.object = o;
        }

        private int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private Object getObject() {
            return this.object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }
}
