package com.example.routersever.controller.eventbus;

import com.example.routersever.controller.eventbus.IEventBus.IEventBus;
import com.example.routersever.controller.eventbus.IEventBusFactory.IEventBusFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/22 14:07.
 * Function :
 */
public class EventBusFactory implements IEventBusFactory {

    private EventBusFactory() {
    }

    private static class Factory {
        private static final EventBusFactory mInstance = new EventBusFactory();
    }

    public static IEventBusFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IEventBus getEventBus() {
        return EventBusImpl.getInstance();
    }
}
