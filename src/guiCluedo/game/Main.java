package guiCluedo.game;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
	private static boolean finished = false;

	/**
	 * Creates the game and asks how many players there are
	 * @param scan - the scanner used for accessing user input
	 */
	public static void createGame(Scanner scan) {
		System.out.println("Welcome to Cluedo!");

		// ask for how many players are playing
		System.out.println("How many players?  (2-6)");
		String numP = scan.next();
		int numPlayers = 0;
		
		numPlayers = isCorrectNumber(scan, 2, 6, numP);
		
		Board b = new Board();

		int playerNum = 0;

		playGame(scan, b, playerNum);
	}

	/**
	 * Loop through all the players while the game hasn't been won. If a player
	 * gets eliminated, break the loop then remove the player and start the loop
	 * again from where it left off.
	 * 
	 * @param scan - the scanner used for accessing user input
	 * @param b - The current board
	 * @param playerNum - the prior players number
	 */
	public static void playGame(Scanner scan, Board b, int playerNum) {

		playerNum = (playerNum % b.getPlayers().size()) + 1; //go to the next player number
		Player currentPlayer = b.getPlayers().get(playerNum - 1);
		Player eliminatedPlayer = null;
		while (finished == false) {	//While the game has not been won (or lost)
			System.out.println("--------------- Player " + playerNum + " ---------------------");
			System.out.println();
			System.out.println("You are " + currentPlayer.getName());
			System.out.println();
			Room r = null;
			currentPlayer.calculateDistances(b);
			Map<Room, Integer> rDist = currentPlayer.getRoomDist();
			int i = 1;
			ArrayList<Room> rooms = new ArrayList<Room>();
			System.out.println("");
			System.out.println("Type the number of the room you wish to move towards");
			for (Entry<Room, Integer> e : rDist.entrySet()) {	// look at room distances map and print out each entry
				Room cr = e.getKey();
				int dist = e.getValue();
				rooms.add(cr);

				System.out.printf("%2s. %22s - Distance = %-100s \n",  i, cr.getName(),dist);
				i++;
			}

			int diceNum = currentPlayer.rollDice();				// roll dice
			System.out.println("");
			System.out.println("You rolled " + diceNum);

			System.out.println("Where do you want to move to?");		// give option for movement
			String numC = scan.next();
			int numChoice = 0;
			
			numChoice = isCorrectNumber(scan, 1, 13, numC);

			// update location
			r = rooms.get(numChoice-1);
			currentPlayer.updateLocation(r);
			scan.useDelimiter(System.getProperty("line.separator"));
			// make a guess option (suggestion, accusation OR nothing)
			System.out.println("");
			System.out.println("What would you like to do?");

			int distToRoom = rDist.get(r);
			if (distToRoom > diceNum) {	//If the player did not roll high enough to make it to the room they wanted then don't give the option for a suggestion
				System.out.println("1 - Accusation");
				System.out.println("2 - Nothing");
				String stringOption = scan.next();
				int option = 0;
				
				option = isCorrectNumber(scan, 1, 2, stringOption);

				if (option == 1) {	//If player chose to make an accusation

					System.out.println("Please type the 3 cards that you are guessing on a new line");
					System.out.println("In this order: Room, Weapon, Character");
					// get cards from typed input on new line
					
					ArrayList<Card> guessHand = createGuess(scan, b);//Create the guess hand
					while (guessHand == null) {
						guessHand = createGuess(scan, b);
					}
					boolean opt = false;//Accusation

					Guess guess = new Guess(opt, guessHand, currentPlayer, b);

					if (guess.getEliminatedPlayer() != null) {
						eliminatedPlayer = guess.getEliminatedPlayer(); //Set the player to be eliminated 
						System.out.println("You guessed wrong");
						System.out.println("You have been eliminated!");
						
						if(b.getPlayers().size() == 2)//If there is no one left in the game then exit
						{
							System.out.println("");
							System.out.println("Game over! No one guessed correctly");
						}
						
						break;//Break out
					} else if (guess.hasWon()) {
						finished = true;
						System.out.println("Congratulations " + currentPlayer.getName() + " you have won!");
						return;
					}

				}

			} else {	//If the player made it to the room they wanted

				System.out.println("1 - Suggestion");
				System.out.println("2 - Accusation");
				System.out.println("3 - Nothing");
				String stringOption = scan.next();
				int option = 0;
				
				option = isCorrectNumber(scan, 1,  3, stringOption);
				if (option == 1 || option == 2) {	//If they chose to make either an accusation of a suggestion

					Guess guess = null;
					do{	//Ask them to type the cards they are suggesting or accusing while the input is invalid
						System.out.println("Please type the 3 cards that you are guessing on a new line");
						System.out.println("In this order: Room, Weapon, Character");
						
						ArrayList<Card> guessHand = createGuess(scan, b);
						while (guessHand == null) {
							guessHand = createGuess(scan, b);
						}

						// create a guess hand
						boolean opt = false;
						if (option == 1) {
							opt = true;
						}

						guess = new Guess(opt, guessHand, currentPlayer, b);
					}while(guess.getFailed() == true);

					if (guess.getEliminatedPlayer() != null) {
						eliminatedPlayer = guess.getEliminatedPlayer();
						System.out.println("You guessed wrong");
						System.out.println("You have been eliminated!");
						
						if(b.getPlayers().size() == 2)//If there is no one left in the game exit
						{
							System.out.println("Game over! No one guessed correctly");
						}
						
						break;
					} else if (guess.hasWon()) {
						finished = true;
						System.out.println("Congratulations " + currentPlayer.getName() + " you have won!");
						return;
					}

				}
			}
			playerNum = (playerNum % b.getPlayers().size()) + 1;	//go to the next player
			currentPlayer = b.getPlayers().get(playerNum - 1);
		}

		playerNum = (playerNum % b.players.size()) - 1;
		b.players.remove(eliminatedPlayer);
		while (b.getPlayers().size() > 1) {	//While there is more than 1 player left keep playing
			playGame(scan, b, playerNum);
		}
	}

	/**
	 * Creates a guess for the specified board. A guess consists of 3 cards used for
	 * either a suggestion or an accusation
	 * @param scan - The scanner used for accessing user input
	 * @param b - The current board
	 * @return The list of 3 cards to be used in the suggestion/accusation.
	 */
	private static ArrayList<Card> createGuess(Scanner scan, Board b) {
		String roomName = scan.next();
		int index = b.getRoomNames().indexOf(roomName);
		Room guessRoom = null;
		if (index != -1) {
			guessRoom = b.getRooms().get(index);
		} else {
			System.out.println("Room name was incorrect, please type the 3 cards again");
			return null;
		}

		int indexW = b.getWeaponNames().indexOf(scan.next());
		Weapon guessWeapon = null;
		if (indexW != -1) {
			guessWeapon = b.getWeapons().get(indexW);
		} else {
			System.out.println("Weapon name was incorrect, please type the 3 cards again");
			return null;
		}

		String characterN = scan.next();
		int indexC = b.getCharacterNames().indexOf(characterN);
		Character guessCharacter = null;
		if (indexC != -1) {
			guessCharacter = b.getCharacters().get(indexC);
		} else {
			System.out.println("Character name was incorrect, please type the 3 cards again");
			return null;
		}

		ArrayList<Card> guessHand = new ArrayList<Card>();
		guessHand.add(guessRoom);
		guessHand.add(guessWeapon);
		guessHand.add(guessCharacter);

		return guessHand;

	}
	
	/**
	 * Checks if the given string can be parsed to an integer.
	 * @param s - The string to be parsed
	 * @return boolean of whether it is or not
	 */
	private static boolean isInteger(String s) {
		  try { 
		      Integer.parseInt(s); 
		   } catch(NumberFormatException e) { 
		      return false; 
		   }
		   // only got here if we didn't return false
		   return true;
		}
	/**
	 * Returns a number the user specifies given it's valid.
	 * 
	 * @param scan - The scanner used for accessing user input
	 * @param minNum - The minimum possible value
	 * @param maxNum - The maximum possible number
	 * @param num - The number to be checked.
	 * @return the integer the player gave
	 */
	private static int isCorrectNumber(Scanner scan, int minNum, int maxNum, String num){
		int numPlayers = 0;
		while(true){
			if(isInteger(num)){
				numPlayers = Integer.parseInt(num); 
				if((numPlayers >= minNum) && (numPlayers<=maxNum)){
					break;
				}
			}
			System.out.println("Input must be between " +  minNum + " and " + maxNum);
			num = scan.next();
		}
		return numPlayers;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		createGame(scan);
	}

}