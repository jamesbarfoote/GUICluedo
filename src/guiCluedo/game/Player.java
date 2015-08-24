package guiCluedo.game;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
	private ArrayList<Card> discoveredCards = new ArrayList<Card>();	//Acts as the paper each player ticks weapons, characters and rooms off of.
	private ArrayList<Card> hand = new ArrayList<Card>();				//The starting hand
	private int roll;
	private Map<Room, Integer> roomDistances = new HashMap<Room, Integer>();	//The distances from current location to all the rooms and stairwells
	private String name;
	private Point location;
	private int playerNum;
	private String character;
	private Color colour;
	private Room currentRoom = null;

	public Player (String name, String character, Color colour, int playerNum){
		this.name = name;
		this.character = character;
		this.colour = colour;
		this.playerNum = playerNum;	
		
		if(character.equals("Miss Scarlett")){
			this.location = new Point(21, 12);
		}
		if(character.equals("Colonel Mustard")){
			this.location = new Point(15,22);
		}
		if(character.equals("Mrs. White")){
			this.location = new Point(0, 12);
		}
		if(character.equals("The Reverend Green")){
			this.location = new Point(0, 8);
		}
		if(character.equals("Mrs. Peacock")){
			this.location = new Point(3, 0);
		}
		if(character.equals("Professor Plum")){
			this.location = new Point(18, 0);
		}
		
		System.out.println(character);
		//Set location based on what character they chose (each character has a specified starting location)
	}

	/**
	 * Rolls the dice
	 * @return int of the number rolled
	 */
	public int rollDice(){
		roll = ((int) Math.ceil(Math.random()*11)) + 1; // generate a random number between 2 and 12 inclusive
		return (roll);
	}
	
	public void addToHand(Card c){
		discoveredCards.add(c);
		hand.add(c);
	}
	
	public void addToDiscoveredCards(Card c){
		discoveredCards.add(c);
	}
	
	public Point getLocation(){
		return location;
	}

	public ArrayList<Card> getDiscoveredCards(){
		return discoveredCards;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}

	public int getRoll(){
		return roll;
	}

	public Map<Room, Integer> getRoomDist(){
		return roomDistances;
	}
	
	public int getNum(){
		return playerNum;
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return colour;
	}
	
	public void setRoll(int roll){
		this.roll = roll;
	}
	
	public void setLocation(Point p){
		this.location = p;
	}
	
	public Room getRoom(){
		return currentRoom;
	}
	
	public void setRoom(Room r){
		this.currentRoom = r;
	}
}