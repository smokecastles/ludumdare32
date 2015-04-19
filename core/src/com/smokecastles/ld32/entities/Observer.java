package com.smokecastles.ld32.entities;

public abstract class Observer {

    public abstract void onNotify(Entity entity, Event event);
}
