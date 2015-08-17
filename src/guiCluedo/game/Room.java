package guiCluedo.game;

public class Room extends Card{
	private String name;
	private Location location;
	
	public Room(String name, Location location){
		this.name = name;
		this.location = location;
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
}