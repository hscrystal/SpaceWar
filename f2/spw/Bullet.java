
package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Mildronize
 */

public class Bullet extends Sprite{
        
        public static final int Y_TO_DIE = 0;
        private boolean alive = true;
                
        private int step = 10;
        
        public Bullet(int x, int y) {
            super(x, y, 4, 7);
        }
        
        public void move(){
            y -= step;
            if(y < Y_TO_DIE ) {
                die();
            }
        }
        
        public boolean isAlive(){
            return alive;
        }
        
        public void die(){
            alive = false;
        }
                
		@Override
		public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		}  
}
