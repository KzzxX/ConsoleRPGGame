package com.controllers;

import com.models.Boss;
import com.models.Hero;
import com.models.Item;
import com.models.Skill;
import com.models.Items.HealLevelOne;
import com.models.Skills.SkillCategory;
import com.sun.istack.internal.NotNull;

public class BattleController {

    private Hero currentHero;
    private Boss currentBoss;
    private UserController userController;
    private boolean isEnd = false;
    private boolean bossWin;
    private boolean heroWin;
    private Skill skill;

    private double heroHp;
    private double bossHp;

    public BattleController(UserController userController,Hero currentHero, Boss currentBoss){
        this.currentHero = currentHero;
        this.currentBoss = currentBoss;
        this.userController = userController;

        heroHp = currentHero.getHealthPoints() + (currentHero.getDefence() * 0.8);
        bossHp = currentBoss.getHealthPoints() + (currentBoss.getDefence() * 1.1);

    }


    public void battleInfo() {

        System.out.printf("Hero hp: %.1f \t x Boss hp: %.1f \n" ,
                heroHp,
                bossHp
        );
        if (heroWin){
            System.out.printf("Hero %s win \n", currentHero.getName());
        }else if(bossWin){
            System.out.printf("Boss %s win \n", currentBoss.getName());
        }
    }

    public boolean isEnd() {
        return isEnd;
    }

    //TODO: ВРАХОВУВАТИ БРОНЮ ДО ХП
    public void attack() {

        double heroCritical = Math.random();
        double bossCritical = Math.random();
        if (currentHero.getCriticalChance() > heroCritical){
            System.out.println("Hero critical hit");
            //currentBoss.setHealthPoints(currentBoss.getHealthPoints() - (currentHero.getAttackAmount() * currentHero.getCriticalHit()));
            bossHp -= currentHero.getAttackAmount() * currentHero.getCriticalHit();

        }else {
            bossHp -= currentHero.getAttackAmount();

        }
        if (currentBoss.getCriticalChance() > bossCritical){
            System.out.println("Boss critical hit");
            //currentHero.setHealthPoints(currentHero.getHealthPoints() - (currentBoss.getAttackAmount() * currentBoss.getCriticalHit()));
            heroHp -= currentBoss.getAttackAmount() * currentBoss.getCriticalHit();

        }else {
            heroHp -= currentBoss.getAttackAmount();
        }
        if (heroHp <= 0 || bossHp <=0) {
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
    public void heal(@NotNull Item item){
    	heroHp = currentHero.restoreHealth(((HealLevelOne) item).getHealToRegenerate(), heroHp);
    	item.removeItem(currentHero);
        userController.save();

    }
    public void useSkill(Skill skill) {
        this.skill = skill;
        if (skill.getSkillCategory() == SkillCategory.ATTACK) {
            bossHp = skill.useSkill(currentHero, bossHp);
        }
        if (skill.getSkillCategory() == SkillCategory.DEFENCE){
            heroHp = skill.useSkill(heroHp);
        }
    }

}
