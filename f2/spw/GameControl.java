package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 *
 * @author mildronize
 */
public class GameControl implements KeyListener {

    private SpaceShip v;
    private GamePanel gp;
    private GameEngine ge;
    private Timer timer;

    public GameControl(SpaceShip v, GamePanel gp, GameEngine ge) {
        this.v = v;
        this.gp = gp;
        this.ge = ge;
        /*timer = new Timer((int) 1000 / Config.FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                process();
            }
        });
        timer.setRepeats(true);*/
    }

    void controlVehicle(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                v.moveX(-1);
                break;
            case KeyEvent.VK_RIGHT:
                v.moveX(1);
                break;
            case KeyEvent.VK_UP:
                v.moveY(-1);
                break;
            case KeyEvent.VK_DOWN:
                v.moveY(1);
                break;
            case KeyEvent.VK_CONTROL:
                ge.shoot(v);
                break;
            case KeyEvent.VK_Z:
                //generateItem();
                break;
            case KeyEvent.VK_D:
                //difficulty += 0.1;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //controlVehicle(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        controlVehicle(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //controlVehicle(e);
    }
}
