package com.dylanscode.ga;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dylanscode.math.Vector2D;

public class Target
{
	public Vector2D position;
	protected int red;
	protected int green;
	protected int blue;
	private Rectangle rect;

	public Target(Vector2D position)
	{
		this.position = position;
		this.red = (int) (Math.random() * 256);
		this.green = (int) (Math.random() * 256);
		this.blue = (int) (Math.random() * 256);
		rect = new Rectangle((int) Math.round(position.x) - 20, (int) Math.round(position.y) - 20, 40, 40);
	}

	public void update()
	{
		rect = new Rectangle((int) Math.round(position.x) - 20, (int) Math.round(position.y) - 20, 40, 40);
	}

	public void draw(Graphics2D g)
	{
		g.setColor(new Color(red, green, blue, 150));
		g.fill(rect);
		g.draw(rect);
	}

	public Rectangle getRect()
	{
		return rect;
	}
}
