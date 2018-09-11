package com.dylanscode.math;

import java.util.ArrayList;

import com.dylanscode.ga.Cell;

public class GameMath
{
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static Cell random(ArrayList<Cell> cells)
	{
		return cells.get((int) (Math.random() * cells.size()));
	}
}
