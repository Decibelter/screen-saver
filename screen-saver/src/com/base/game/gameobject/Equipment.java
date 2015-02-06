package com.base.game.gameobject;

import com.base.game.gameobject.item.EquippableItem;

public class Equipment
{
	private EquippableItem[] items;
	private Inventory inv;
	
	public Equipment(Inventory inv)
	{
		items = new EquippableItem[EquippableItem.NUM_SLOTS];
		this.inv = inv;
	}
	
	public boolean equip(EquippableItem item)
	{
		int index = item.getSlot();
		
		if(items[index] != null)
		{
			if(!deEquip(index))
				return false;
		}
		
		items[index] = item;
		return true;
	}
	
	public boolean deEquip(int slot)
	{
		//Take current index and send it to the inventory
		if(inv.add(items[slot]))
		{
			items[slot] = null;
			return true;
		}
		return false;
	}
}
