package com.example.liangge.rxjavatest.common.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 类名称：RxBus
 * 类描述：RxBus
 * 创建人：ghl
 * 创建时间：2017/4/25 14:52
 * 修改人：ghl
 * 修改时间：2017/4/25 14:52
 *
 * @version v1.0
 */

public class RxBus {
    private final Subject<Object> bus;

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }

    private RxBus() {

        bus = PublishSubject.create().toSerialized();

    }

    public static synchronized RxBus getInstance() {
        return RxBusHolder.instance;
    }

    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 接受消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
