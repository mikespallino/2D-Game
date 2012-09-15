package game;

/**
 *
 * @author Mike
 */
public class Enemy extends Entity {

    private double distance;
    private final int DISTANCE_TO_FOLLOW = 75;
    private final int DISTANCE_TO_STOP_X = 24;
    private final int DISTANCE_TO_STOP_Y = 32;
    
    public Enemy(int x, int y, String pathToSprite) {
        super(x,y,pathToSprite);
    }
    
    @Override
    public void init() {
    }
    
    public void moveToPlayer(Player p, Tile[] t, int dir) {
        super.setDX(0);
        super.setDY(0);
        if(canMove(t,dir)) {
            int goToX = p.getX();
            int goToY = p.getY();
            distance = Math.sqrt(Math.pow(super.getX()-goToX,2)+Math.pow(super.getY()-goToY,2));
            if(distance <= DISTANCE_TO_FOLLOW) {
                if(super.getX() < goToX) {
                    super.setDX(1);
                    super.setDir(1);
                } else {
                    super.setDX(-1);
                    super.setDir(3);
                }
                if(super.getY() < goToY) {
                    super.setDY(1);
                    super.setDir(2);
                } else {
                    super.setDY(-1);
                    super.setDir(0);
                }
            }
            
            if(distance == DISTANCE_TO_STOP_X || (distance >= DISTANCE_TO_STOP_X - 2 && distance <= DISTANCE_TO_STOP_X + 2)) {
                stopFollow();
            } else if(distance == DISTANCE_TO_STOP_Y || (distance >= DISTANCE_TO_STOP_Y - 2 && distance <= DISTANCE_TO_STOP_Y + 2)) {
                stopFollow();
            }

            super.addToX(super.getDX());
            super.addToY(super.getDY());
        }
    }
    
    public void stopFollow() {
        super.setDX(0);
        super.setDY(0);
    }
    
    public double getDistance() {
        return distance;
    }
}