package com.smokecastles.ld32.entities;

public class Event {

    public enum Type{
        HIT_BY_ENEMY,
        WIN,
        GAME_OVER
    }
    private Type type;

    public Event(Type type_){
        type = type_;
    }

    public Type type(){
        return type;
    }

}
