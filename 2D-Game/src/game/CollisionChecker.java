package game;

import java.awt.Rectangle;

/**
 *
 * @author Mike
 */
public class CollisionChecker {
    
    Rectangle t1;
    Rectangle t2Whole;
    Rectangle t2P1;
    Rectangle t2P2;
    Rectangle t2P3;
    Rectangle t2P4;
    boolean splitTile = false;
    private int direction = 0;
    
    public boolean checkCollision(Entity entity, Tile t) {
        
        //get the players direction
        direction = entity.getLastDirection();
        //set splitTile false incase it was set true before
        splitTile = false;
        //create a temp rectangle to represent the player
        t1 = new Rectangle(entity.getX(), entity.getY(), 24, 32);
        //get the bytes from the tile
        if(t.getTileBytes() != null) {
            for(int i = 0; i < t.getTileBytesLength(); i++) {
                //if any of the bytes are not 0 then the tile is split
                if(t.getTileByte(i) != 0) {
                    splitTile = true;
                }
            }   
        }
        
        //if the byte is 0 then collision does not need to be checked for
        if(t.getCollisionType() != 0 && splitTile == false) {
            
            //create a temp rectangle for the whole tile
            t2Whole = new Rectangle(t.getX(), t.getY(), 32, 32);

            //check if temp1 intersects temp2 or vice versa
            //if neither of those is true then there is no collision
            if(t1.intersects(t2Whole)) {
                return true;
            } else if (t2Whole.intersects(t1)) {
                return true;
            } else {
                return false;
            }
        
        //if the tile is split
        } else if(splitTile) {
            
            //make 4 temp rectangles based off of the 4 quadrants of the tile
            //tiles are 32X32 so each rectangle will be 16X16
            t2P1 = new Rectangle(t.getX(), t.getY(),16,16);
            t2P2 = new Rectangle(t.getX() + 16, t.getY(),16,16);
            t2P3 = new Rectangle(t.getX(), t.getY() + 16,16,16);
            t2P4 = new Rectangle(t.getX() + 16, t.getY() + 16,16,16);
            
            //check collision on the first quadrant if the byte is 1
            if(t.getTileByte(0) == 1) {
                if(t1.intersects(t2P1)) {
                    return true;
                } else if (t2P1.intersects(t1)) {
                    return true;
                } else {
                    return false;
                }
            //check collision on the second quadrant if the byte is 1
            } else if(t.getTileByte(1) == 1) {
                if(t1.intersects(t2P2)) {
                    return true;
                } else if (t2P2.intersects(t1)) {
                    return true;
                } else {
                    return false;
                }
            //check collision on the third quadrant if the byte is 1
            } else if(t.getTileByte(2) == 1) {
                if(t1.intersects(t2P3)) {
                    return true;
                } else if (t2P3.intersects(t1)) {
                    return true;
                } else {
                    return false;
                }
            //check collision on the fourth quadrant if the byte is 1
            } else if(t.getTileByte(3) == 1) {
                if(t1.intersects(t2P4)) {
                    return true;
                } else if (t2P4.intersects(t1)) {
                    return true;
                } else {
                    return false;
                }
            //otherwise this tile does not have any bytes that are handled
            //we could add different types of collisions
            } else {
                return false;
            }
        //Otherwise there is no collision
        } else {
            return false;
        }
        
    }
    
    public boolean checkEntityCollision(Entity e, Entity e2) {
        t1 = new Rectangle(e.getX(), e.getY(), 24, 32);
        t2Whole = new Rectangle(e2.getX(), e2.getY(), 24, 32);
        
        if(t1.intersects(t2Whole)) {
            return true;
        } else if(t2Whole.intersects(t1)) {
            return true;
        } else {
            return false;
        }
    }
    
    //get the direction when the player collides with a tile
    public int dirWhenCollision(Player p) {
        return direction;
    }
    
}