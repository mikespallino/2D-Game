package loaders;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author mike
 */
public class MapLoader {

    File map;
    Scanner scan;
    
    String[][] stringTileNumbers = new String[500][500];
    int[][] tileNumbers = new int[500][500];
    
    //set a specific map to load
    public MapLoader(File f) {
        map = f;
    }
    
    //use the default map
    public MapLoader() {
        map = new File("bin\\res\\map.txt");
    }
    
    //load data from the map
    public void load() {
        
        try {
            //scan the file provided or default map
            //set x and y to 0 if they are different from a previous call
            scan = new Scanner(map);
            int y = 0;
            //populate the stringTileNumbers array with everything from the file, splitting by ","
            while(scan.hasNext()) {
                stringTileNumbers[y] = scan.next().split(",");
                y++;
            }
            // convert the stringTileNumbers to ints stored in tileNumbers
            for(int i = 0; i < stringTileNumbers.length; i++) {
                for(int j = 0; j < stringTileNumbers[i].length; j++) {
                    if(i != 500 && j != 500) {
                        if(stringTileNumbers[i][j] == null) {
                            tileNumbers[i][j] = -1;
                        } else {
                            tileNumbers[i][j] = Integer.parseInt(stringTileNumbers[i][j]);
                        }
                    }
                }    
            }
        } catch (IOException ex) {
        	System.err.println("ERROR: Failed to load map data.");
        }
        
    }
    
    //return the tile numbers
    public int[][] getTiles() {
        return tileNumbers;
    } 
}