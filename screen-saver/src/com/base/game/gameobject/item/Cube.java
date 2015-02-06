package com.base.game.gameobject.item;

public class Cube extends Item
{
	public static final float SIZE = 24;
	
	public Cube(float x, float y)
	{
		init(x, y, 0.5f, 0.7f, 0.9f, SIZE, SIZE, "The Cube");
	}
	
}
