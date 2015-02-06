package com.base.game.gameobject;
import java.util.ArrayList;

import com.base.engine.GameObject;
import com.base.game.Delay;
import com.base.game.Game;
import com.base.game.Time;
import com.base.game.Util;
import com.base.game.gameobject.item.Item;

public class Player extends StatObject
{
	public static final float SIZE = 24;
	
	public static final int FORWARD = 0;
	public static final int BACKWARD = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	private Inventory inventory;
	private Equipment equipment;
	
	private int attackRange;
	private Delay attackDelay;
	private int attackDamage;
	private int facingDirection;
	
	public Player(float x, float y)
	{
		init(x, y, 0.1f, 0.05f, 0.2f, SIZE, SIZE, PLAYER_ID);
		stats = new Stats(0, true);
		inventory = new Inventory(20);
		equipment = new Equipment(inventory);
		attackDelay = new Delay(500);
		attackRange = 49;
		attackDamage = 1;
		facingDirection = 0;
		attackDelay.terminate();
	}
	
	@Override
	public void update()
	{	
		//System.out.println("STATS: SPEED: " + getSpeed() + " LEVEL: " + getLevel() + " MAXHP: " + getMaxHealth() + " HP: " + getCurrentHealth() + " STRENGTH: " + getStrength() + " MAGIC: " + getMagic());
		ArrayList<GameObject> objects = Game.rectangleCollide(x, y, x + SIZE, y + SIZE);
				
		for(GameObject go : objects)
			if(go.getType() == GameObject.ITEM_ID)
			{
				System.out.println("You just picked up xXx_" + ((Item)go).getName() + "_xXx!");
				go.remove();
				addItem((Item)go);
			}
	}
	
	public void getInput()
	{
		//Lol, glfw fucked this hard.
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			move(0, 1);
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			move(0, -1);
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			move(1, 0);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			move(-1, 0);
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackDelay.isOver())
			attack();
		*/
	}
	
	public void attack()
	{
		System.out.print("Attack was made by: " + this);
		
		//Find objects in attack range 
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		
		if(facingDirection == FORWARD)
			objects = Game.rectangleCollide(x, y, x + SIZE, y + attackRange);
		else if(facingDirection == BACKWARD)
			objects = Game.rectangleCollide(x, y - attackRange + SIZE, x + SIZE, y);
		else if(facingDirection == LEFT)
			objects = Game.rectangleCollide(x - attackRange + SIZE, y, x, y + SIZE);
		else if(facingDirection == RIGHT)
			objects = Game.rectangleCollide(x, y, x + attackRange/* + SIZE*/, y + SIZE);
		
		//Find which objects are enemies
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		
		for(GameObject go : objects)
		{
			if(go.getType() == ENEMY_ID)
				enemies.add((Enemy)go);
		}
		
		//Find closest enemy if one exists
		if(enemies.size() > 0)
		{
			Enemy target = enemies.get(0);
			
			if(enemies.size() > 1)
			{
				for(Enemy e : enemies)
					if(Util.dist(x, y, e.getX(), e.getY()) < Util.dist(x, y, target.getX(), target.getY()))
							target = e;
			}
			else
				target = enemies.get(0);
			
			//Attack the enemy
			target.damage(attackDamage);
			System.out.println(" : " + target.getCurrentHealth() + "/" + target.getMaxHealth());
		}
		else
			System.out.println(" : No target");
		
		attackDelay.restart();
	}
	
	private void move(float magX, float magY)
	{
		if(magX == 0 && magY == 1)
			facingDirection = FORWARD;
		if(magX == 0 && magY == -1)
			facingDirection = BACKWARD;
		if(magX == -1 && magY == 0)
			facingDirection = LEFT;
		if(magX == 1 && magY == 0)
			facingDirection = RIGHT;
		
		x += getSpeed() * magX * Time.getDelta();
		y += getSpeed() * magY * Time.getDelta();
		
		//System.out.println("Player x: " + getX() + "Player y: " + getY());
	}
	
	public void addItem(Item item)
	{
		inventory.add(item);
	}
	
	public void addXp(float amt)
	{
		stats.addXp(amt);
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
}
