package com.narxoz.rpg.strategy;

public class BalancedStrategy implements CombatStrategy {

    public int calculateDamage(int basePower) {
        return basePower;
    }

    public int calculateDefense(int baseDefense) {
        return baseDefense;
    }

    public String getName() {
        return "Balanced";
    }
}