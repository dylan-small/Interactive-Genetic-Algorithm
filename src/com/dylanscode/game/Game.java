package com.dylanscode.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.dylanscode.ga.Cell;
import com.dylanscode.ga.DNA;
import com.dylanscode.ga.ObstacleBoard;
import com.dylanscode.ga.Population;
import com.dylanscode.ga.Target;
import com.dylanscode.input.KeyHandler;
import com.dylanscode.input.MouseHandler;
import com.dylanscode.input.MouseMotionHandler;
import com.dylanscode.math.Vector2D;

public class Game extends JFrame implements Runnable
{

	private static final long serialVersionUID = 1L;
	public boolean running = false;
	private boolean debug = false;

	private Graphics g;
	private BufferStrategy bs;

	public static int WIDTH;
	public static int HEIGHT;

	public static Population population;
	public static Target target;
	public static ObstacleBoard board;
	public static int DNA_INDEX;
	public static int GENERATION;
	public static double MAX_FITNESS;

	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private MouseMotionHandler mouseMotionHandler;

	public void init()
	{
		setName("Genetic Algorithm");
		setUndecorated(true);
		setAlwaysOnTop(true);
		setVisible(true);
		setResizable(false);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		try
		{
			gd.setFullScreenWindow(this);
		} catch (Exception e)
		{
			gd.setFullScreenWindow(null);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		setMinimumSize(screen);
		setPreferredSize(screen);
		setMaximumSize(screen);

		pack();
		setLocationRelativeTo(null);

		createBufferStrategy(4);

		WIDTH = getWidth();
		HEIGHT = getHeight();

		keyHandler = new KeyHandler(this);
		mouseHandler = new MouseHandler(this);
		mouseMotionHandler = new MouseMotionHandler(this,mouseHandler);

		population = new Population();
		target = new Target(new Vector2D(WIDTH / 2, HEIGHT / 6));
		board = new ObstacleBoard(200);
		DNA_INDEX = 0;
		GENERATION = 0;

		bs = getBufferStrategy();
		g = getGraphics();
	}

	public void tick()
	{
		if (keyHandler.ESC.isPressed())
		{
			System.exit(0);
		}
		if(mouseHandler.LEFT_CLICK.isClicked() || mouseMotionHandler.handler.LEFT_CLICK.isDragged()) {
			int ex = mouseHandler.LEFT_CLICK.getX();
			int ey = mouseHandler.LEFT_CLICK.getY();
			board.toggleObstacle(ex, ey,true);
		}
		if(mouseHandler.RIGHT_CLICK.isClicked() || mouseMotionHandler.handler.RIGHT_CLICK.isDragged()) {
			int ex = mouseHandler.RIGHT_CLICK.getX();
			int ey = mouseHandler.RIGHT_CLICK.getY();
			board.toggleObstacle(ex, ey, false);
		}
		if(true) {
			//debug stuff!
		}
		if (DNA_INDEX >= DNA.MAX_DNA)
		{
			advance();
			DNA_INDEX = 0;
		}
		population.update();
		board.update(population);
	}

	public void advance()
	{
		population.evaluate();
		Cell[] newCells = population.getBest();
		population = new Population(newCells);
		GENERATION++;
	}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2 = null;
		if (bs != null)
		{
			do
			{
				try
				{
					g2 = (Graphics2D) bs.getDrawGraphics();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					g2.setColor(Color.black);
					g2.fillRect(0, 0, getWidth(), getHeight());

					population.draw(g2);
					target.draw(g2);
					board.draw(g2);
					
					g2.setColor(Color.white);
					g2.setFont(new Font("TimesRoman",Font.PLAIN,20));
					g2.drawString("Generation: " + GENERATION, 5, 15);
					g2.drawString("Max Fitness: " + MAX_FITNESS, 5, 35);
					
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					if (g2 != null)
					{
						g2.dispose();
					}
				}
				bs.show();
			} while (bs.contentsLost());
		}
	}

	public synchronized void start()
	{
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop()
	{
		running = false;
	}

	@Override
	public void run()
	{
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000 / 60D;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		init();
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			while (delta >= 1)
			{
				ticks++;
				tick();
				delta -= 1;
			}
			frames++;
			paint(g);
			if (System.currentTimeMillis() - lastTimer > 1000)
			{
				lastTimer += 1000;
				if (debug)
					System.out.println("[Debug] Frames: " + frames + " | Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public static void main(String[] args)
	{
		new Game().start();
	}
	public static Vector2D getStart() {
		return new Vector2D(WIDTH/2,HEIGHT-10);
	}
}
