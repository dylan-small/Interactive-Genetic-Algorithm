package com.dylanscode.ga;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.dylanscode.game.Game;
import com.dylanscode.math.Vector2D;

public class Obstacle
{
	private Vector2D position;
	private int size;
	private Color color;
	private Rectangle rect;
	private boolean isEnabled;

	public Obstacle(Vector2D position, int size)
	{
		this.position = position;
		this.size = size;
		color = new Color(Game.target.red, Game.target.green, Game.target.blue,150);
		rect = new Rectangle((int) (position.x - (size / 2.0)), (int) (position.y - (size / 2.0)), size, size);
		isEnabled = false;
	}

	public void update()
	{
		rect = new Rectangle((int) (position.x - (size / 2.0)), (int) (position.y - (size / 2.0)), size, size);
	}

	public void draw(Graphics2D g)
	{
		if(this.isEnabled()) {
			g.setColor(color);
			g.fill(rect);
			g.draw(rect);
		}
	}

	public Rectangle getRect()
	{
		return rect;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	public boolean isOnMouse(int mx, int my) {
		return this.getRect().contains(new Point(mx,my));
	}
}
