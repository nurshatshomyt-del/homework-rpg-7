package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {
    public void onEvent(GameEvent event) {
        System.out.println("[LOG] " + event.getType() + " by " + event.getSourceName() + " value=" + event.getValue());
    }
}