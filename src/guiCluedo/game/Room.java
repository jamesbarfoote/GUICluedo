package guiCluedo.game;

import java.awt.Point;
import java.awt.Polygon;

public class Room extends Card{
	private String name;
	private Point location;
	private Polygon boundingBox;
	
	public Room(String name, Polygon polygon, Point location){
		this.name = name;
		this.boundingBox = polygon;
		this.location = location;
	}
	
	/**
	 * Returns the location of the room where the name will be drawn.
	 * @return Location
	 */
	public Point getLocation(){
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