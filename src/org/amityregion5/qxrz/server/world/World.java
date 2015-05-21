package org.amityregion5.qxrz.server.world;

import java.util.ArrayList;

import org.amityregion5.qxrz.server.world.entity.GameEntity;

public class World
{

	private ArrayList<GameEntity> entities;
	private Landscape l;	
	
	public World()
	{
		entities = new ArrayList<GameEntity>();
		l = new Landscape();
	}

	public void add(GameEntity e)
	{
		entities.add(e);
	}
	
	public void addObstacle(Obstacle o)
	{
		l.add(o);
	}
	
	public void update(double tSinceUpdate)
	{
		for (GameEntity t : entities)
		{
			t.update(tSinceUpdate, l);
		}
	}
	
}
