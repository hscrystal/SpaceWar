package f2.spw;

import java.awt.Color;

/**
 *
 * @author Mildronize
 */
public class ItemInvisible extends Item {

    SpaceShip v;
    public static final int TIME = 5;// second 
    
    public ItemInvisible(int x, int y, SpaceShip v) {
        super(x, y, 15, 15, Color.MAGENTA);
        this.v = v;
    }
    
    @Override
    public void proceed(int timeOut) {
        v.hide((int)1000/Config.FPS*TIME+timeOut);
    }
}
