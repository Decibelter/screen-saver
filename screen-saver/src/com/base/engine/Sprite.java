package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.base.game.Game;

public class Sprite
{
	private float r;
	private float g;
	private float b;
	
	private float x;
	private float y;
	private float sx;
	private float sy;
	
	public Sprite(float x, float y, float r, float g, float b, float sx, float sy)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
		this.sx = sx;
		this.sy = sy;
	}
	
	public void render()
	{
		/*
		 * Outdated code
		glColor3f(r, g, b);
		glBegin(GL_QUADS);
		{
			glVertex2f(0, 0);
			glVertex2f(0, sy);
			glVertex2f(sx, sy);
			glVertex2f(sx, 0);
		}
		glEnd();
		*/
		//FloatBuffer vertices = BufferUtils.createFloatBuffer(12);
		
        // Add vertices of our sprite to build two triangles making a quad
        Game.vertices.put(new float[]
                {
                        x, y,  0.0f,
                        x, sy,  0.0f,
                        sx, sy,  0.0f,
                        sx, sy,  0.0f,
                        x,  y,  0.0f,
                        x, y,  0.0f
                });
        System.out.println(Game.vertices);
        // Rewind the vertices
        Game.vertices.rewind();
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
		
	public float getSX()
	{
		return sx;
	}
	
	public float getSY()
	{
		return sy;
	}
	
	public void setSX(float sx)
	{
		this.sx = sx;
	}
	
	public void setSY(float sy)
	{
		this.sy = sy;
	}
}
