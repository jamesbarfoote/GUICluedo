package guiCluedo.game;

public class Character extends Card{
	String name;
	
	public Character(String name){
		this.name = name;
	}

	/**
	 * Returns the name of the character
	 * @return String
	 */
	public String getName() {
		return name;
	}
}