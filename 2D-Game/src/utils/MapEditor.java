package utils;

import game.Tile;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import loaders.MapLoader;
import loaders.TileLoader;

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
    KL keyListener;
    
    JMenuBar menu;
    JMenu file;
    JMenuItem save;
    JMenuItem load;
    
    Timer time;
    BufferedWriter saver;
    MapLoader loader;
    
    TileLoader arb = new TileLoader();
    
    Point startPoint;
    Point endPoint;
    
    int mouseXOnClick;
    int mouseYOnClick;
    int clicks = 0;
    int[][] laidTilesInfo = new int[250000][3];
    int tileNumberSelected = 1;
    boolean loaded = false;
    int scrollAmt;
    int xFactor, yFactor;
    
    Tile[] tiles;
    
    public MapEditor() {
        frame = new JFrame("2D Game - Map Editor");
        panel = new DP();
        mouseListener = new ML();
        itemListener = new IL();
        mouseWheelListener = new MWL();
        keyListener = new KL();
        
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
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.NORTH, menu);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.addKeyListener(keyListener);
        frame.setFocusable(true);
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
            saver = new BufferedWriter(new FileWriter(new File("bin\\res\\editor_map.txt")));
            for(int j = 0; j < tileNumbers.length; j++) {
                for(int k = 0; k < tileNumbers[j].length; k++) {
                    saver.write(tileNumbers[j][k] + ",");
                }
                saver.write("\n");
            }
            System.out.println("Map was sucessfully saved!");
        } catch (IOException ex) {
            System.out.println("ERROR: The map could not be saved.");
        }
    }
    
    public void load() {
        try {
            loader = new MapLoader(new File("bin\\res\\editor_map.txt"));
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
        
    public Point fixMouseCoordinates(int x, int y) {
        double temp1 = 0.0;
        double dec = 0.0;
        int temp2 = 0;
        int num = 0;
        int mult = 0;
        
        if(x % 32 == 0) {
        } else {
            temp1 = (double)x/32;
            dec = temp1 - (int)temp1;
            num = Math.round((float)temp1);
            
            mult = num*32;
            temp2 = Math.abs(mult-x);
            
            if(dec > .5) {
                x = x + temp2 - 32;
            } else {
                x = Math.abs(x - temp2);
            }
        } 
        
        if(y % 32 == 0) {   
        } else {
            temp1 = (double)(y)/32;
            dec = temp1 - (int)temp1;
            num = Math.round((float)temp1);

            mult = num*32;
            temp2 = Math.abs(mult-y);

            if(dec > .5) {
                y = y + temp2 - 32;
            } else {
                y = Math.abs(y - temp2);
            }
        }
        return new Point(x,y);
    }
    
    //true - first parameter is greater
    //false - second parameter is greater
    public boolean checkGreater(int x, int y) {
    	return x > y;
    }
    
    public void fillSpace() {
    	startPoint = fixMouseCoordinates((int)startPoint.getX(),(int)startPoint.getY());
    	endPoint = fixMouseCoordinates((int)endPoint.getX(),(int)endPoint.getY());
    	if(checkGreater((int)startPoint.getX(), (int)endPoint.getX())) {
    		xFactor = -32;
    	} else {
    		xFactor = 32;
    	}
    	if(checkGreater((int)startPoint.getY(), (int)endPoint.getY())) {
    		yFactor = -32;
    	} else {
    		yFactor = 32;
    	}
	    for(int y = (int)startPoint.getY(); y < (int)endPoint.getY(); y+=yFactor) {
	    	for(int x = (int)startPoint.getX(); x < (int)endPoint.getX(); x+=xFactor) {
	            laidTilesInfo[clicks][0] = tileNumberSelected;
		    	laidTilesInfo[clicks][1] = x;
		    	laidTilesInfo[clicks][2] = y;
		    	clicks++;
		    	if(clicks == 250000) {
		    		System.out.println("ERROR: Click Limit Reached");
		    		clicks++;
		    	}
	    	}
	    }
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
            g.drawImage(tiles[tileNumberSelected-1].getTile(), MouseInfo.getPointerInfo().getLocation().x-8, MouseInfo.getPointerInfo().getLocation().y-53, null);
        }
    }
    
    private class ML extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        	mouseXOnClick = (int) MouseInfo.getPointerInfo().getLocation().getX() - 8;
            mouseYOnClick = (int) MouseInfo.getPointerInfo().getLocation().getY() - 54;
            Point p = fixMouseCoordinates(mouseXOnClick, mouseYOnClick);
            for(int i = 0; i< clicks; i++) {
            	if(laidTilesInfo[i][1] == (int)p.getX() && laidTilesInfo[i][2] == (int)p.getY()) {
            		laidTilesInfo[i][0] = tileNumberSelected;
            	}
            }
            laidTilesInfo[clicks][0] = tileNumberSelected;
            laidTilesInfo[clicks][1] = (int)p.getX();
            laidTilesInfo[clicks][2] = (int)p.getY();
            
            clicks++;
            if(clicks == 250000) {
                System.out.println("ERROR: Click Limit Reached");
                clicks = 0;
            }
        }
    }
    
    private class IL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == save) {
                save();
            } else if(e.getSource() == load) {
                load();
            }
        }
        
    }
    
    private class MWL implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scrollAmt = e.getWheelRotation();
            tileNumberSelected += scrollAmt;
            if(tileNumberSelected >= arb.getNumberOfTiles()) {
                tileNumberSelected = 1;
            } else if (tileNumberSelected <= 0) {
            	tileNumberSelected = arb.getNumberOfTiles()-1;
            }
        }
    }
    
    private class KL extends KeyAdapter {
    	public void keyPressed(KeyEvent ev) {
    		int key = ev.getKeyCode();
    		switch(key) {
    			case KeyEvent.VK_ENTER:
    				startPoint = MouseInfo.getPointerInfo().getLocation();
    				break;
    			case KeyEvent.VK_SHIFT:
    				endPoint = MouseInfo.getPointerInfo().getLocation();
    				break;
    			case KeyEvent.VK_5:
    				if(startPoint != null && endPoint != null) {
        				fillSpace();
        			}
    		}
    	}
    }
    
}
