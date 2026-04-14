package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.observer.*;

import java.util.List;

public class DungeonEngine {

    private List<Hero> heroes;
    private DungeonBoss boss;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss) {
        this.heroes = heroes;
        this.boss = boss;
    }

    public EncounterResult run() {
        int rounds = 0;

        while (boss.isAlive() && heroes.stream().anyMatch(Hero::isAlive)) {
            rounds++;

            for (Hero h : heroes) {
                if (!h.isAlive()) continue;

                int dmg = h.attack() - boss.defend();
                if (dmg < 0) dmg = 0;

                boss.takeDamage(dmg);
                boss.notifyEvent(new GameEvent(GameEventType.ATTACK_LANDED, h.getName(), dmg));
            }

            for (Hero h : heroes) {
                if (!h.isAlive()) continue;

                int dmg = boss.attack() - h.defend();
                if (dmg < 0) dmg = 0;

                h.takeDamage(dmg);
                boss.notifyEvent(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), dmg));

                if (h.getHp() <= h.getMaxHp() * 0.3) {
                    boss.notifyEvent(new GameEvent(GameEventType.HERO_LOW_HP, h.getName(), h.getHp()));
                }
                if (!h.isAlive()) {
                    boss.notifyEvent(new GameEvent(GameEventType.HERO_DIED, h.getName(), 0));
                }
            }

            if (rounds > 50) break;
        }

        long alive = heroes.stream().filter(Hero::isAlive).count();
        return new EncounterResult(!boss.isAlive(), rounds, (int) alive);
    }
}