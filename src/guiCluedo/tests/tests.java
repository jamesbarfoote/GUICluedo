package guiCluedo.tests;

import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import org.junit.Test;
import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Character;
import guiCluedo.game.Guess;
import guiCluedo.game.Player;
import guiCluedo.game.Room;
import guiCluedo.game.Weapon;
import guiCluedo.ui.UI;

public class tests {

	ArrayList<Player> players = new ArrayList<Player>();

	@Test
	public void correctNumPlayers(){
		Board board = createBoard();
		assertEquals(board.getPlayers().size(), players.size());
		players.clear();

	}

	@Test
	public void allRoomsCreated(){
		Board board = createBoard();
		assertEquals(board.getRooms().size(), 9);
		players.clear();
	}

	@Test
	public void allWeaponsCreated(){
		Board board = createBoard();
		assertEquals(board.getWeapons().size(), 6);
		players.clear();
	}

	@Test
	public void allCharactersCreated()
	{
		Board board = createBoard();
		assertEquals(board.getCharacters().size(), 6);
		players.clear();
	}

	@Test
	public void checkCardEquals(){
		Board board = createBoard();
		Weapon w = null;
		for(Weapon weapon : board.getWeapons()){
			if(weapon.getName().equals("Knife")){
				w = weapon;
			}
		}
		Weapon weapon = new Weapon("Knife", new Point(2,3));
		assertTrue(w.equals(weapon));
		players.clear();
	}

	@Test
	public void checkCardNotEquals(){
		Board board = createBoard();
		Weapon w = null;
		for(Weapon weapon : board.getWeapons()){
			if(weapon.getName().equals("Knife")){
				w = weapon;
			}
		}
		Weapon weapon = new Weapon("Lead Pipe", new Point(1,6));
		assertFalse(w.equals(weapon));
		players.clear();
	}

	@Test
	public void checkValidAccusation(){

		Board board = createBoard();
		ArrayList<Card> answer = board.getAnswer();
		Player p = board.getPlayers().get(0);
		Guess g = new Guess(false, answer, p, board);
		assertTrue(g.hasWon());
		players.clear();
	}

	@Test
	public void checkValidAccusation2(){ //Check that the player isn't eliminated after getting accusation right
		Board board = createBoard();
		int playersB = board.getPlayers().size();
		ArrayList<Card> answer = board.getAnswer();
		Player p = board.getPlayers().get(0);
		new Guess(false, answer, p, board);
		int playersA = board.getPlayers().size();
		assertTrue(playersB == playersA);
		players.clear();
	}

	@Test
	public void checkInvalidAccusation(){
		Board board = createBoard();
		Room r = null;
		Weapon w = null;
		Character c = null;
		int i = 0;
		ArrayList<Player> p = board.getPlayers();
		while(i < (p.size()-1)){
			if(p.get(0).getHand().get(i) instanceof Room){
				r = (Room) p.get(0).getHand().get(i);
			}
			else if(p.get(0).getHand().get(i) instanceof Weapon){
				w = (Weapon) p.get(0).getHand().get(i);
			}
			else if(p.get(0).getHand().get(i) instanceof Character){
				c = (Character) p.get(0).getHand().get(i);
			}
			i++;
		}
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(r);
		cards.add(w);
		cards.add(c);
		Player player = board.getPlayers().get(0);
		Guess g = new Guess(false, cards, player, board);
		assertFalse(g.hasWon());
		players.clear();
	}

	@Test
	public void checkInvalidAccusation2()//Check player is eliminated
	{
		Board b = createBoard();
		Room r = null;
		Weapon w = null;
		Character c = null;
		int i = 0;
		ArrayList<Player> p = b.getPlayers();
		while(i < (p.size()-1))
		{
			if(p.get(0).getHand().get(i) instanceof Room)
			{
				r = (Room) p.get(0).getHand().get(i);
			}
			else if(p.get(0).getHand().get(i) instanceof Weapon)
			{
				w = (Weapon) p.get(0).getHand().get(i);
			}
			else if(p.get(0).getHand().get(i) instanceof Character)
			{
				c = (Character) p.get(0).getHand().get(i);
			}
			i++;
		}

		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(r);
		cards.add(w);
		cards.add(c);
		Player player = b.getPlayers().get(0);
		Guess g = new Guess(false, cards, player, b);
		assertTrue(g.getEliminatedPlayer() == player);
		players.clear();
	}

	@Test
	public void testPlayerHasLocation(){
		Board b = createBoard();
		Player p = b.getPlayers().get(0);
		assertTrue(p.getLocation() != null);
		players.clear();
	}

	@Test
	public void testAddToHand(){
		Board b = createBoard();
		Player p = b.getPlayers().get(0);
		Weapon w = new Weapon("Wrench", new Point(5,5));
		int handSize = p.getHand().size();
		p.addToHand(w);

		assertTrue((p.getHand().size() == (handSize + 1)));
		players.clear();
	}

	@Test
	public void testAddDiscoveredCards(){
		Board b = createBoard();
		Player p = b.getPlayers().get(0);
		Weapon w = new Weapon("Wrench", new Point(15,15));
		int handSize = p.getDiscoveredCards().size();
		p.addToDiscoveredCards(w);
		
		assertTrue((p.getDiscoveredCards().size() == (handSize + 1)));
		players.clear();
	}

	@Test
	public void playerHasNumber(){
		Board b = createBoard();
		Player p = b.getPlayers().get(0);
		assert(p.getNum() != -1);
		players.clear();
	}

	@Test
	public void playerHasUniqueNumber(){
		Board b = createBoard();
		ArrayList<Player> players = b.getPlayers();
		int dup = 0;
		for(int i = 0; i < players.size(); i++){
			for(int j = i + 1; j < players.size(); j++)
			{
				if((players.get(j).getNum()) == (players.get(i).getNum()))
				{
					dup = 1;
				}
			}
		}
		assertTrue(dup == 0);
		this.players.clear();
	}



	@Test 
	public void ValidSuggestion(){
		Board b = createBoard();
		ArrayList<Card> ans = b.getAnswer();
		Player p = b.getPlayers().get(0);
		Player p2 = b.getPlayers().get(1);
		p2.addToHand(ans.get(0));
		Room ar = (Room) ans.get(2);
		p.getLocation().setLocation(ar.getLocation());
		p.setRoom(ar);;
		Guess guess = new Guess(true, ans, p, b);
		assertTrue(guess.getFailed() == false);
		players.clear();
	}

	@Test 
	public void InvalidSuggestion()
	{
		Board b = createBoard();
		ArrayList<Card> ans = b.getAnswer();
		Player p = b.getPlayers().get(0);
		p.getLocation().setLocation(new Point(12,12));
		p.setRoom(null);
		Guess g = new Guess(true, ans, p, b);

		assertTrue(g.getFailed());
		players.clear();
	} 
	
	@Test 
	public void checkPlayersMove(){
		Board board = createBoard();
		ArrayList<Card> ans = board.getAnswer();
		ans.remove(1);
		ans.add(new Character("Mr. Green"));
		Player p = board.getPlayers().get(0);
		Player suggestedPlayer = null;
		Character character = null;
		Room room = null;
		for(Card card : ans){
			if (card instanceof Character){
				character = (Character) card;
			}
			if (card instanceof Room){
				room = (Room) card;
				p.setRoom(room);
			}
		}
		for(Player player : board.getPlayers()){
			if(player.getCharacterName().equals(character.getName())){
				suggestedPlayer = player;
				break;
			}
		}
		Guess guess = new Guess(true, ans, p, board);
		guess.moveIcons(ans, board);
		assertTrue(room.getBoundingBox().contains(suggestedPlayer.getLocation()));
	}
	
	@Test 
	public void checkWeaponsMove(){
		Board board = createBoard();
		ArrayList<Card> ans = board.getAnswer();
		ans.remove(1);
		ans.add(new Character("Mr. Green"));
		Player p = board.getPlayers().get(0);
		Weapon weapon = null;
		Room room = null;
		for(Card card : ans){
			if(card instanceof Weapon){
				weapon = (Weapon) card;
			}
			if (card instanceof Room){
				room = (Room) card;
				p.setRoom(room);
			}
		}
		Guess guess = new Guess(true, ans, p, board);
		guess.moveIcons(ans, board);
		assertTrue(room.getBoundingBox().contains(weapon.getLocation()));
	}
	
	/**
	 * Trying to enter room through wall
	 */
	@Test
	public void checkInvalidMove(){
		Board board = createBoard();
		Player player = board.getPlayers().get(0);
		assertFalse(isValidMove(new Point(0,0), player, board));
	}
	
	/**
	 * Trying to enter square already occupied by another player
	 */
	@Test
	public void checkInvalidMove2(){
		Board board = createBoard();
		Player player = board.getPlayers().get(0);
		assertFalse(isValidMove(new Point(0,8), player, board));
	}
	
	/**
	 * If trying to enter centre zone
	 */
	@Test
	public void checkInvalidMove3(){
		Board board = createBoard();
		Player player = board.getPlayers().get(0);
		assertFalse(isValidMove(new Point(11,11), player, board));
	}
	
	/**
	 * If in room and trying to exit through wall
	 */
	@Test
	public void checkInvalidMove4(){
		Board board = createBoard();
		Player player = board.getPlayers().get(0);
		player.setLocation(new Point(0,0));
		assertFalse(isValidMove(new Point(0,7), player, board));
	}

	//--------Helper Methods----------------//
	
	/**
	 * Creates a new UI and board to test
	 * @return
	 */
		private Board createBoard(){
			players.add(new Player("Bob", "Colonel Mustard", Color.YELLOW, 1));
			players.add(new Player("Jeremy", "Mr. Green", Color.GREEN, 2));
			UI ui = new UI(players);
			return ui.b;
		}
		
		/**
		 * Checks if
		 * @param newLocation
		 * @return
		 */
		private boolean isValidMove(Point newLocation, Player player, Board board){
			//If trying to enter centre piece
			if(board.getCentre().contains(newLocation)){
				return false;
			}
			//If Trying to enter square occupied by another player
			for(Point point : board.getPlayerSquares()){
				if(newLocation.equals(point)){
					return false;
				}
			}
			//If in room and trying to exit
			for(Room room : board.getRooms()){
				Polygon boundingBox = room.getBoundingBox();
				if(boundingBox.contains(player.getLocation())){
					if(!boundingBox.contains(newLocation)){
						if(board.getDoors().contains(player.getLocation())){
							//Exiting room
							player.setRoom(null);
							return true;
						}
						else{
							return false;
						}
					}
					//In room and staying in there
					return true;
				}
			}
			//Not in room and trying to enter one
			for(Room room : board.getRooms()){
				Polygon boundingBox = room.getBoundingBox();
				if(boundingBox.contains(newLocation)){
					if(!board.getDoors().contains(newLocation)){
						return false;
					}
					else{
						//Entering a room
						player.setRoom(room);
						return true;
					}
				}
			}
			//Not in room and staying that way
			return true;
		}

	}
