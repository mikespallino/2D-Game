package game;

import java.awt.Image;
import java.awt.Rectangle;

import loaders.SpriteLoader;

/**
 *
 * @author Mike
 */
public abstract class Entity {

    private int x,y,dx,dy,dir,lastDir;
    private Image [][] sprite = new Image[4][3];
    private SpriteLoader sL;
    private boolean fromBreak = false;
    private static final int HEIGHT = 32;
    private static final int WIDTH = 24;
    
    public Entity() {
    	
    }
    
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
        int i = 0;
        for(i = 0; i < tiles.length; i++) {
            if(tiles[i] != null) {
                if(tiles[i].getX() <= x + 24 && tiles[i].getX() >= x - 24
                   && tiles[i].getY() <= y + 32 && tiles[i].getY() >= y - 32) {
                    if(checkTileCollision(tiles[i])) {
                        fromBreak = true;
                        break;
                    }
                }
            }
        }
        if(fromBreak) {
            fixTileCollision(tiles[i]);
            return false;
        } else {
            return true;
        }
    }
    
    boolean canMoveEntity(Entity e) {
        if(checkEntityCollision(e)) {
            fixEntityCollision(e);
            return true;
        } else {
            return false;
        }
    }
    
    boolean checkTileCollision(Tile t) {
    	Rectangle r1 = new Rectangle(getX(),getY(),WIDTH,HEIGHT);
    	if(t.getCollisionType() != 0) {
    		Rectangle r2Whole = new Rectangle(t.getX(), t.getY(), 32, 32);
    		return (r1.intersects(r2Whole) || r2Whole.intersects(r1));
    	} else {
    		Rectangle r2p1 = new Rectangle(t.getX(), t.getY(), 16, 16);
    		Rectangle r2p2 = new Rectangle(t.getX()+16, t.getY(), 16, 16);
    		Rectangle r2p3 = new Rectangle(t.getX(), t.getY()+16, 16, 16);
    		Rectangle r2p4 = new Rectangle(t.getX()+16, t.getY()+16, 16, 16);
    		
    		//check collision on the first quadrant if the byte is 1
            if(t.getTileByte(0) == 1) {
                return(r1.intersects(r2p1) || r2p1.intersects(r1));
            //check collision on the second quadrant if the byte is 1
            } else if(t.getTileByte(1) == 1) {
            	 return(r1.intersects(r2p2) || r2p2.intersects(r1));
            //check collision on the third quadrant if the byte is 1
            } else if(t.getTileByte(2) == 1) {
            	 return(r1.intersects(r2p3) || r2p3.intersects(r1));
            //check collision on the fourth quadrant if the byte is 1
            } else if(t.getTileByte(3) == 1) {
            	 return(r1.intersects(r2p4) || r2p4.intersects(r1));
            //otherwise this tile does not have any bytes that are handled
            //we could add different types of collisions
            } else {
                return false;
            }
    	}
    }
    
    boolean checkEntityCollision(Entity e) {
    	Rectangle r1 = new Rectangle(getX(),getY(),WIDTH,HEIGHT);
    	Rectangle r2 = new Rectangle(e.getX(),e.getY(), Entity.WIDTH, Entity.HEIGHT);
    	return ((r1.intersects(r2)) || (r2.intersects(r1)));
    }
    
    void fixTileCollision(Tile t) {
    	while(checkTileCollision(t)) {
			if(x < t.getX()) {
				x -= 2;
			} else if(x > t.getX()) {
				x += 2;
			}
			if(y < t.getY()) {
				y -= 2;
			} else if(y > t.getY()) {
				y += 2;
			}
		}
    }
    
    void fixEntityCollision(Entity e) {
    	while(checkEntityCollision(e)) {
			if(x < e.getX()) {
				x -= 2;
			} else if(x > e.getX()) {
				x += 2;
			}else if(y < e.getY()) {
				y -= 2;
			} else if(y > e.getY()) {
				y += 2;
			}
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