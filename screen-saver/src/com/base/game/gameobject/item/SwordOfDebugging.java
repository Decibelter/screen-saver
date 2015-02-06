package com.base.game.gameobject.item;

public class SwordOfDebugging extends EquippableItem
{
	public static final float SIZE = 32;
	
	private int damage;
	
	public SwordOfDebugging(float x, float y)
	{
		init(x, y, 0.5f, 0.7f, 0.9f, SIZE, SIZE, "The Legendary Sword of Debuggery", WEAPON_SLOT);
		damage = 3;
	}
}
