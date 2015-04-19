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

//    public void removeObserver(Observer observer){
//        observers.remove(observer);
//    }

    protected void notifyObservers(Event event){
        for (Observer observer : observers ){
            observer.onNotify(this, event);
        }
    }

//    protected void notify(Observer observer, Event event){
//        if (observers.contains(observer)){
//            observer.onNotify(this, event);
//        }
//    }

}
