package guiCluedo.game;

public class Weapon extends Card{
	String name;
	
	public Weapon(String name){
		this.name = name;
	}

	/**
	 * Returns the name of the weapon
	 * @return String
	 */
	public String getName() {
		return name;
	}
}