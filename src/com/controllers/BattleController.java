package com.controllers;

import com.models.Boss;
import com.models.Hero;
import com.models.User;

import java.util.Random;

public class BattleController {

    private Hero currentHero;
    private Boss currentBoss;
    private UserController userController;
    private double heroHp;
    private double bossHp;
    private boolean isEnd = false;
    private boolean bossWin;
    private boolean heroWin;
    public BattleController(UserController userController,Hero currentHero, Boss currentBoss){
        this.currentHero = currentHero;
        this.currentBoss = currentBoss;
        this.userController = userController;
        heroHp = currentHero.getHealthPoints() + (currentHero.getPhysicalArmor()*0.1);
        bossHp = currentBoss.getHealthPoints() + (currentBoss.getPhysicalArmor()*0.1);
    }


    public void battle() {

        System.out.printf("Hero hp: %s \t x Boss hp: %s \n",
                heroHp,
                bossHp);
        if (heroWin){
            System.out.printf("Hero %s win \n", currentHero.getName());
        }else if(bossWin){
            System.out.printf("Boss %s win \n", currentBoss.getName());
        }


    }

    public boolean isEnd() {
        return isEnd;
    }
    public void setEnd(boolean isEnd){
        this.isEnd = isEnd;
    }

    public void attack() {

        double heroCritical = Math.random();
        double bossCritical = Math.random();
        if (currentHero.getCriticalChance() > heroCritical){
            System.out.println("Hero critical hit");
            bossHp -= currentHero.getAttackAmount() * currentHero.getCriticalHit();
        }else {
            bossHp -= currentHero.getAttackAmount();
        }

        if (currentBoss.getCriticalChance() > bossCritical){
            System.out.println("Boss critical hit");
            heroHp -= currentBoss.getAttackAmount()*currentBoss.getCriticalHit();
        }else {
            heroHp -= currentBoss.getAttackAmount();
        }
        if (heroHp <= 0) {
            isEnd = true;
        }
        if (bossHp <= 0){
            isEnd = true;
        }
        if (isEnd){
            if (heroHp<=0){
                bossWin =true;
                userController.getCurrentHero().setLose(currentHero.getLose()+1);
            }else {
                userController.getCurrentHero().setWin(currentHero.getWin() + 1);
                heroWin = true;
                userController.getCurrentHero().levelUp(currentBoss.getExperience());
            }
            userController.save();
        }


    }

}
