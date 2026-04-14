package com.narxoz.rpg;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.BattleLogger;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Hero h1 = new Hero("Knight", 100, 20, 15, new BalancedStrategy());
        Hero h2 = new Hero("Mage", 80, 25, 10, new AggressiveStrategy());
        Hero h3 = new Hero("Tank", 120, 15, 20, new DefensiveStrategy());

        DungeonBoss boss = new DungeonBoss("Dragon", 300, 25, 15);

        BattleLogger logger = new BattleLogger();

        boss.addObserver(logger);
        boss.addObserver(boss);

        List<Hero> heroes = Arrays.asList(h1, h2, h3);

        DungeonEngine engine = new DungeonEngine(heroes, boss);
        EncounterResult result = engine.run();

        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds: " + result.getRoundsPlayed());
        System.out.println("Alive heroes: " + result.getSurvivingHeroes());
    }
}