package guiCluedo.game;

import java.awt.Point;

public class Weapon extends Card{
	String name;
	Point location;
	
	public Weapon(String name, Point location){
		this.name = name;
		this.location = location;
	}

	/**
	 * Returns the name of the weapon
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point location){
		this.location = location;
	}
}