package utils;

import loaders.MapLoader;
import game.Tile;
import loaders.TileLoader;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author mike
 */
public class MapEditor implements ActionListener {
    
    JFrame frame;
    DP panel;
    ML mouseListener;
    MWL mouseWheelListener;
    IL itemListener;
    
    JMenuBar menu;
    JMenu file;
    JMenuItem save;
    JMenuItem load;
    
    Timer time;
    BufferedWriter saver;
    MapLoader loader;
    
    TileLoader arb = new TileLoader();
    
    int mouseXOnClick;
    int mouseYOnClick;
    int[][] laidTilesInfo = new int[250000][3];
    int tileNumberSelected = 1;
    boolean loaded = false;
    int scrollAmt;
    
    Tile[] tiles;
    
    public MapEditor() {
        frame = new JFrame("2D Game - Map Editor");
        panel = new DP();
        mouseListener = new ML();
        itemListener = new IL();
        mouseWheelListener = new MWL();
        
        menu = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save Map");
        load = new JMenuItem("Load Map");
        
        menu.add(file);
        file.add(save);
        file.add(load);
        
        save.addActionListener(itemListener);
        load.addActionListener(itemListener);
        
        arb.loadTiles();
        tiles = arb.getTiles();
    }
    
    public static void main(String[] args) {
        MapEditor editor = new MapEditor();
        editor.setUp();
    }
    
    public void setUp() {
        frame.setSize(512,512);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.NORTH, menu);
        
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        panel.addMouseListener(mouseListener);
        panel.addMouseWheelListener(mouseWheelListener);
        panel.setFocusable(true);
        frame.setVisible(true);
        
        time = new Timer(5, this);
        time.start();  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(loaded) {
            frame.repaint();
        }
        frame.repaint(); 
    }
    
    public void save() {
        int[][] tileNumbers = new int[500][500];
        boolean tileLaid = false;
        int x = 0;
        int y = 0;
        
        for(x = 0; x < 500; x++) {
            
            tileLaid = false;
            
            for(int i = 0; i < laidTilesInfo.length; i++) {
                if(laidTilesInfo[i][1]/32 == x && laidTilesInfo[i][2]/32 == y) {
                    System.out.println("tile number: " + laidTilesInfo[i][0] + " x: " + x*32 + ", y: " + y*32);
                    tileLaid = true;
                    tileNumbers[y][x] = laidTilesInfo[i][0];
                    break;
                }
                if(laidTilesInfo[i][0] == 0) {
                    break;
                }
            }
            if(!tileLaid) {
                tileNumbers[y][x] = 0;
            }
            if(x == 499) {
                x = -1;
                y++;
            }
            if(y == 500) {
                break;
            }
        }
        try {
            saver = new BufferedWriter(new FileWriter(new File("res\\editor_map.txt")));
            for(int j = 0; j < tileNumbers.length; j++) {
                for(int k = 0; k < tileNumbers[j].length; k++) {
                    saver.write(tileNumbers[j][k] + ",");
                }
                saver.newLine();
            }
            System.out.println("Map was sucessfully saved!");
        } catch (IOException ex) {
            System.out.println("ERROR: The map could not be saved.");
        }
    }
    
    public void load() {
        try {
            loader = new MapLoader(new File("res\\editor_map.txt"));
            loader.load();
            int[][] tileNumbers = loader.getTiles();
            int itr = 0;
            for(int i = 0; i < tileNumbers.length -1; i++) {
                for(int j = 0; j < tileNumbers[j].length -1; j++) {
                    if(itr == 99999) {
                        break;
                    } else {
                        laidTilesInfo[itr][0] = tileNumbers[i][j];
                        laidTilesInfo[itr][1] = j*32;
                        laidTilesInfo[itr][2] = i*32;
                        itr++;
                    }
                }
            }
            
            System.out.println("The map was sucessfully loaded!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: The map could not be loaded.");
        }
        frame.repaint();
    }
    
    public class DP extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
        public void paintComponent(Graphics g) {
            for(int i = 0; i < laidTilesInfo.length; i++) {
                if(laidTilesInfo[i][0] != 0) {
                    g.drawImage(tiles[laidTilesInfo[i][0]-1].getTile(), laidTilesInfo[i][1], laidTilesInfo[i][2], null);
                }
            }
        }
    }
    
    public class ML extends MouseAdapter {
        int clicks = 0;
        @Override
        public void mouseClicked(MouseEvent e) {
        	
            mouseXOnClick = MouseInfo.getPointerInfo().getLocation().x;
            mouseYOnClick = MouseInfo.getPointerInfo().getLocation().y;
            double temp1 = 0.0;
            double dec = 0.0;
            int temp2 = 0;
            int num = 0;
            int mult = 0;
            
            if(mouseXOnClick % 32 == 0) {
            } else {
                temp1 = (double)mouseXOnClick/32;
                dec = temp1 - (int)temp1;
                num = Math.round((float)temp1);
                
                mult = num*32;
                temp2 = Math.abs(mult-mouseXOnClick);
                
                if(dec > .5) {
                    mouseXOnClick = mouseXOnClick + temp2 - 32;
                } else {
                    mouseXOnClick = Math.abs(mouseXOnClick - temp2);
                }
            } 
            
            if(mouseYOnClick % 32 == 0) {   
            } else {
                temp1 = (double)mouseYOnClick/32;
                dec = temp1 - (int)temp1;
                num = Math.round((float)temp1);
                mult = num*32;
                temp2 = Math.abs(mult-mouseYOnClick);
                if(dec > .5) {
                    mouseYOnClick = mouseYOnClick + temp2 - 64;
                } else {
                    mouseYOnClick = Math.abs(mouseYOnClick - temp2 - 64);
                }
            }
            laidTilesInfo[clicks][0] = tileNumberSelected;
            laidTilesInfo[clicks][1] = mouseXOnClick;
            laidTilesInfo[clicks][2] = mouseYOnClick;
            
            clicks++;
            if(clicks == 250000) {
                System.out.println("ERROR: Click Limit Reached");
                clicks = 0;
            }
        }
    }
    
    public class IL implements ActionListener, ItemListener {
    	
        @Override
        public void itemStateChanged(ItemEvent e) {
                        
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == save) {
                save();
            } else if(e.getSource() == load) {
                load();
            }
        }
        
    }
    
    public class MWL implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scrollAmt = e.getWheelRotation();
            tileNumberSelected += scrollAmt;
            if(tileNumberSelected <= 0 || tileNumberSelected >= 20) {
                tileNumberSelected = 1;
            }
        }
    }
    
}
