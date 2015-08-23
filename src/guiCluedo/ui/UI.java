
package guiCluedo.ui;

import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import javax.swing.event.*;
import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Character;
import guiCluedo.game.Guess;
import guiCluedo.game.Player;
import guiCluedo.game.Room;
import guiCluedo.game.Weapon;

public class UI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private Player currentPlayer;
	private Board b;
	private BoardCanvas canvas;
	private HandCanvas hCanvas;
	private static final String MOVE_UP = "move up";
	private static final String MOVE_RIGHT = "move right";
	private static final String MOVE_DOWN = "move down";
	private static final String MOVE_LEFT = "move left";
	private boolean isGuess = true;

	static JLabel obj1 = new JLabel();

	/**
	 * Creates new form UI
	 */
	public UI() {
		initComponents();
		b = new Board();
		System.out.println("Board created");
		System.out.println("boardArea.getWidth() = " + boardArea.getWidth());
		canvas = new BoardCanvas(b, boardArea.getWidth(), boardArea.getHeight());
		hCanvas = new HandCanvas(b, handArea.getWidth(), handArea.getHeight());
		handArea.add(hCanvas);
		System.out.println("Canvas created");
		boardArea.add(canvas);
		System.out.println("canvas added");
		playGame(b, 0);
		System.out.println("Game played");

		keyBindings();

		this.addComponentListener(new ComponentAdapter() 
		{  
			public void componentResized(ComponentEvent evt) {
				Component c = (Component)evt.getSource();
				boardArea.setSize(boardArea.getWidth(), boardArea.getHeight());
				//handArea.setSize(handArea.getWidth(), handArea.getHeight());
				canvas.setWidth(boardArea.getWidth());
				//hCanvas.setWidth(handArea.getWidth());
				canvas.setHeight(boardArea.getHeight());
				//hCanvas.setWidth(boardArea.getWidth());
				canvas.repaint();
				//hCanvas.repaint();
			}
		});
	}

	private void keyBindings() {
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);

		obj1.getActionMap().put(MOVE_UP, new MoveAction("Up", currentPlayer, this.canvas, b));
		obj1.getActionMap().put(MOVE_RIGHT, new MoveAction("Right", currentPlayer, this.canvas, b));
		obj1.getActionMap().put(MOVE_DOWN, new MoveAction("Down", currentPlayer, this.canvas, b));
		obj1.getActionMap().put(MOVE_LEFT, new MoveAction("Left", currentPlayer, this.canvas, b));
		add(obj1);

	}

	private void accusButtonActionPerformed(ActionEvent e) {
		isGuess = false;
		guessRoom.setEnabled(true);
		guessDialoge.setVisible(true);
	}

	private void guessButtonActionPerformed(ActionEvent e) {
		isGuess = true;
		guessRoom.setEnabled(false);
		if(findContainingRoom(b) == null){
			errorDialog.setVisible(true);
			return;
		}
		guessDialoge.setVisible(true);
	}

	private void endTurnActionPerformed(ActionEvent e) {
		playGame(b, currentPlayer.getNum());
		rollDice.setEnabled(true);

	}

	private void guessOKButtonActionPerformed(ActionEvent e) {
		if(isGuess)
		{
			String room = findContainingRoom(b);
			String character = guessCharacter.getSelectedItem().toString();
			String weapon = guessWeapon.getSelectedItem().toString();

			//Make cards from these and add to arraylist
			ArrayList<String> cards = new ArrayList<String>();
			cards.add(room);
			cards.add(weapon);
			cards.add(character);
			ArrayList<Card> guessHand = createGuess(cards, b);
			//Pass Arraylist to the guess class
			Guess g = new Guess(true, guessHand, currentPlayer, b);
			guessDialoge.setVisible(false);
		}
		else
		{
			String room = guessRoom.getSelectedItem().toString();;
			String character = guessCharacter.getSelectedItem().toString();
			String weapon = guessWeapon.getSelectedItem().toString();

			//Make cards from these and add to arraylist
			ArrayList<String> cards = new ArrayList<String>();
			cards.add(room);
			cards.add(weapon);
			cards.add(character);
			ArrayList<Card> guessHand = createGuess(cards, b);
			//Pass Arraylist to the guess class
			Guess g = new Guess(true, guessHand, currentPlayer, b);
			guessDialoge.setVisible(false);
		}


	}

	public String findContainingRoom(Board b)
	{
		double x = currentPlayer.getLocation().getX();
		double y = currentPlayer.getLocation().getY();

		for(Room r: b.getRooms())
		{
			Polygon poly = r.getBoundingBox();
			if(poly.contains(x, y))
			{
				return r.getName();
			}
		}

		return null;
	}

	private static ArrayList<Card> createGuess(ArrayList<String> cards, Board b) {
		ArrayList<Card> guessHand = new ArrayList<Card>();
		for(int i = 0; i < 3; i++)
		{
			if(i == 0){
				String roomName = cards.get(i);
				int index = b.getRoomNames().indexOf(roomName);
				Room guessRoom = b.getRooms().get(index);
			}
			else if(i == 1){
				String weaponName = cards.get(i);
				System.out.println("weapon name = " + weaponName);
				int indexW = b.getWeaponNames().indexOf(weaponName);
				Weapon guessWeapon = b.getWeapons().get(indexW);
			}
			else if(i == 2)
			{
				String characterN = cards.get(i);
				int indexC = b.getCharacterNames().indexOf(characterN);
				Character guessCharacter = b.getCharacters().get(indexC);
			}
		}



		return guessHand;

	}

	private void errorOKActionPerformed(ActionEvent e) {
		errorDialog.setVisible(false);
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
	public void playGame(Board b, int playerNum) {
		
		playerNum = (playerNum % b.getPlayers().size()) + 1; //go to the next player number
		System.out.println("Current player is: " + playerNum + " " );
		currentPlayer = b.getPlayers().get(playerNum - 1);
		System.out.println(currentPlayer.getName());
		Player eliminatedPlayer = null;
		keyBindings();
		//		while (finished == false) {	//While the game has not been won (or lost)
		//			Room room = null;
		//			for(Room r : b.getRooms()){
		//				if(r.getBoundingBox().contains(currentPlayer.getLocation())){
		//					room = r; 
		//				}
		//			}
		//			if (room == null) {	//If the player is not within a room.
		//
		//				if (true) {	//If player chose to make an accusation				
		//					ArrayList<Card> guessHand = createGuess(scan, b);//Create the guess hand
		//					while (guessHand == null) {
		//						guessHand = createGuess(scan, b);
		//					}
		//					boolean opt = false;//Accusation
		//
		//					Guess guess = new Guess(opt, guessHand, currentPlayer, b);
		//
		//					if (guess.getEliminatedPlayer() != null) {
		//						eliminatedPlayer = guess.getEliminatedPlayer(); //Set the player to be eliminated 
		//						System.out.println("You guessed wrong");
		//						System.out.println("You have been eliminated!");
		//						
		//						if(b.getPlayers().size() == 2)//If there is no one left in the game then exit
		//						{
		//							System.out.println("");
		//							System.out.println("Game over! No one guessed correctly");
		//						}
		//						
		//						break;//Break out
		//					} else if (guess.hasWon()) {
		//						finished = true;
		//						System.out.println("Congratulations " + currentPlayer.getName() + " you have won!");
		//						return;
		//					}
		//
		//				}
		//
		//			} else {	//If the player made it to the room they wanted
		//
		//				if () {	//If they chose to make either an accusation of a suggestion
		//
		//					Guess guess = null;
		//					do{	//Select the 3 cards that make up the guess					
		//						ArrayList<Card> guessHand = createGuess(scan, b);
		//						while (guessHand == null) {
		//							guessHand = createGuess(scan, b);
		//						}
		//
		//						// create a guess hand
		//						boolean opt = false;
		//						if () {	//if accusation
		//							opt = true;
		//						}
		//
		//						guess = new Guess(opt, guessHand, currentPlayer, b);
		//					}while(guess.getFailed() == true);	//if not all 3 were selected
		//
		//					if (guess.getEliminatedPlayer() != null) {
		//						eliminatedPlayer = guess.getEliminatedPlayer();
		//						System.out.println("You guessed wrong");
		//						System.out.println("You have been eliminated!");
		//						
		//						if(b.getPlayers().size() == 2)//If there is no one left in the game exit
		//						{
		//							System.out.println("Game over! No one guessed correctly");
		//						}
		//						
		//						break;
		//					} else if (guess.hasWon()) {
		//						finished = true;
		//						System.out.println("Congratulations " + currentPlayer.getName() + " you have won!");
		//						return;
		//					}
		//
		//				}
		//			}
		//		}

		//		playerNum = (playerNum % b.players.size()) - 1;
		//		b.players.remove(eliminatedPlayer);
		//		while (b.getPlayers().size() > 1) {	//While there is more than 1 player left keep playing
		//			playGame(scan, b, playerNum);
		//		}
	}

	/**
	 * 
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	// Generated using JFormDesigner Evaluation license - James Barfoote
	private void initComponents() {
		jMenuBar = new JMenuBar();
		fileMenu = new JMenu();
		newGame = new JMenuItem();
		GameMenu = new JMenu();
		jSeparator1 = new JSeparator();
		rollDice = new JButton();
		endTurn = new JButton();
		guessButton = new JButton();
		accusButton = new JButton();
		boardArea = new JLayeredPane();
		handArea = new JLayeredPane();
		yourhandText = new JLabel();
		youRolledText = new JLabel();
		label6 = new JLabel();
		guessDialoge = new Dialog(this);
		guessOKButton = new JButton();
		guessWeapon = new JComboBox();
		guessCharacter = new JComboBox();
		guessRoom = new JComboBox();
		label2 = new JLabel();
		label1 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		errorDialog = new JDialog();
		label5 = new JLabel();
		errorOK = new JButton();
		errorText2 = new JLabel();

		guessWeapon.addItem("Knife");
		guessWeapon.addItem("Revolver");
		guessWeapon.addItem("Pipe");
		guessWeapon.addItem("Rope");
		guessWeapon.addItem("Candle Stick");
		guessWeapon.addItem("Wrench");

		guessCharacter.addItem("Colonel Mustard");
		guessCharacter.addItem("Miss Scarlet");
		guessCharacter.addItem("Mrs. White");
		guessCharacter.addItem("Mrs. Peacock");
		guessCharacter.addItem("Mr. Green");
		guessCharacter.addItem("Professor Plum");

		guessRoom.addItem("Ballroom");
		guessRoom.addItem("Billard Room");
		guessRoom.addItem("Conservatory");
		guessRoom.addItem("Dining Room");
		guessRoom.addItem("Hall");
		guessRoom.addItem("Kitchen");
		guessRoom.addItem("Library");
		guessRoom.addItem("Lounge");
		guessRoom.addItem("Study");

		
		//======== this ========
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Cluedo");
		Container contentPane = getContentPane();

		//======== jMenuBar ========
		{

			//======== fileMenu ========
			{
				fileMenu.setText("File");

				//---- newGame ----
				newGame.setText("New Game");
				fileMenu.add(newGame);
			}
			jMenuBar.add(fileMenu);

			//======== GameMenu ========
			{
				GameMenu.setText("Game");
				GameMenu.addMenuListener(new MenuListener() {
					@Override
					public void menuCanceled(MenuEvent e) {}
					@Override
					public void menuDeselected(MenuEvent e) {}
					@Override
					public void menuSelected(MenuEvent e) {
						GameMenuMenuSelected(e);
					}
				});
			}
			jMenuBar.add(GameMenu);
		}
		setJMenuBar(jMenuBar);

		//---- rollDice ----
		rollDice.setText("Roll Dice");
		rollDice.setName("rollDice");
		rollDice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDiceActionPerformed(e);
			}
		});

		//---- endTurn ----
		endTurn.setText("End Turn");
		endTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endTurnActionPerformed(e);
			}
		});

		//---- guessButton ----
		guessButton.setText("Suggestion");
		guessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guessButtonActionPerformed(e);
			}
		});

		//---- accusButton ----
		accusButton.setText("Accusation");
		accusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accusButtonActionPerformed(e);
			}
		});

		//======== handArea ========
		{
			handArea.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		}

		//---- yourhandText ----
		yourhandText.setText("Your Hand:");

		//---- youRolledText ----
		youRolledText.setText("You rolled a ");

		//---- label6 ----
		label6.setText("You have 0 moves left");

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(rollDice)
						.addComponent(youRolledText)
						.addComponent(label6))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(accusButton)
						.addComponent(guessButton)
						.addComponent(endTurn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
					.addGap(32, 32, 32)
					.addComponent(yourhandText)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(handArea, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(jSeparator1, GroupLayout.Alignment.TRAILING)
				.addComponent(boardArea, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addComponent(boardArea, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(handArea, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(16, 16, 16))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(rollDice)
								.addComponent(endTurn))
							.addGroup(contentPaneLayout.createParallelGroup()
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGap(18, 18, 18)
									.addComponent(youRolledText)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label6))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(guessButton)
										.addComponent(yourhandText))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(accusButton))))))
		);
		pack();
		setLocationRelativeTo(getOwner());

		//======== guessDialoge ========
		{
			guessDialoge.setTitle("Suggestion / Accusation");

			//---- guessOKButton ----
			guessOKButton.setText("OK");
			guessOKButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					guessOKButtonActionPerformed(e);
				}
			});

			//---- label2 ----
			label2.setText("Make a Guess by selecting one from each of the drop down boxes");

			//---- label1 ----
			label1.setText("Weapon");

			//---- label3 ----
			label3.setText("Character");

			//---- label4 ----
			label4.setText("Room");

			GroupLayout guessDialogeLayout = new GroupLayout(guessDialoge);
			guessDialoge.setLayout(guessDialogeLayout);
			guessDialogeLayout.setHorizontalGroup(
				guessDialogeLayout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, guessDialogeLayout.createSequentialGroup()
						.addContainerGap(70, Short.MAX_VALUE)
						.addGroup(guessDialogeLayout.createParallelGroup()
							.addGroup(GroupLayout.Alignment.TRAILING, guessDialogeLayout.createSequentialGroup()
								.addComponent(guessOKButton, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addGap(143, 143, 143))
							.addGroup(GroupLayout.Alignment.TRAILING, guessDialogeLayout.createSequentialGroup()
								.addComponent(label2)
								.addGap(43, 43, 43))))
					.addGroup(GroupLayout.Alignment.TRAILING, guessDialogeLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(guessWeapon, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
						.addGap(27, 27, 27)
						.addComponent(guessCharacter, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addComponent(guessRoom, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
						.addGap(21, 21, 21))
					.addGroup(guessDialogeLayout.createSequentialGroup()
						.addGap(45, 45, 45)
						.addComponent(label1)
						.addGap(96, 96, 96)
						.addComponent(label3)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
						.addComponent(label4)
						.addGap(70, 70, 70))
			);
			guessDialogeLayout.setVerticalGroup(
				guessDialogeLayout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, guessDialogeLayout.createSequentialGroup()
						.addGap(12, 12, 12)
						.addComponent(label2)
						.addGap(8, 8, 8)
						.addGroup(guessDialogeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label1)
							.addComponent(label3)
							.addComponent(label4))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(guessDialogeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(guessRoom, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
							.addComponent(guessWeapon, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
							.addComponent(guessCharacter, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addComponent(guessOKButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
			guessDialoge.pack();
			guessDialoge.setLocationRelativeTo(guessDialoge.getOwner());
		}

		//======== errorDialog ========
		{
			errorDialog.setTitle("ERROR!");
			Container errorDialogContentPane = errorDialog.getContentPane();

			//---- label5 ----
			label5.setText("You have tried to perform a suggestion ");
			label5.setFont(new Font("Tahoma", Font.PLAIN, 14));

			//---- errorOK ----
			errorOK.setText("OK");
			errorOK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					errorOKActionPerformed(e);
					errorOKActionPerformed(e);
				}
			});

			//---- errorText2 ----
			errorText2.setText("without being in a room");
			errorText2.setFont(new Font("Tahoma", Font.PLAIN, 14));

			GroupLayout errorDialogContentPaneLayout = new GroupLayout(errorDialogContentPane);
			errorDialogContentPane.setLayout(errorDialogContentPaneLayout);
			errorDialogContentPaneLayout.setHorizontalGroup(
				errorDialogContentPaneLayout.createParallelGroup()
					.addGroup(errorDialogContentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(errorDialogContentPaneLayout.createParallelGroup()
							.addComponent(label5)
							.addGroup(errorDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(errorOK)
								.addComponent(errorText2)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			errorDialogContentPaneLayout.setVerticalGroup(
				errorDialogContentPaneLayout.createParallelGroup()
					.addGroup(errorDialogContentPaneLayout.createSequentialGroup()
						.addGap(22, 22, 22)
						.addComponent(label5)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(errorText2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
						.addComponent(errorOK)
						.addContainerGap())
			);
			errorDialog.pack();
			errorDialog.setLocationRelativeTo(errorDialog.getOwner());
		}
	}// </editor-fold>//GEN-END:initComponents

	private void rollDiceActionPerformed(java.awt.event.ActionEvent evt) {
		int roll = ((int) Math.ceil(Math.random()*11)) + 1; // generate a random number between 2 and 12 inclusive
		currentPlayer.setRoll(roll);
		System.out.println(roll);
		youRolledText.setText("You rolled " + roll);
		rollDice.setEnabled(false);
	}

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jTextField2ActionPerformed

	private void GameMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_GameMenuMenuSelected
		// TODO add your handling code here:
	}//GEN-LAST:event_GameMenuMenuSelected


	//    private javax.swing.JMenu GameMenu;
	//    private javax.swing.JLabel diceRolled;
	//    private javax.swing.JMenu fileMenu;
	//    private static javax.swing.JFrame jFrame1;
	//    private javax.swing.JMenuBar jMenuBar;
	//    private javax.swing.JSeparator jSeparator1;
	//    private javax.swing.JLabel yourHandText;
	//    private javax.swing.JMenuItem newGame;
	//    private javax.swing.JButton rollDice; 
	// private javax.swing.JLayeredPane boardArea;
	//private javax.swing.JLayeredPane handArea;


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

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - James Barfoote
	private JMenuBar jMenuBar;
	private JMenu fileMenu;
	private JMenuItem newGame;
	private JMenu GameMenu;
	private JSeparator jSeparator1;
	private JButton rollDice;
	private JButton endTurn;
	private JButton guessButton;
	private JButton accusButton;
	private JLayeredPane boardArea;
	private JLayeredPane handArea;
	private JLabel yourhandText;
	private JLabel youRolledText;
	private JLabel label6;
	private Dialog guessDialoge;
	private JButton guessOKButton;
	private JComboBox guessWeapon;
	private JComboBox guessCharacter;
	private JComboBox guessRoom;
	private JLabel label2;
	private JLabel label1;
	private JLabel label3;
	private JLabel label4;
	private JDialog errorDialog;
	private JLabel label5;
	private JButton errorOK;
	private JLabel errorText2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}