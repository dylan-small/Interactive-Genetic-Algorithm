package com.dylanscode.ga;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dylanscode.game.Game;
import com.dylanscode.math.GameMath;
import com.dylanscode.math.Vector2D;

public class Cell
{
	private Vector2D position;
	private Vector2D velocity;
	private Vector2D acceleration;

	private DNA dna;
	private Color color;
	private Rectangle rect;
	//all fitness-worthy calculations
	private double fitness;
	private int finish;
	private double closestDistToTarget = Integer.MAX_VALUE;
	
	private boolean dead;
	private boolean finished;

	public Cell()
	{
		position = Game.getStart();
		velocity = Vector2D.createEmptyVector();
		acceleration = Vector2D.createEmptyVector();
		color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256), 150);
		this.dna = new DNA();
		rect = new Rectangle((int) Math.round(position.x) - 10, (int) Math.round(position.y) - 10, 20, 20);
	}

	public Cell(DNA dna)
	{
		position = Game.getStart();
		velocity = Vector2D.createEmptyVector();
		acceleration = Vector2D.createEmptyVector();
		color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256), 150);
		this.dna = dna;
		rect = new Rectangle((int) Math.round(position.x) - 10, (int) Math.round(position.y) - 10, 20, 20);
	}

	public void draw(Graphics2D g)
	{
		g.rotate(Math.toRadians(velocity.angleInDegrees()), position.x, position.y);
		g.setColor(color);
		g.fill(rect);
		g.draw(rect);
		g.rotate(-1 * (Math.toRadians(velocity.angleInDegrees())), position.x, position.y);
	}

	public void update()
	{
		if (!this.isDead() && !this.isFinished())
		{
			addForce(dna.genes[Game.DNA_INDEX]);
			acceleration.setMagnitude(0.15);
			velocity.add(acceleration);
			position.add(velocity);
			acceleration.multiply(0);
			rect = new Rectangle((int) Math.round(position.x) - 10, (int) Math.round(position.y) - 10, 20, 20);
			double dtt = GameMath.distance(this.position.x,this.position.y,Game.target.position.x,Game.target.position.y);
			if(dtt < this.closestDistToTarget)
				this.closestDistToTarget = dtt;
			if ((!this.isDead())
					&& (this.position.x > Game.WIDTH || this.position.x < 0 || this.position.y > Game.HEIGHT
							|| this.position.y < 0))
			{
				this.setDead(true);
				finish = Game.DNA_INDEX;
			}
			if (!this.isFinished() && Game.target.getRect().intersects(this.getRect()))
			{
				this.setFinished(true);
				finish = Game.DNA_INDEX;
			}
		}
	}

	public void calculateFitness()
	{
		//reset fitness
		fitness = 0;
		//fitness as function of closeness to target
		fitness += 100 / closestDistToTarget;
		//fitness multiplies if finished, as a function of the time it finishes in
		if(this.isFinished()) {
			fitness *= (8 + ((DNA.MAX_DNA * 10)/(finish * 5)));
		}
		//fitness decreases if dead, as a function of the time it dies
		if(this.isDead()) {
			fitness *= 20/closestDistToTarget;
		}
	}

	public void addForce(Vector2D v)
	{
		acceleration.add(v);
	}

	public Rectangle getRect()
	{
		return rect;
	}

	public boolean isDead()
	{
		return dead;
	}

	public void setDead(boolean dead)
	{
		this.dead = dead;
	}

	public boolean isFinished()
	{
		return finished;
	}

	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}

	public DNA getDNA()
	{
		return dna;
	}

	public double getFitness()
	{
		return fitness;
	}

	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}
	public void setFinish(int finish) {
		this.finish = finish;
	}
}
