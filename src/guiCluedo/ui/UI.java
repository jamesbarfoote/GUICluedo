
package guiCluedo.ui;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Character;
import guiCluedo.game.Player;
import guiCluedo.game.Room;
import guiCluedo.game.Weapon;

public class UI extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private Player currentPlayer;
	private Board b;
	private BoardCanvas canvas;
	private static final String MOVE_UP = "move up";
	private static final String MOVE_RIGHT = "move right";
	private static final String MOVE_DOWN = "move down";
	private static final String MOVE_LEFT = "move left";
	
	static JLabel obj1 = new JLabel();

    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
        b = new Board();
        this.canvas = new BoardCanvas(b, boardArea.getWidth(), boardArea.getHeight());
        boardArea.add(canvas);
        playGame(b, 0);
        
        obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
        obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
        obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        
        obj1.getActionMap().put(MOVE_UP, new MoveAction("Up", currentPlayer, this.canvas));
        obj1.getActionMap().put(MOVE_RIGHT, new MoveAction("Right", currentPlayer, this.canvas));
        obj1.getActionMap().put(MOVE_DOWN, new MoveAction("Down", currentPlayer, this.canvas));
        obj1.getActionMap().put(MOVE_LEFT, new MoveAction("Left", currentPlayer, this.canvas));
        add(obj1);
        
        this.addComponentListener(new ComponentAdapter() 
        {  
                public void componentResized(ComponentEvent evt) {
                    Component c = (Component)evt.getSource();
                    System.out.println("Redrawn");
                    int height = (int) (boardArea.getWidth()*0.44);
                    boardArea.setSize(boardArea.getWidth(), height);
                    System.out.println("Width = " + boardArea.getWidth());
                    System.out.println("Height = " + boardArea.getHeight());
                    canvas.setWidth(boardArea.getWidth());
                    canvas.setHeight(boardArea.getHeight());
                    canvas.repaint();
                }
        });
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
		currentPlayer = b.getPlayers().get(playerNum - 1);
		Player eliminatedPlayer = null;
		playerNum = (playerNum % b.getPlayers().size()) + 1;	//go to the next player
		currentPlayer = b.getPlayers().get(playerNum - 1);
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
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jSeparator1 = new javax.swing.JSeparator();
        rollDice = new javax.swing.JButton();
        diceRolled = new javax.swing.JLabel();
        yourHandText = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        GameMenu = new javax.swing.JMenu();
        boardArea = new javax.swing.JLayeredPane();
        handArea = new javax.swing.JLayeredPane();

        jFrame1.setSize(400,400);
        jFrame1.setAlwaysOnTop(true);

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rollDice.setText("Roll Dice");
        rollDice.setName("rollDice"); // NOI18N
        rollDice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollDiceActionPerformed(evt);
            }
        });
        
        handArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout handAreaLayout = new javax.swing.GroupLayout(handArea);
        handArea.setLayout(handAreaLayout);
        handAreaLayout.setHorizontalGroup(
            handAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );
        handAreaLayout.setVerticalGroup(
            handAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout boardAreaLayout = new javax.swing.GroupLayout(boardArea);
        boardArea.setLayout(boardAreaLayout);
        boardAreaLayout.setHorizontalGroup(
            boardAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        boardAreaLayout.setVerticalGroup(
            boardAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        

        diceRolled.setText("You rolled: ");
        yourHandText.setText("Your hand:");

        fileMenu.setText("File");

        newGame.setText("New Game");
        fileMenu.add(newGame);

        jMenuBar.add(fileMenu);

        GameMenu.setText("Game");
        GameMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                GameMenuMenuSelected(evt);
            }
        });
        jMenuBar.add(GameMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rollDice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(diceRolled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(73, 73, 73)
                        .addComponent(yourHandText)))
                .addGap(35, 35, 35)
                .addComponent(handArea)
                .addContainerGap())
            .addComponent(boardArea)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(boardArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rollDice)
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(diceRolled)
                            .addComponent(yourHandText))
                        .addGap(18, 18, 18))
                    .addComponent(handArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        rollDice.getAccessibleContext().setAccessibleName("rollDice");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rollDiceActionPerformed(java.awt.event.ActionEvent evt) {
    	int roll = ((int) Math.ceil(Math.random()*11)) + 1; // generate a random number between 2 and 12 inclusive
		currentPlayer.setRoll(roll);
		System.out.println(roll);
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void GameMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_GameMenuMenuSelected
        // TODO add your handling code here:
    }//GEN-LAST:event_GameMenuMenuSelected
    

    private javax.swing.JMenu GameMenu;
    private javax.swing.JLabel diceRolled;
    private javax.swing.JMenu fileMenu;
    private static javax.swing.JFrame jFrame1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel yourHandText;
    private javax.swing.JMenuItem newGame;
    private javax.swing.JButton rollDice; 
    private javax.swing.JLayeredPane boardArea;
    private javax.swing.JLayeredPane handArea;


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
}