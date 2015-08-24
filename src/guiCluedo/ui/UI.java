
package guiCluedo.ui;

import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Character;
import guiCluedo.game.Guess;
import guiCluedo.game.Player;
import guiCluedo.game.Room;
import guiCluedo.game.Weapon;

public class UI extends javax.swing.JFrame implements KeyListener{

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
	private static final String N = "next turn";
	private boolean isGuess = true;
	private Card cardClicked;

	static JLabel obj1 = new JLabel();
	static JLabel shortCuts = new JLabel();
	

	/**
	 * Creates new form UI
	 */
	public UI() {	
		initComponents();
		b = new Board();
		
		playGame(b, 0);
		this.addKeyListener(this);
		System.out.println("Board created");
		System.out.println("boardArea.getWidth() = " + boardArea.getWidth());
		canvas = new BoardCanvas(b, boardArea.getWidth(), boardArea.getHeight());
		hCanvas = new HandCanvas(b, handArea.getWidth(), handArea.getHeight(), currentPlayer);
		handArea.add(hCanvas);
		System.out.println("Canvas created");
		boardArea.add(canvas);
		System.out.println("canvas added");

		System.out.println("Game played");

		keyBindings();
//		handArea.addMouseListener(this);
//		addMouseListener(this);

		this.addComponentListener(new ComponentAdapter() 
		{  
			public void componentResized(ComponentEvent evt) {
				Component c = (Component)evt.getSource();
				boardArea.setSize(boardArea.getWidth(), boardArea.getHeight());
				handArea.setSize(handArea.getWidth(), handArea.getHeight());
				canvas.setWidth(boardArea.getWidth());
				hCanvas.setWidth(handArea.getWidth());
				canvas.setHeight(boardArea.getHeight());
				hCanvas.setHeight(handArea.getHeight());
				canvas.repaint();
				hCanvas.repaint();
			}
		});
	}

	private void keyBindings() {
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
		
		shortCuts.getInputMap(IFW).put(KeyStroke.getKeyStroke("NEXT"), N);
		shortCuts.getActionMap().put(N, new shortcutKeys("Next", this, this.canvas, b));

		obj1.getActionMap().put(MOVE_UP, new MoveAction("Up", currentPlayer, this.canvas, b));
		obj1.getActionMap().put(MOVE_RIGHT, new MoveAction("Right", currentPlayer, this.canvas, b));
		obj1.getActionMap().put(MOVE_DOWN, new MoveAction("Down", currentPlayer, this.canvas, b));
		obj1.getActionMap().put(MOVE_LEFT, new MoveAction("Left", currentPlayer, this.canvas, b));
		add(obj1);
	}

//	void keyPressed(KeyEvent e)
//	{
//		movesLeftLabel.setText("You have " + currentPlayer.getRoll() + " moves left");
//	}

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
		//HandCanvas h = new HandCanvas(b, handArea.getWidth(), handArea.getHeight(), currentPlayer);
		rollDice.setEnabled(true);
		hCanvas.setHand(currentPlayer);
		this.hCanvas.repaint();

	}

	private void guessOKButtonActionPerformed(ActionEvent e) {
		if(isGuess)
		{
			//Suggestion logic
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
			g.moveIcons(guessHand, b);
			canvas.repaint();
			guessDialoge.setVisible(false);
			
			//Call method that iterates over all the players and finds the first
			//one with a matching card from the guess hand
			//Then displays the popup asking for them to click on a card in their hand that matches
			guessDiagPlayerNameText.setText(currentPlayer.getName());
			line1Text.setText("Player blah guessed these three cards");
			line2Text.setText("name of the 3 cards");
			line3Text.setText("Please select one of these cards from your hand and click ok");
			guessDialog.setVisible(true);
			guessDialog.setAlwaysOnTop(true);
		}
		else
		{
			//Accusation logic
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
			Guess g = new Guess(false, guessHand, currentPlayer, b);
			guessDialoge.setVisible(false);
			if(g.getEliminatedPlayer()!=null){
				b.players.remove(g.getEliminatedPlayer());
				canvas.repaint();
				playGame(b, currentPlayer.getNum());
				rollDice.setEnabled(true);
				hCanvas.setHand(currentPlayer);
				this.hCanvas.repaint();

				//Show message that you have been eliminated
				errorDialog.setTitle("YOU HAVE BEEN ELIMINATED!!");
				errorText1.setText(currentPlayer.getName() + " you have been eliminated!");
				errorText2.setText("");
				errorDialog.setVisible(true);
				
				
				
				if(b.getPlayers().size() == 1){
					
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					//Show message that you have been eliminated
					errorDialog.setTitle("YOU HAVE WON!!");
					errorText1.setText(currentPlayer.getName() + " you have won the game!");
					errorText2.setText("Congratulations!");
					errorDialog.setVisible(true);
					}
			}
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
				System.out.println("room name = " + roomName);
				int index = b.getRoomNames().indexOf(roomName);
				Room guessRoom = b.getRooms().get(index);
				guessHand.add(guessRoom);
			}
			else if(i == 1){
				String weaponName = cards.get(i);
				System.out.println("weapon name = " + weaponName);
				int indexW = b.getWeaponNames().indexOf(weaponName);
				Weapon guessWeapon = b.getWeapons().get(indexW);
				guessHand.add(guessWeapon);
			}
			else if(i == 2)
			{
				String characterN = cards.get(i);
				System.out.println("character name = " + characterN);
				int indexC = b.getCharacterNames().indexOf(characterN);
				Character guessCharacter = b.getCharacters().get(indexC);
				guessHand.add(guessCharacter);
			}
		}
		return guessHand;

	}

	private void errorOKActionPerformed(ActionEvent e) {
		errorDialog.setVisible(false);
	}

	private void newGameActionPerformed(ActionEvent e) {
		b.players.clear();
		startScreen sc = new startScreen();
		sc.startScreenForm.setVisible(true);
		this.setVisible(false);
	}

	private void handAreaMouseClicked(MouseEvent e) {
		// TODO add your code here
	}

	private void showShortcutsActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void discoveredCardsmenuItemActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void checkBoxMenuItem1ItemStateChanged(ItemEvent e) {
		// TODO add your code here
	}

	private void cheatAnswerItemStateChanged(ItemEvent e) {
		// TODO add your code here
	}

	private void guessDiagOkButtonActionPerformed(ActionEvent e) {
		
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
		playerTurnText.setText(currentPlayer.getName() + " / " + currentPlayer.getCharacterName());

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
		showShortcuts = new JMenuItem();
		GameMenu = new JMenu();
		discoveredCardsmenuItem = new JMenuItem();
		menu1 = new JMenu();
		checkBoxMenuItem1 = new JCheckBoxMenuItem();
		cheatAnswer = new JCheckBoxMenuItem();
		rollDice = new JButton();
		endTurn = new JButton();
		guessButton = new JButton();
		accusButton = new JButton();
		boardArea = new JLayeredPane();
		handArea = new JLayeredPane();
		yourhandText = new JLabel();
		youRolledText = new JLabel();
		movesLeftLabel = new JLabel();
		playerTurnText = new JLabel();
		separator1 = new JSeparator();
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
		errorText1 = new JLabel();
		errorOK = new JButton();
		errorText2 = new JLabel();
		guessDialog = new JDialog();
		line1Text = new JLabel();
		line2Text = new JLabel();
		line3Text = new JLabel();
		guessDiagOkButton = new JButton();
		guessDiagPlayerNameText = new JLabel();
		
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
				newGame.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						newGameActionPerformed(e);
					}
				});
				fileMenu.add(newGame);

				//---- showShortcuts ----
				showShortcuts.setText("Show shortcuts");
				showShortcuts.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						showShortcutsActionPerformed(e);
					}
				});
				fileMenu.add(showShortcuts);
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

				//---- discoveredCardsmenuItem ----
				discoveredCardsmenuItem.setText("Show discovered cards");
				discoveredCardsmenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						discoveredCardsmenuItemActionPerformed(e);
					}
				});
				GameMenu.add(discoveredCardsmenuItem);
			}
			jMenuBar.add(GameMenu);

			//======== menu1 ========
			{
				menu1.setText("Cheats");

				//---- checkBoxMenuItem1 ----
				checkBoxMenuItem1.setText("Infinite move");
				checkBoxMenuItem1.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						checkBoxMenuItem1ItemStateChanged(e);
					}
				});
				menu1.add(checkBoxMenuItem1);

				//---- cheatAnswer ----
				cheatAnswer.setText("Show answer");
				cheatAnswer.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						cheatAnswerItemStateChanged(e);
					}
				});
				menu1.add(cheatAnswer);
			}
			jMenuBar.add(menu1);
		}
		setJMenuBar(jMenuBar);

		//---- rollDice ----
		rollDice.setText("Roll Dice");
		rollDice.setName("rollDice");
		rollDice.setToolTipText("Roll the dice so that you can move");
		rollDice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDiceActionPerformed(e);
			}
		});

		//---- endTurn ----
		endTurn.setText("End Turn");
		endTurn.setToolTipText("End your turn so that the next player can go");
		endTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endTurnActionPerformed(e);
			}
		});

		//---- guessButton ----
		guessButton.setText("Suggestion");
		guessButton.setToolTipText("Make a guess as to what cards you think are the answer");
		guessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guessButtonActionPerformed(e);
			}
		});

		//---- accusButton ----
		accusButton.setText("Accusation");
		accusButton.setToolTipText("If you are sure what the answer if click me the make your accusation");
		accusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accusButtonActionPerformed(e);
			}
		});

		//======== boardArea ========
		{
			boardArea.setDoubleBuffered(true);
		}

		//======== handArea ========
		{
			handArea.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			handArea.setDoubleBuffered(true);
			handArea.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					handAreaMouseClicked(e);
				}
			});
		}

		//---- yourhandText ----
		yourhandText.setText("Your Hand:");

		//---- youRolledText ----
		youRolledText.setText("You rolled a ");

		//---- movesLeftLabel ----
		movesLeftLabel.setText("You have 0 moves left");

		//---- playerTurnText ----
		playerTurnText.setText("Hello it is your turn");
		playerTurnText.setFont(new Font("Tahoma", Font.BOLD, 12));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addComponent(boardArea)
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(youRolledText)
								.addComponent(movesLeftLabel)
								.addComponent(rollDice, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addComponent(playerTurnText, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(endTurn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addComponent(accusButton)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(guessButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(yourhandText)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(handArea))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE)))
					.addGap(10, 10, 10))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addComponent(boardArea, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGap(10, 10, 10)
							.addComponent(endTurn)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(guessButton)
								.addComponent(yourhandText))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(accusButton)
							.addContainerGap())
						.addGroup(contentPaneLayout.createParallelGroup()
							.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(playerTurnText, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(youRolledText)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(movesLeftLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(rollDice, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addGap(6, 6, 6))
							.addGroup(contentPaneLayout.createSequentialGroup()
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(handArea, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))))
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

			//---- errorText1 ----
			errorText1.setText("You have tried to perform a suggestion ");
			errorText1.setFont(new Font("Tahoma", Font.PLAIN, 14));

			//---- errorOK ----
			errorOK.setText("OK");
			errorOK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					errorOKActionPerformed(e);
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
							.addComponent(errorText1)
							.addGroup(errorDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(errorOK)
								.addComponent(errorText2)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			errorDialogContentPaneLayout.setVerticalGroup(
				errorDialogContentPaneLayout.createParallelGroup()
					.addGroup(errorDialogContentPaneLayout.createSequentialGroup()
						.addGap(22, 22, 22)
						.addComponent(errorText1)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(errorText2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
						.addComponent(errorOK)
						.addContainerGap())
			);
			errorDialog.pack();
			errorDialog.setLocationRelativeTo(errorDialog.getOwner());
		}

		//======== guessDialog ========
		{
			guessDialog.setTitle("Suggestion");
			Container guessDialogContentPane = guessDialog.getContentPane();

			//---- line1Text ----
			line1Text.setText("Player blah guessed these cards:");

			//---- line2Text ----
			line2Text.setText("Insert 3 cards here");

			//---- line3Text ----
			line3Text.setText("Please click ok and then select one of your cards");

			//---- guessDiagOkButton ----
			guessDiagOkButton.setText("OK");
			guessDiagOkButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					guessDiagOkButtonActionPerformed(e);
				}
			});

			//---- guessDiagPlayerNameText ----
			guessDiagPlayerNameText.setText("Player 1");

			GroupLayout guessDialogContentPaneLayout = new GroupLayout(guessDialogContentPane);
			guessDialogContentPane.setLayout(guessDialogContentPaneLayout);
			guessDialogContentPaneLayout.setHorizontalGroup(
				guessDialogContentPaneLayout.createParallelGroup()
					.addGroup(guessDialogContentPaneLayout.createSequentialGroup()
						.addGap(117, 117, 117)
						.addComponent(guessDiagOkButton)
						.addContainerGap(120, Short.MAX_VALUE))
					.addGroup(GroupLayout.Alignment.TRAILING, guessDialogContentPaneLayout.createSequentialGroup()
						.addGap(0, 33, Short.MAX_VALUE)
						.addGroup(guessDialogContentPaneLayout.createParallelGroup()
							.addGroup(GroupLayout.Alignment.TRAILING, guessDialogContentPaneLayout.createSequentialGroup()
								.addComponent(line3Text)
								.addGap(19, 19, 19))
							.addGroup(GroupLayout.Alignment.TRAILING, guessDialogContentPaneLayout.createSequentialGroup()
								.addComponent(line1Text)
								.addGap(52, 52, 52))
							.addGroup(GroupLayout.Alignment.TRAILING, guessDialogContentPaneLayout.createSequentialGroup()
								.addComponent(line2Text)
								.addGap(88, 88, 88))))
					.addGroup(GroupLayout.Alignment.TRAILING, guessDialogContentPaneLayout.createSequentialGroup()
						.addContainerGap(130, Short.MAX_VALUE)
						.addComponent(guessDiagPlayerNameText)
						.addGap(115, 115, 115))
			);
			guessDialogContentPaneLayout.setVerticalGroup(
				guessDialogContentPaneLayout.createParallelGroup()
					.addGroup(guessDialogContentPaneLayout.createSequentialGroup()
						.addGap(5, 5, 5)
						.addComponent(guessDiagPlayerNameText)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(line1Text)
						.addGap(13, 13, 13)
						.addComponent(line2Text)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(line3Text)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
						.addComponent(guessDiagOkButton)
						.addContainerGap())
			);
			guessDialog.pack();
			guessDialog.setLocationRelativeTo(guessDialog.getOwner());
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
	private JMenuItem showShortcuts;
	private JMenu GameMenu;
	private JMenuItem discoveredCardsmenuItem;
	private JMenu menu1;
	private JCheckBoxMenuItem checkBoxMenuItem1;
	private JCheckBoxMenuItem cheatAnswer;
	private JButton rollDice;
	private JButton endTurn;
	private JButton guessButton;
	private JButton accusButton;
	private JLayeredPane boardArea;
	private JLayeredPane handArea;
	private JLabel yourhandText;
	private JLabel youRolledText;
	private JLabel movesLeftLabel;
	private JLabel playerTurnText;
	private JSeparator separator1;
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
	private JLabel errorText1;
	private JButton errorOK;
	private JLabel errorText2;
	private JDialog guessDialog;
	private JLabel line1Text;
	private JLabel line2Text;
	private JLabel line3Text;
	private JButton guessDiagOkButton;
	private JLabel guessDiagPlayerNameText;
	// JFormDesigner - End of variables declaration  //GEN-END:variables


	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Pressed");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Released");
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Typed");
		if(e.equals(KeyEvent.VK_P))
		{
			this.setFocusable(true);
			endTurnActionPerformed(null);
		}
		
	}

	
}