package com.dylanscode.ga;

import com.dylanscode.math.Vector2D;

public class DNA
{
	public Vector2D[] genes;
	public static final int MAX_DNA = 800;

	public DNA()
	{
		genes = new Vector2D[MAX_DNA];
		for (int i = 0; i < genes.length; i++)
		{
			genes[i] = Vector2D.createRandomVector();
		}
	}

	public DNA(Vector2D[] genes)
	{
		this.genes = genes;
	}

	public void mutate()
	{
		for (int i = 0; i < genes.length; i++)
		{
			if ((int) (Math.random() * 20) == 0)
			{
				genes[i] = Vector2D.createRandomVector();
			}
		}
	}

	public void differ()
	{
		for (int i = 0; i < genes.length; i++)
		{
			if ((int) (Math.random() * 100) == 0)
			{
				genes[i] = Vector2D.createRandomVector();
			}
		}
	}

	public static DNA cross(DNA mother, DNA father)
	{
		Vector2D[] newGenes = new Vector2D[MAX_DNA];
		int middle = (int) (Math.random() * (MAX_DNA + 1));
		for (int i = 0; i < MAX_DNA; i++)
		{
			if (i > middle)
			{
				newGenes[i] = mother.genes[i];
			} else
			{
				newGenes[i] = father.genes[i];
			}
		}
		return new DNA(newGenes);
	}
}
