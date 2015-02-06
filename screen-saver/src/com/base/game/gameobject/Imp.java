package com.base.game.gameobject;

public class Imp extends Enemy
{
	public static final int SIZE = 24;
	
	public Imp(float x, float y, int level)
	{
		super(level);
		init(x, y, 0.7f, 0.0f, 0.3f, SIZE, SIZE, 0);
		setAttackDelay(200);
	}
}
