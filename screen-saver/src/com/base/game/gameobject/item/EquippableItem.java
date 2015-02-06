package com.base.game.gameobject.item;

import com.base.engine.Sprite;

public class EquippableItem extends Item
{
	public static final int NUM_SLOTS = 4;
	
	public static int WEAPON_SLOT = 0;
	public static int HEAD_SLOT = 1;
	public static int BODY_SLOT = 2;
	public static int LEG_SLOT = 3;
	
	//Which body part holds this item
	private int slot;
	
	protected void init(float x, float y, float r, float g, float b, float sx, float sy, String name, int slot)
	{
		this.x = x;
		this. y = y;
		this.type = ITEM_ID;
		this.slot = slot;
		this.spr = new Sprite(x, y, r, g, b, sx, sy);
		this.name = name;
	}
	
	public int getSlot()
	{
		return slot;
	}
}
