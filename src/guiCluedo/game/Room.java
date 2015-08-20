package guiCluedo.game;

import java.awt.Polygon;

public class Room extends Card{
	private String name;
	private Location location;
	private Polygon boundingBox;
	
	public Room(String name, Location location, Polygon polygon){
		this.name = name;
		this.location = location;
		this.boundingBox = polygon;
	}
	
	/**
	 * Returns the location of the room.
	 * @return Location
	 */
	public Location getLocation(){
		return location;
	}
	
	/**
	 * Returns the name of the room
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the polygon of the room
	 * @return Polygon
	 */
	public Polygon getBoundingBox(){
		return boundingBox;
	}
}