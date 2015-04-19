package com.smokecastles.ld32.entities;

import java.util.ArrayList;

public class Entity {

    private ArrayList<Observer> observers;

    public Entity(){
        observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    protected void notifyObservers(Entity entity, Event event){
        for (Observer observer : observers ){
            observer.onNotify(entity, event);
        }
    }

    protected void notify(Observer observer, Entity entity, Event event){
        if (observers.contains(observer)){
            observer.onNotify(entity, event);
        }
    }

}
