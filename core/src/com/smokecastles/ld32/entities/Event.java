package com.smokecastles.ld32.entities;

public class Event {

    private enum Type{
        DECREASE_LIFE,
        INCREASE_LIFE,
        PLAY_SOUND,
        WIN,
        GAME_OVER
    }
    private Type type;

    public Event(Type type_){
        type = type_;
    }

}
