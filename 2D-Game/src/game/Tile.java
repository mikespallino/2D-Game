package game;

import java.awt.Image;

/**
 *
 * @author Mike
 */
public class Tile{
    
    private Image tile;
    //for predictive tile laying in editor
    private byte b1;
    private byte b2;
    private byte b3;
    private byte b4;
    //for collision detection
    private byte b5;
    //for returning all values at once
    private byte[] tileBytes;
    //for collision detection
    private int x = 0;
    private int y = 0;
    private int tileNumber;
    
    //set all the fields to values form the constructor
    public Tile(Image img, int num, byte b1, byte b2, byte b3, byte b4, byte b5) {
    	
        tile = img;
        tileNumber = num;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
        this.b5 = b5;
        
        tileBytes = new byte[] {b1,b2,b3,b4};
               
    }
    
    //set the tile to the tile provided in the constructor
    public Tile(Tile t) {
        
        tile = t.getTile();
        tileNumber = t.getTileNumber();
        b1 = t.getTileByte(0);
        b2 = t.getTileByte(1);
        b3 = t.getTileByte(2);
        b4 = t.getTileByte(3);
        b5 = t.getCollisionType();
        tileBytes = t.getTileBytes();
        
    }
    
    //return the bytes of the tile
    public byte[] getTileBytes() {
        
        return tileBytes;
        
    }
    
    //return the byte of the tile at the specified index
    public byte getTileByte(int index) {
        
        if(index < tileBytes.length && index >= 0) {
            return tileBytes[index];
        } else {
            System.out.println("ERROR: Not a valid index.");
            return 0;
        }
        
    }
    
    //return the collision type byte
    public byte getCollisionType() {
        return b5;    
    }
    
    //return the tile's image (for drawing)
    public Image getTile() { 
        return tile;  
    }
    
    //set the image of the tile
    public void setTile(Image img) {
        tile = img; 
    }
    
    //set the x coordinate of the tile
    public void setX(int x) {
        this.x = x;
    }
    
    //set the y coordinate of the tile
    public void setY(int y) {
        this.y = y; 
    }
    
    //return the x coordinate of the tile
    public int getX() {
        return x;
    }
    
    //return the y coordinate of the tile
    public int getY() {
        return y; 
    }
    
    //return the tile number of the tile
    public int getTileNumber() {
        return tileNumber;  
    }
    
    //return the length of the tileBytes array
    public int getTileBytesLength() {
        return tileBytes.length;
    }
    
    //prints out data specific to the tile
    @Override
    public String toString() {
        return "Tile Numnber: " + tileNumber + " b5: " + b5 + " Collision Bytes: " + b1 + ", " + b2 + ", " + b3 + ", " + b4 + " X: " + getX() + " Y: " + getY();
    }
    
}
