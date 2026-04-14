package com.narxoz.rpg.strategy;

public class DefensiveStrategy implements CombatStrategy {

    public int calculateDamage(int basePower) {
        return (int)(basePower * 0.7);
    }

    public int calculateDefense(int baseDefense) {
        return (int)(baseDefense * 1.5);
    }

    public String getName() {
        return "Defensive";
    }
}