package com.dylanscode.math;

public class Vector2D
{
	public double x;
	public double y;
	public double angle;

	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.angle = angleInRadians();
	}

	public double angleInDegrees()
	{
		if (x == 0)
		{
			return Math.atan(y / 1) * (180 / Math.PI);
		}
		return Math.atan(y / x) * (180 / Math.PI);
	}

	public double angleInRadians()
	{
		return Math.atan(y / x);
	}

	public static Vector2D createEmptyVector()
	{
		return new Vector2D(0, 0);
	}

	public static Vector2D createRandomVector()
	{
		return new Vector2D((Math.random() * 2) - 1, (Math.random() * 2) - 1);
	}

	public void add(Vector2D v)
	{
		this.x += v.x;
		this.y += v.y;
	}

	public void subtract(Vector2D v)
	{
		this.x -= v.x;
		this.y -= v.y;
	}

	public void multiply(double m)
	{
		this.x *= m;
		this.y *= m;
	}

	public void divide(double m)
	{
		this.x /= m;
		this.y /= m;
	}

	public double getMagnitude()
	{
		return Math.sqrt((x * x + y * y));
	}

	public void normalize()
	{
		double mag = this.getMagnitude();
		if (mag != 0 && mag != 1)
		{
			divide(mag);
		}
	}

	public void setMagnitude(double newMag)
	{
		normalize();
		this.multiply(newMag);
	}

	@Override
	public String toString()
	{
		return "[ " + this.x + " , " + this.y + " ]";
	}
}
