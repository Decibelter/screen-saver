package com.base.game;

import com.base.engine.GameObject;

public class Util
{
	public static boolean LineOfSight(GameObject go1, GameObject go2)
	{
		return true;
	}
	
	public static float dist(float x1, float y1, float x2, float y2)
	{
		return (float) Math.hypot(x2 - x1, y2 - y1);			
	}
}
