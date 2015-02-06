package com.base.game.gameobject;

import java.util.ArrayList;

import com.base.engine.GameObject;
import com.base.engine.Sprite;
import com.base.game.Delay;
import com.base.game.Game;
import com.base.game.Time;
import com.base.game.Util;

public class Enemy extends StatObject
{
	public static final float DAMPING = 0.5f;
	
	private StatObject target;
	private float attackRange;
	private int attackDamage;
	private Delay attackDelay; 
	private float sightRange;
	
	public Enemy(int level)
	{
		stats = new Stats(level, false);
		target = null;
		attackDelay = new Delay(500);
		attackRange = 48f;
		attackDamage = 1;
		attackDelay.terminate();
		sightRange = 128;
	}
	
	@Override
	public void update()
	{
		if(target == null)
			Look();
		else
		{
			if(Util.LineOfSight(this, target) && Util.dist(x, y, getTarget().getX(), getTarget().getY()) <= attackRange)
			{
				if(attackDelay.isOver())
					Attack();
			}
			else
				Chase();
		}
		
		if(stats.getCurrentHealth() <= 0)
			Death();
	}
	
	protected void Attack()
	{
		getTarget().damage(getAttackDamage());
		System.out.println("Player HP: " + getTarget().getCurrentHealth() + "/" + getTarget().getMaxHealth());
		attackDelay.restart();
	}
	
	protected void Death()
	{
		remove();
	}
	
	protected void Look()
	{
		ArrayList<GameObject> objects = Game.sphereCollide(x, y, sightRange);
		
		for(GameObject go : objects)
			if(go.getType() == PLAYER_ID)
				setTarget((StatObject)go);
	}
	
	protected void Chase()
	{
		float speedX = (getTarget().getX() - x);
		float speedY = (getTarget().getY() - y);
		
		float maxSpeed = getStats().getSpeed() * DAMPING;
		
		if(speedX > maxSpeed)
			speedX = maxSpeed;
		if(speedX < -maxSpeed)
			speedX = -maxSpeed;
		
		if(speedY > maxSpeed)
			speedY = maxSpeed;
		if(speedY < -maxSpeed)
			speedY = -maxSpeed;
		
		x += speedX * Time.getDelta();
		y += speedY * Time.getDelta();
		
		/*
		 * Implementation 1 (I succeeded where Benny failed) 
		float nextX = getTarget().getX() - x;
		float nextY = getTarget().getY() - y;
		
		if(nextX > getStats().getSpeed() * DAMPING)
			nextX = getStats().getSpeed() * DAMPING;
		if(nextY > getStats().getSpeed() * DAMPING)
			nextY = getStats().getSpeed() * DAMPING;
		if(nextX < -getStats().getSpeed() * DAMPING)
			nextX = -getStats().getSpeed() * DAMPING;
		if(nextY < -getStats().getSpeed() * DAMPING)
			nextY = -getStats().getSpeed() * DAMPING;
		
		x += nextX;
		y += nextY;
		 */
	}
	
	public void setTarget(StatObject go)
	{
		target = go;
	}
	
	public StatObject getTarget()
	{
		return target;
	}
	
	public Stats getStats()
	{
		return stats;
	}
	
	public int getAttackDamage()
	{
		return attackDamage;
	}
	
	public void setAttackRange(int range)
	{
		attackRange = range;
	}
	
	public void setAttackDelay(int time)
	{
		attackDelay = new Delay(time);
		attackDelay.terminate();
	}
	
	public void setAttackDamage(int amt)
	{
		attackDamage = amt;
	}
	
	public void setSightRange(float distance)
	{
		sightRange = distance;
	}
	
	@Override
	protected void init(float x, float y, float r, float g, float b, float sx, float sy, int type)
	{
		this.x = x;
		this. y = y;
		this.type = ENEMY_ID;
		this.spr = new Sprite(x, y, r, g, b, sx, sy);
	}
}
