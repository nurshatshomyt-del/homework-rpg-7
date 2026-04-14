package com.narxoz.rpg.boss;

import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class DungeonBoss implements GameObserver {

    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int phase = 1;

    private CombatStrategy strategy;
    private List<GameObserver> observers = new ArrayList<>();

    public DungeonBoss(String name, int hp, int attack, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.strategy = new BalancedStrategy();
    }

    public void addObserver(GameObserver obs) {
        observers.add(obs);
    }

    public void notifyEvent(GameEvent event) {
        for (GameObserver o : observers) {
            o.onEvent(event);
        }
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;

        double percent = (double) hp / maxHp;

        if (percent <= 0.6 && phase == 1) {
            phase = 2;
            notifyEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2));
        }
        if (percent <= 0.3 && phase == 2) {
            phase = 3;
            notifyEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3));
        }

        if (hp == 0) {
            notifyEvent(new GameEvent(GameEventType.BOSS_DEFEATED, name, 0));
        }
    }

    public int attack() {
        return strategy.calculateDamage(attack);
    }

    public int defend() {
        return strategy.calculateDefense(defense);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            if (event.getValue() == 2) strategy = new AggressiveStrategy();
            if (event.getValue() == 3) strategy = new AggressiveStrategy();
        }
    }
}