package game;

import loaders.MapLoader;
import loaders.TileLoader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author mike
 */
public class Game implements ActionListener {

    JFrame frame;
    DP panel;
    KL keyListener;
    MapLoader loader;
    Timer time;
    TileLoader arb;
    Player player;
    CollisionChecker cChecker;
    
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    int animationFrame;
    int[][] mapTileNumbers;
    Tile[] tiles;
    Image topHud;
    Image bottomHud;
    Image menu;
    int x = 0;
    int y = 0;
    int m = 0;
    int n = 0;
    int incr = 0;
    int t = 0;

    //Just initialize everything - Look at classes for specifics
    public Game() {

        arb = new TileLoader();
        arb.loadTiles();
        frame = new JFrame("2D Game");
        panel = new DP(true);
        keyListener = new KL();
        loader = new MapLoader();
        loader.load();
        player = new Player(0,0, "playerSprites.png");
        cChecker = new CollisionChecker();

        mapTileNumbers = loader.getTiles();
        tiles = new Tile[250000];
        topHud = new ImageIcon("src\\res\\tophud-transparent.png").getImage();
        bottomHud = new ImageIcon("src\\res\\bottomhud-transparent.png").getImage();
        menu = new ImageIcon("src\\res\\Menu.png").getImage();

    }

    //create a Game object and call its start method
    public static void main(String[] args) {
        Game g = new Game();
        g.start();
    }

    //sets up the frame
    //generates the map
    //starts the timer
    public void start() {
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        panel.setFocusable(true);
        panel.addKeyListener(keyListener);
        frame.setVisible(true);
        generateMap();
        for(int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * 450) + 1;
            int y = (int) (Math.random() * 450) + 1;
            enemies.add(new Enemy(x,y,"enemySprites2.png"));
        }
        time = new Timer(5, this);
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int enemyCollisions = 0;
        for(int i = 0; i < enemies.size(); i++) {
            if(player.canMoveEntity(enemies.get(i), player.getDir())) {
                enemyCollisions++;
            }
        }
        //check for a collision with tiles close to the player
        if(player.canMove(tiles, player.getDir()) && enemyCollisions == 0) {
            player.move();
        }
        if(enemies.size() > 0) {
            for(int j = 0; j < enemies.size(); j++) {
                if(!player.canMoveEntity(enemies.get(j), player.getDir())) {
                    enemies.get(j).moveToPlayer(player, tiles, enemies.get(j).getDir());
                } else {
                    enemies.get(j).stopFollow();
                }
            }
        }
        //repaint the screen
        frame.repaint();

    }

    //sets up the map
    //it just populates the tile[] with tiles from the map file
    public void generateMap() {
        //loop through the tile numbers from the map file
        for(int i = 0; i < mapTileNumbers.length*32; i++) {
            //for the actual screen x and y of the tile
            if(x == mapTileNumbers.length*32) {
                x = 0;
                y+=32;
            }
            if(n > mapTileNumbers[m].length -1) {
                n = 0;
                m++;
            }
            //loop and check if any of the tile numbers that were loaded are equal to the increment
            for(int j = 1; j < arb.tiles.length; j++) {
                //if so then set the tile at the current increment to a tile with that tile number
                // also set that tiles x and y
                if(mapTileNumbers[m][n] == j) {
                    tiles[incr] = new Tile(arb.getTile(j));
                    tiles[incr].setX(x);
                    tiles[incr].setY(y);
                    incr++;
                }
            }
            n++;
            x+=32;
        }

    }

    public class DP extends JPanel {

		private static final long serialVersionUID = 1L;

		public DP(boolean b) {
			super(true);
		}

		//draw stuff
        @Override
        public void paintComponent(Graphics g) {
            g.setFont(new Font("courier new", Font.TYPE1_FONT, 12));
            for(int i = 0; i < tiles.length; i++) {
                if(tiles[i] != null) {
                    g.drawImage(tiles[i].getTile(), tiles[i].getX(), tiles[i].getY(), 32, 32, null);
                }
            }
            if(animationFrame < 30) {
                g.drawImage(player.getSprite(player.getDir(),1), player.getX(), player.getY(), 24, 32, null);
            } else if(animationFrame > 30 && animationFrame < 79) {
                g.drawImage(player.getSprite(player.getDir(),0), player.getX(), player.getY(), 24, 32, null);
            } else if (animationFrame > 80 && animationFrame < 150) {
                g.drawImage(player.getSprite(player.getDir(),2), player.getX(), player.getY(), 24, 32, null);
            }
            if (animationFrame < 150) {
                animationFrame++;
            } else {
                animationFrame = 0;
            }
            for(int j = 0; j < enemies.size(); j++) {
                if(animationFrame < 30) {
                    g.drawImage(enemies.get(j).getSprite(enemies.get(j).getDir(),1), enemies.get(j).getX(), enemies.get(j).getY(), 24, 32, null);
                    g.drawString("" + enemies.get(j).getDistance(), enemies.get(j).getX(), enemies.get(j).getY() -10);
                } else if(animationFrame > 30 && animationFrame < 79) {
                    g.drawImage(enemies.get(j).getSprite(enemies.get(j).getDir(),1), enemies.get(j).getX(), enemies.get(j).getY(), 24, 32, null);
                    g.drawString("" + enemies.get(j).getDistance(), enemies.get(j).getX(), enemies.get(j).getY() -10);
                } else if (animationFrame > 80 && animationFrame < 150) {
                    g.drawImage(enemies.get(j).getSprite(enemies.get(j).getDir(),1), enemies.get(j).getX(), enemies.get(j).getY(), 24, 32, null);
                    g.drawString("" + enemies.get(j).getDistance(), enemies.get(j).getX(), enemies.get(j).getY() -10);
                }
                if (animationFrame < 150) { 
                    animationFrame++;
                } else {
                    animationFrame = 0;
                }
            }
            g.drawImage(topHud, 5, 5, null);
            g.setFont(new Font("courier new", Font.TYPE1_FONT, 20));
            g.drawString("" +player.getLevel(),40,42);
            //draw background for top hud display bars 
            g.setColor(Color.DARK_GRAY);
            g.fillRect(69,75,51,4);
            g.fillRect(69,81,51,4);
            g.fillRect(69,87,51,4);
            //draw specifics
            //bar width = (cHealth * barWidth)/mHealth
            g.setColor(Color.RED);
            g.fillRect(69,75,(player.getCHealth()*51)/player.getMHealth(),4);
            g.setColor(Color.BLUE);
            g.fillRect(69,81,(player.getCMana()*51)/player.getMMana(),4);
            g.setColor(Color.GREEN);
            g.fillRect(69,87,(player.getCXP()*51)/player.getNextLevelXP(),4);
            //bottom hud
            g.drawImage(bottomHud, 130, 497, null);
            //draw info on top of bottom hud
            g.setColor(Color.BLACK);
            g.setFont(new Font("courier new", Font.TYPE1_FONT, 12));
            g.drawString("HEALTH: " + player.getCHealth() + "/" + player.getMHealth(), 205, 507);
            g.drawString("MANA: " + player.getCMana() + "/" + player.getMMana(), 365, 507);
            g.drawString("XP: " + player.getCXP() + "/" + player.getNextLevelXP(), 490, 507);
            if(player.getPaused()) {
                g.setFont(new Font("courier new", Font.TYPE1_FONT, 26));
                g.drawImage(menu, 250, 100, null);
                g.drawString("PAUSED", 313, 137);
                
            }
        }

    }

    //for getting key input
    public class KL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

    }

}