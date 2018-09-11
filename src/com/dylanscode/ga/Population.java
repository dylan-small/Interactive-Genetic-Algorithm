package com.dylanscode.ga;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.dylanscode.game.Game;
import com.dylanscode.math.GameMath;

public class Population
{
	public Cell[] cells;
	public ArrayList<Cell> pool;
	public static final int CELLS_SIZE = 50;
	public Population()
	{
		initializeCells();
	}

	public Population(Cell[] cells)
	{
		this.cells = cells;
	}

	public void initializeCells()
	{
		cells = new Cell[CELLS_SIZE];
		for (int i = 0; i < cells.length; i++)
		{
			cells[i] = new Cell();
		}
	}

	public void evaluate()
	{
		double max = 0;
		for (Cell b : cells)
		{
			b.calculateFitness();
			if (b.getFitness() > max)
			{
				max = b.getFitness();
			}
		}
		Game.MAX_FITNESS = max;
		for (Cell b : cells)
		{
			b.setFitness(b.getFitness() / max);
		}
		pool = new ArrayList<Cell>();
		for (int i = 0; i < cells.length; i++)
		{
			int frequency = (int) Math.pow(cells[i].getFitness(),10);
			for(int j = 0; j<frequency;j++) {
				pool.add(cells[i]);
			}
		}
	}

	public Cell[] getBest()
	{
		Cell[] newCells = new Cell[CELLS_SIZE];
		for(int i = 0;i<CELLS_SIZE;i++) {
			DNA mother = GameMath.random(pool).getDNA();
			DNA father = GameMath.random(pool).getDNA();
			DNA child = DNA.cross(mother,father);
			if((int) (Math.random() * 5) == 0) {
				child.mutate();
			}
			child.differ();
			newCells[i] = new Cell(child);
		}
		return newCells;
	}

	public void update()
	{
		for (Cell b : cells)
		{
			b.update();
		}
		Game.DNA_INDEX++;
	}

	public void draw(Graphics2D g)
	{
		for (Cell b : cells)
		{
			b.draw(g);
		}
	}
}
