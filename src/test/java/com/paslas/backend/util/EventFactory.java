package com.paslas.backend.util;

import com.paslas.backend.entity.Event;

public class EventFactory {

    public static Event createEventWithName(String name){
        Event event = new Event();
        event.setName(name);
        return event;
    }
}
