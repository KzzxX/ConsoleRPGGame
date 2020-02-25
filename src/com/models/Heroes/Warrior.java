package com.models.Heroes;

import com.models.EHeroes;
import com.models.Hero;
import com.models.Inventory;
import com.models.Skill;
import com.models.Skills.All.Defence.UpDefence;

import java.io.Serializable;
import java.util.ArrayList;

public class Warrior extends Hero implements Serializable {

    public Warrior(String name, int id, Inventory inventory){
        super();
        this.setId(id);
        this.setMaxHealthPoints(100);
        this.setMaxManaPoints(50);
        this.setProfession(EHeroes.WARRIOR.toString());
        this.setLevel(1);
        this.setAttackAmount(12);
        this.setHealthPoints(100);
        this.setManaPoints(50);
        this.setDefence(8);
        this.setName(name);
        this.setAttackType(AttackType.PHYSICAL);
        this.setPhysicalArmor(9);
        this.setMagicalArmor(7);
        this.setMoney(500);
        this.setInventory(inventory);
        this.setMaxWeight(500);
        this.setCriticalChance(0.12);
        this.setCriticalHit(2.25);
        this.setWin(0);
        this.setLose(0);
        ArrayList<Skill> skills = new ArrayList<>();
        UpDefence sk = new UpDefence();
        skills.add(sk);
        this.setSkills(skills);
    }
    


}
