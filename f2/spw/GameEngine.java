package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter {

	GamePanel gp;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private SpaceShip v;
	private Timer timer;
	private long score = 0;
	private int life = 2;
	private int level = 1;
	private int level_before = 1;
	private double difficulty = 0.05; // 0-1 0.05
	private double probDrop = 0.2; // 0-1 0.2
	// private int[] dropItem = {100};
	private int time = 0;
	int t = 0;

	// private int stepEnemy = 4;

	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;

		gp.sprites.add(v);

		timer = new Timer((int) 1000 / Config.FPS, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);

	}

	public void start() {
		timer.start();
	}

	private void generateEnemy() {
		if (Math.random() < difficulty) {
			Enemy e = new Enemy((int) ((Math.random() * 390) / 10.0) * 15, 10,
					level);
			gp.sprites.add(e);
			enemies.add(e);
		}
	}

	private int random(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	private void generateItem(Enemy e) {
		if (Math.random() < probDrop) {
			int ranItem = random(1, 100);
			if (ranItem <= 50 ) {
				Item i = new ItemInvisible(e.x, e.y, v);
				gp.sprites.add(i);
				items.add(i);
			} else if (ranItem <= 90) {
				Item i = new ItemIncreaseBullet(e.x, e.y, v);
				gp.sprites.add(i);
				items.add(i);
			} else {
				Item i = new ItemIncreaseLife(e.x, e.y);
				gp.sprites.add(i);
				items.add(i);
			}
			System.out.println("Generate Item!!!!!");
		}
	}

	public void shoot(SpaceShip v) {
		ArrayList<Bullet> tmpBullets = v.GenerateBullet();
		for (Bullet b : tmpBullets) {
			gp.sprites.add(b);
			bullets.add(b);
		}
	}

	private void process() {
		generateEnemy();

		moveEnemy();
		moveBullet();
		moveItem();

		gp.updateGameUI(this);

		checkBulletHit();
		checkItemHit();
		checkHit();

		if (!v.isVisible()) {
			if (v.getTimeOut() <= time) {
				v.show();
			}
		}

		increseSpeed();
		time++;
		if (time > 10000)
			time = 0;

	}

	private void increseSpeed() {
		if (getScore() > 0) {

			if (((int) (getScore() / 4000) + 1) > level) {
				difficulty += 0.05;
				level = ((int) (getScore() / 4000) + 1);
				for (Enemy e : enemies) {
					e.increseStep();
				}

			}
			// level = (int) (getScore() / 4000) + 1;
			// if (level - level_before == 1) {
			// difficulty += 0.05;
			// System.out.println(difficulty);
			// level_before++;
			// }
			// level = level_before;
		}
	}

	private void moveEnemy() {
		Iterator<Enemy> e_iter = enemies.iterator();
		while (e_iter.hasNext()) {
			Enemy e = e_iter.next();
			e.move();

			if (!e.isAlive()) {
				e_iter.remove();
				gp.sprites.remove(e);
				score += 10;
			}
		}

	}

	private void moveBullet() {
		Iterator<Bullet> b_iter = bullets.iterator();
		while (b_iter.hasNext()) {
			Bullet b = b_iter.next();
			b.move();

			if (!b.isAlive()) {
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
	}

	private void moveItem() {
		Iterator<Item> i_iter = items.iterator();
		while (i_iter.hasNext()) {
			Item i = i_iter.next();
			i.move();

			if (!i.isAlive()) {
				i_iter.remove();
				gp.sprites.remove(i);
			}
		}
	}

	private void checkBulletHit() {
		for (Iterator<Bullet> b_iter = bullets.iterator(); b_iter.hasNext();) {
			Bullet b = b_iter.next();
			Rectangle2D.Double br = b.getRectangle();
			for (Iterator<Enemy> e_iter = enemies.iterator(); e_iter.hasNext();) {
				Enemy e = e_iter.next();
				Rectangle2D.Double er = e.getRectangle();
				if (er.intersects(br)) {
					// Enemy Die!!!
					generateItem(e);
					b_iter.remove();
					gp.sprites.remove(b);
					e_iter.remove();
					gp.sprites.remove(e);
					score += 100;
					e.die();
					b.die();
					break;
				}
			}
		}
	}

	private void checkItemHit() {
		Rectangle2D.Double vr = v.getRectangle();
		for (Iterator<Item> i_iter = items.iterator(); i_iter.hasNext();) {
			Item i = i_iter.next();
			Rectangle2D.Double ir = i.getRectangle();
			if (ir.intersects(vr)) {
				i.proceed(time);
				if(i instanceof ItemIncreaseLife)
					life++;
				i.die();
				i_iter.remove();
				gp.sprites.remove(i);
			}
		}
	}

	private void checkHit() {
		Rectangle2D.Double vr = v.getRectangle();
		for (Iterator<Enemy> e_iter = enemies.iterator(); e_iter.hasNext();) {
			Enemy e = e_iter.next();
			Rectangle2D.Double er = e.getRectangle();
			if (er.intersects(vr)) {
				// e.proceed(time);
				if (v.isVisible()) {
					if (life == 0) {
						die();
						return;
					} else {
						e.die();
						e_iter.remove();
						gp.sprites.remove(e);
						life--;
						v.resetBullet();
						// break;
					}
				}
			}
		}

	}

	/*
	 * private void checkHit() { Rectangle2D.Double vr = v.getRectangle();
	 * Rectangle2D.Double er; for (Enemy e : enemies) { er = e.getRectangle();
	 * if (er.intersects(vr)) { if (v.isVisible()) { if (life == 0) { die();
	 * return; } else { life--; break; } }
	 * 
	 * } } }
	 */
	public void die() {
		timer.stop();
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
			shoot(v);
			break;
		case KeyEvent.VK_Z:
			// generateItem();
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		}
	}

	@Override
	public long getScore() {
		return score;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}
}
