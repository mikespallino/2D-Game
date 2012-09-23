package loaders;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author mike
 * 
 */
public class SpriteLoader {
    
    File sprites;
    BufferedImage spriteSheet;
    Image[][] entitySprites = new Image[4][3];
    
    //set the file where sprites are located
    public SpriteLoader(String filename) {
        sprites = new File("bin\\res\\" + filename);
    }
    
    public void load() {
        
        try {
            //get the image
            //set i and j to 0 if they are different from a previous call
            spriteSheet = ImageIO.read(sprites);
            int i = 0;
            int j = 0;
            
            //loop through the dimensions of the spritesheet (area where player is: 71 X 135)
            for(int y = 2; y < 135; y += 32) {
                for(int x = 0; x < 71; x += 24) {
                    //there are only 4 directions so break if j = 4
                    if(j == 4) {
                        break;
                    } else {
                        //set the image to the players sprites
                        entitySprites[j][i] = spriteSheet.getSubimage(x,y,24,32);
                        i++;
                    }
                }
                i = 0;
                j++;
            }
            
        } catch (IOException ex) {
            System.out.println("ERROR: Faild to load sprites.");
        }
        
    }
    
    //return the sprites the were loaded
    public Image[][] getSprite() {
        return entitySprites;
    }
    
}
