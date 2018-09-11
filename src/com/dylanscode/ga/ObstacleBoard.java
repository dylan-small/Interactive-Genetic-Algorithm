package com.dylanscode.ga;

import java.awt.Graphics2D;

import com.dylanscode.game.Game;
import com.dylanscode.math.Vector2D;

public class ObstacleBoard
{
	Obstacle[][] obstacles;
	private int amountOfObstaclesX;
	private int amountOfObstaclesY;
	private int obstacleSize;

	public ObstacleBoard(int amountOfObstaclesX)
	{
		this.amountOfObstaclesX = amountOfObstaclesX;
		this.amountOfObstaclesY = ((Game.HEIGHT * amountOfObstaclesX) / Game.WIDTH);
		obstacleSize = Game.WIDTH / this.amountOfObstaclesX;
		obstacles = generateObstacles();
	}

	public Obstacle[][] generateObstacles()
	{
		this.amountOfObstaclesX += 14;
		this.amountOfObstaclesY += 9;
		Obstacle[][] obstacles = new Obstacle[this.amountOfObstaclesY][this.amountOfObstaclesX];
		int xC = 0;
		int yC = 0;
		for (int x = 0; x < obstacles.length; x++)
		{
			for (int y = 0; y < obstacles[x].length; y++)
			{
				obstacles[x][y] = new Obstacle(new Vector2D(xC, yC), obstacleSize);
				xC += obstacleSize;
			}
			xC = 0;
			yC += obstacleSize;
		}
		return obstacles;
	}

	public void draw(Graphics2D g)
	{
		for (int i = 0; i < obstacles.length; i++)
		{
			for (int j = 0; j < obstacles[i].length; j++)
			{
				obstacles[i][j].draw(g);
			}
		}

	}

	public void update(Population population)
	{
		for (int x = 0; x < obstacles.length; x++)
		{
			for (int y = 0; y < obstacles[x].length; y++)
			{
				if (obstacles[x][y].isEnabled())
				{
					for (Cell b : population.cells)
					{
						if (obstacles[x][y].getRect().intersects(b.getRect()))
						{
							b.setDead(true);
							b.setFinish(Game.DNA_INDEX);
						}
					}
				}
			}
		}
	}

	public void toggleObstacle(int mx, int my, boolean enabled)
	{
		for (int i = 0; i < obstacles.length; i++)
		{
			for (int j = 0; j < obstacles[i].length; j++)
			{
				if (obstacles[i][j].isOnMouse(mx, my))
				{
					obstacles[i][j].setEnabled(enabled);
				}
			}
		}
	}
}
