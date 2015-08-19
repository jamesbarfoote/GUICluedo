package guiCluedo.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Character;
import guiCluedo.game.Guess;
import guiCluedo.game.Player;
import guiCluedo.game.Room;
import guiCluedo.game.Weapon;

public class tests {

	@Test
	public void correctNumPlayers()
	{
		Board b = new Board();
		assertEquals(b.getPlayers().size(), 6);		
	}

	@Test
	public void allRoomsCreated()
	{
		Board b = new Board();
		assertEquals(b.getRooms().size(), 9);	
	}

	@Test
	public void allWeaponsCreated()
	{
		Board b = new Board();
		assertEquals(b.getWeapons().size(), 6);
	}

	@Test
	public void allCharactersCreated()
	{
		Board b = new Board();
		assertEquals(b.getCharacters().size(), 6);
	}

	@Test
	public void checkCardEquals()
	{
		Weapon w = new Weapon("Dagger");
		assertTrue(w.equals(w));
	}

	@Test
	public void checkCarNotEquals()
	{
		Weapon w = new Weapon("Dagger");
		Character c = new Character("Miss Scarlett");
		assertFalse(w.equals(c));
	}


	@Test
	public void checkName()
	{
		Weapon w = new Weapon("Dagger");
		String wName = w.getName();
		assertTrue(wName.equals("Dagger"));
	}

	@Test
	public void checkValidAccusation()
	{
		Board b = new Board();
		ArrayList<Card> ans = b.answer;
		Player p = b.getPlayers().get(0);
		Guess g = new Guess(false, ans, p, b);

		assertTrue(g.hasWon());
	}

	@Test
	public void checkValidAccusation2() //Check that the player isn't eliminated after getting it right
	{
		Board b = new Board();
		int playersB = b.getPlayers().size();
		ArrayList<Card> ans = b.answer;
		Player p = b.getPlayers().get(0);
		Guess g = new Guess(false, ans, p, b);
		int playersA = b.getPlayers().size();

		assertTrue(playersB == playersA);
	}

	@Test
	public void checkInvalidAccusation()
	{
		Board b = new Board();
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

		assertFalse(g.hasWon());
	}

	@Test
	public void checkInvalidAccusation2()//Check player is eliminated
	{
		Board b = new Board();
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
		int playersB = b.getPlayers().size();
		Guess g = new Guess(false, cards, player, b);
		int playersA = b.getPlayers().size();

		assertTrue(g.getEliminatedPlayer() == player);
	}

	@Test
	public void testPlayerMoves()
	{
		Board b = new Board();
		Player p = b.getPlayers().get(0);
		int loc = p.getLocation().getX() + p.getLocation().getY();
		ArrayList<Card> ans = b.answer;
		Room ar = (Room) b.getRooms().get(0);
		p.rollDice();
		p.calculateDistances(b);
		p.updateLocation(ar);
		int locAfter = p.getLocation().getX() + p.getLocation().getY();

		assertFalse(loc == (locAfter));
	}

	@Test
	public void testPlayerHasLocation()
	{
		Board b = new Board();
		Player p = b.getPlayers().get(0);
		assertTrue(p.getLocation() != null);
	}
	
	@Test
	public void testAddToHand()
	{
		Board b = new Board();
		Player p = b.getPlayers().get(0);
		Weapon w = new Weapon("Spanner");
		int handSize = p.getHand().size();
		p.addToHand(w);
		
		assertTrue((p.getHand().size() == (handSize + 1)));
	}
	
	@Test
	public void testAddDiscoveredHand()
	{
		Board b = new Board();
		Player p = b.getPlayers().get(0);
		Weapon w = new Weapon("Spanner");
		int handSize = p.getDiscoveredCards().size();
		p.addToHand2(w);
		
		assertTrue((p.getDiscoveredCards().size() == (handSize + 1)));
	}
	
	@Test
	public void playerHasNumber()
	{
		Board b = new Board();
		Player p = b.getPlayers().get(0);
		assert(p.getNum() != -1);
	}
	
	@Test
	public void playerHasUniqueNumber()
	{
		Board b = new Board();
		Player p = b.getPlayers().get(0);
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
	}
	
	

	@Test 
	public void ValidSuggestion()
	{
		Board b = new Board();
		ArrayList<Card> ans = b.answer;
		Player p = b.getPlayers().get(0);
		Player p2 = b.getPlayers().get(1);
		p2.addToHand(ans.get(0));
		Room ar = (Room) ans.get(2);
		p.getLocation().setX(ar.getLocation().getX());
		p.getLocation().setY(ar.getLocation().getY());
		//	
		int sizeB = p.getDiscoveredCards().size();
		System.out.println(sizeB);
		Guess g = new Guess(true, ans, p, b);
		int sizeA = p.getDiscoveredCards().size();
		System.out.println(sizeB);
		assertTrue(sizeA > sizeB);
	}

	@Test 
	public void InvalidSuggestion()
	{
		Board b = new Board();
		ArrayList<Card> ans = b.answer;
		Player p = b.getPlayers().get(0);
		Room ar = (Room) ans.get(2);
		p.getLocation().setX(12);
		p.getLocation().setY(12);

		int sizeB = p.getDiscoveredCards().size();
		Guess g = new Guess(true, ans, p, b);

		assertTrue(g.getFailed());
	}


}
