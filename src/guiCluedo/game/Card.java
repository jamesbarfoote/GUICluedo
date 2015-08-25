package guiCluedo.game;

public abstract class Card {
	
	/**
	 * Checks if this card is equal to the another.
	 * @param card - The card you're checking against
	 * @return boolean
	 */
	public boolean equals(Card card){
		if(card.getName().equals(this.getName())){
			return true;
		}
		return false;
	}

	/**
	 * Returns the name of the card
	 * @return String
	 */
	public String getName() {
		if(this instanceof Room){
			Room room = (Room) this;
			return room.getName();
		}
		else if(this instanceof Weapon){
			Weapon weapon = (Weapon) this;
			return weapon.getName();
		}
		else if(this instanceof Character){
			Character character = (Character) this;
			return character.getName();
		}
		return null;
	}
}