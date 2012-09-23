/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.event.KeyEvent;

/**
 *
 * @author mike
 */
public class Player extends Entity{

    private static int key, cHealth, mHealth, cMana, mMana, cXP, nextLevelXP, level;
    private static boolean paused = false;
    
    public Player(int x, int y, String pathToSprite) {
        super(x,y,pathToSprite);
    }
    
    @Override
    public void init() {
        cHealth = 89;
        mHealth = 100;
        cMana = 45;
        mMana = 50;
        cXP = 17;
        nextLevelXP = 100;
        level = 5;
    }
    
    //key input
    public void keyPressed(KeyEvent e) {
        
        //store the key code
        key = e.getKeyCode();
        
        switch(key) {
            //if the key is the up arrow set dy = -1 so the player moves up
            // set the direction to 0
            case KeyEvent.VK_UP:
                super.setDY(-2);
                super.setDir(0);
                break;
            //if the key is the down arrow set dy = 1 so the player moves down
            // set the direction to 2
            case KeyEvent.VK_DOWN:
                super.setDY(2);
                super.setDir(2);
                break;
            //if the key is the left arrow set dx = -1 so the player moves left
            // set the direction to 3
            case KeyEvent.VK_LEFT:
                super.setDX(-2);
                super.setDir(3);
                break;
            //if the key is the right arrow set dx = 1 so the player moves right
            // set the direction to 1
            case KeyEvent.VK_RIGHT:
                super.setDX(2);
                super.setDir(1);
                break;
            case KeyEvent.VK_ESCAPE:
                if(!paused) {
                    paused = true;
                } else {
                    paused = false;
                }
        }
        
    }

    //key input
    public void keyReleased(KeyEvent e) {
    
        //store the key code
        key = e.getKeyCode();
        
        //set the dy or dx back to 0
        switch(key) {
            case KeyEvent.VK_UP:
                super.setDY(0);
                break;
            case KeyEvent.VK_DOWN:
                super.setDY(0);
                break;
            case KeyEvent.VK_LEFT:
                super.setDX(0);
                break;
            case KeyEvent.VK_RIGHT:
                super.setDX(0);
                break;
        }
        
    }
    
    //get the players current health
    public int getCHealth() {
        return cHealth;
    }
    
    //get the players max health
    public int getMHealth() {
        return mHealth;
    }
    
    //get the players current mana
    public int getCMana() {
        return cMana;
    }
    
    //get the players max mana
    public int getMMana() {
        return mMana;
    }
    
    //get the players current XP
    public int getCXP() {
        return cXP;
    }
    
    //get the xp needed for the next level
    public int getNextLevelXP() {
        return nextLevelXP;
    }
    
    //get the players level
    public int getLevel() {
        return level;
    }
    
    //get the current state of the game: paused/running
    public boolean getPaused() {
        return paused;
    }
    
}