package guiCluedo.game;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Synthesizer;

public class Guess {

	private Player player;
	private boolean won = false;
	private boolean failed = false;
	private Player eliminatedPlayer;

	public Guess(boolean suggestion, List<Card> guess, Player player, Board b) {
		this.player = player;
		if (suggestion) {
			Suggestion(guess, b);
		} else {
			Accusation(guess, b);
		}
	}

	public void moveIcons(List<Card> guess, Board b){
		Room currentRoom = player.getRoom();
		Weapon weapon = null;
		Character character = null;
		for(Card card : guess){
			if (card instanceof Weapon){
				weapon = (Weapon) card;
			}
		}
		for(Card card : guess){
			if (card instanceof Character){
				character = (Character) card;
			}
		}
		int count = 0;
		Player suggestedPlayer = null;
		for(Player player : b.getPlayers()){
			if(player.getCharacterName().equals(character.getName())){
				suggestedPlayer = player;
			}
		}
		for(int i = 0; i < 22; i++){
			for(int j = 0; j < 23; j++){
				if(currentRoom.getBoundingBox().contains(i, j)){
					Point p = new Point(i, j);
					if(!b.getUsedSquares().contains(p) && (count == 0) && (!currentRoom.getBoundingBox().contains(weapon.getLocation()))){
						b.getUsedSquares().remove(weapon.getLocation());
						weapon.setLocation(p);
						b.getUsedSquares().add(weapon.getLocation());
						count++;
					}
					else if(currentRoom.getBoundingBox().contains(weapon.getLocation()) && count == 0){
						count++;
					}
					else if(!b.getUsedSquares().contains(p) && (count == 1) && (!currentRoom.getBoundingBox().contains(suggestedPlayer.getLocation()))){
						b.getUsedSquares().remove(suggestedPlayer.getLocation());
						suggestedPlayer.setLocation(p);
						b.getUsedSquares().add(suggestedPlayer.getLocation());
						count++;
					}
				}
			}
		}
	}



	/**
	 * Makes a suggestion based on the 3 cards given,
	 * if suggestion is valid and not the answer then a
	 * card is discovered for every player.
	 * @param guess - The 3 cards to be suggested
	 * @param b - The board to make a suggestion on.
	 */
	private void Suggestion(List<Card> guess, Board b) {
		Room room = null;
		Card discoveredCard = null;
		ArrayList<Player> players = b.players;
		for (Card card : guess) {
			System.out.println("reached");
			if (card instanceof Room) {	//Grab the room from the 3 suggested cards
				room = (Room) card;
			}
		}

		if (player.getRoom().equals(room)) {	//If player is in the suggested room
			for (Card card : guess) {
				int playerNum = player.getNum();
				playerNum = (playerNum % b.players.size()) + 1;
				int start = player.getNum();
				while (playerNum != start) {	//iterate over all the other players
					ArrayList<Card> cards = players.get(playerNum-1).getHand();
					for (Card c : cards) {	//Check the hand of all the other players
						if (c.equals(card)) { //If a player has one of the suggested cards in their hand
							discoveredCard = c;
							System.out.println(players.get(playerNum-1).getName());
							break;
						}
					}
					playerNum = (playerNum % b.players.size()) + 1;
					if (discoveredCard != null) {
						break;
					}
				}
				if (discoveredCard != null) {
					break;
				}
			}
		}
		else{
			System.out.println("Must be in the room to suggest it");
			this.failed= true;
			return;
		}
		if (discoveredCard != null) {
			for (Player p : players) {
				p.addToDiscoveredCards(discoveredCard);	//Add the discovered card to the list of discovered cards in every player
			}
		} else {
			System.out.println("No player had any of the suggested cards");
			return;
		}
		System.out.println("Discovered card was: " + discoveredCard.getName() + " this card has been added to everyones hand");
	}

	/**
	 * Check to see if the Accusation is equal to the answer, if it is then the
	 * player who made the accusation wins. Otherwise they are eliminated from
	 * the game and their cards are delegated out to the remaining players.
	 * @param guess - The 3 cards to be accused
	 * @param b - The board to make an accusation on.
	 */
	private void Accusation(List<Card> guess, Board b) {
		boolean entered = false;
		for (Card card : guess) {
			if(!b.answer.contains(card)){	//If accusation is not correct
				entered = true;
				int playerNum = player.getNum();
				playerNum = (playerNum % b.players.size()) + 1;
				int start = player.getNum();
				while (true) {	//Distribute the cards in the current players hand to all the remaining players.
					if(player.getHand().isEmpty()){
						break;
					}
					if(b.players.get(playerNum-1)!=player){
						b.players.get(playerNum-1).addToHand(player.getHand().get(0));
						player.getHand().remove(0);
					}
					playerNum = (playerNum % b.players.size()) + 1;
					if(playerNum == start){
						playerNum = (playerNum % b.players.size()) + 1;
					}
				}
				System.out.println("Eliminated player was: " + player.getName());
				eliminatedPlayer = player;

			}
		}
		if(entered == false && !guess.contains(null)){
			this.won = true;
		}

	}

	public boolean hasWon(){
		return won;
	}

	public Player getEliminatedPlayer(){
		return eliminatedPlayer;
	}

	public boolean getFailed(){
		return failed;
	}
}