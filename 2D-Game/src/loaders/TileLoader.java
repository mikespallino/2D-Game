package loaders;

import game.Tile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author mike
 */
public class TileLoader {
    
    public Tile[] tiles = new Tile[256];
    private int numberOfTiles;
    
    public void loadTiles() {
        
        BufferedImage tileSheet = null;
        try {
            //load the tile sheet
            //create a scanner for the stored tile bytes
            //create ints to hold tile number, x, y, index and the tile bytes
            tileSheet = ImageIO.read(new File("bin\\res\\tilesheet4.png"));
            Scanner sc = new Scanner(new File("bin\\res\\tileRef.txt"));
            
            int i,x,y,tileNum,b1,b2,b3,b4,b5;
            i = 0;
            x = 0;
            y = 0;
            
            //dummy scan to get to the next line of the file
            sc.nextLine();
            
            while(sc.hasNextInt()) {
                tileNum = sc.nextInt();
                b1 = sc.nextInt();
                b2 = sc.nextInt();
                b3 = sc.nextInt();
                b4 = sc.nextInt();
                b5 = sc.nextInt();
                
                tiles[i] = new Tile(tileSheet.getSubimage(x, y, 32, 32),tileNum,(byte)b1,(byte)b2,(byte)b3,(byte)b4,(byte)b5);
                
                i++;
                x+=32;
                if(x == 512) {
                    x = 0;
                    y+=32;
                }
                
                if(y == 512) {
                    break;
                }
            }
            
            numberOfTiles = i;
            
            //close the scanner
            sc.close();
            
        } catch (IOException e) {
            System.out.println("ERROR: Failed to load the tile sheet.");
        }
        
    }
    
    //return the tiles array
    public Tile[] getTiles() {
        return tiles;
    }
    
    //return the tile are the index num
    public Tile getTile(int num) {
        return tiles[num-1];
    }
    
    public int getNumberOfTiles() {
    	return numberOfTiles + 1;
    }
    
}
