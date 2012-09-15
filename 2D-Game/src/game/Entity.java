package game;

import loaders.SpriteLoader;
import java.awt.Image;

/**
 *
 * @author Mike
 */
public abstract class Entity {

    private int x,y,dx,dy,dir,lastDir;
    private Image [][] sprite = new Image[4][3];
    private SpriteLoader sL;
    private CollisionChecker cC = new CollisionChecker();
    private boolean fromBreak = false;
    
    public Entity(int x, int y, String pathToSprite) {
        this.x = x;
        this.y = y;
        dx=0;
        dy=0;
        dir = 0;
        lastDir=0;
        sL = new SpriteLoader(pathToSprite);
        sL.load();
        init();
        loadSprites();
    }
    
    void init() {
    }
    
    final void loadSprites() {
        Image[][] temp = sL.getSprite();
        System.arraycopy(temp[0], 0, sprite[0], 0, 3);
        System.arraycopy(temp[1], 0, sprite[1], 0, 3);
        System.arraycopy(temp[2], 0, sprite[2], 0, 3);
        System.arraycopy(temp[3], 0, sprite[3], 0, 3);
    }
    
    boolean canMove(Tile[] tiles, int direction) {
        fromBreak = false;
        for(int i = 0; i < tiles.length; i++) {
            if(tiles[i] != null) {
                if(tiles[i].getX() <= x + 24 && tiles[i].getX() >= x - 24
                   && tiles[i].getY() <= y + 32 && tiles[i].getY() >= y - 32) {
                    if(cC.checkCollision(this, tiles[i])) {
                        fromBreak = true;
                        break;
                    }
                }
            }
        }
        if(fromBreak) {
            //switch based off of the direction the Entity was traveling when the collision happened
            switch(direction){
                case 0:
                    //move the Entity up 2 (opposite direction of travel)
                    y += dy - 2;
                    lastDir = 0;
                    break;
                case 1:
                    //move the Entity right 2 (opposite direction of travel)
                    x += dx + 2;
                    lastDir = 1;
                    break;
                case 2:
                    //move the Entity down 2 (opposite direction of travel)
                    y += dy + 2;
                    lastDir = 2;
                    break;
                case 3:
                    //move the Entity left 2 (opposite direction of travel)
                    x += dx - 2;
                    lastDir = 3;
                    break;
            }
            return false;
        } else {
            return true;
        }
    }
    
    boolean canMoveEntity(Entity e, int direction) {
        if(cC.checkEntityCollision(e, this)) {
            //switch based off of the direction the Entity was traveling when the collision happened
            switch(direction){
                case 0:
                    //move the Entity down 2 (opposite direction of travel)
                    y += dy + 2;
                    lastDir = 0;
                    break;
                case 1:
                    //move the Entity left 2 (opposite direction of travel)
                    x += dx - 2;
                    lastDir = 1;
                    break;
                case 2:
                    //move the Entity up 2 (opposite direction of travel)
                    y += dy - 2;
                    lastDir = 2;
                    break;
                case 3:
                    //move the Entity right 2 (opposite direction of travel)
                    x += dx + 2;
                    lastDir = 3;
                    break;
            }
            return true;
        } else {
            return false;
        }
    }
    
    //move method
    void move() {
        x+=dx;
        y+=dy;
    }
    
    //get the current direction of the enemy
    int getDir() {
    	return dir;
    }
    
    //get the last direction of the Entity
    int getLastDirection() {
        return lastDir;
    }
    
    //get the x coordinate of the Entity
    int getX() {
        return x;
    }
    
    //get the y coordinate of the Entity
    int getY() {
        return y;
    }
    
    //get the current sprite of the Entity
    Image getSprite(int dir, int i) {
        return sprite[dir][i];
    }
    
    void setX(int x) {
        this.x = x;
    }
    
    void setY(int y) {
        this.y = y;
    }
    
    int getDX() {
        return dx;
    }
    
    int getDY() {
        return dy;
    }
    
    void setDir(int dir) {
    	this.dir = dir;
    }

    void setDY(int dy) {
        this.dy = dy;
    }
    void setDX(int dx) {
        this.dx = dx;
    }
    
    void addToX(int ammt) {
        x += ammt;
    }
    
    void addToY(int ammt) {
        y += ammt;
    }
    
}