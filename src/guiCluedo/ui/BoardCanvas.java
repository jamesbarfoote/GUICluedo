package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import guiCluedo.game.Board;
import guiCluedo.game.Player;
import guiCluedo.game.Room;

public class BoardCanvas extends Canvas{

	private static final long serialVersionUID = 1L;
	Board gameBoard;
	private String[][] board;
	private ArrayList<Player> players;
	int width, height;

	public BoardCanvas(Board b, int width, int height){
		this.gameBoard = b;
		board = b.getBoard();
		this.players = b.getPlayers();
		this.width = width;
		this.height = height;
		setBackground (Color.YELLOW);
		setSize(width, height);
		readFile();
	}

	public void paint(Graphics g) {
		Graphics2D g2;
		g2 = (Graphics2D) g;
		setSize(width, height);
		drawBoard(g2);
		drawIcons(g2);
	}

	@SuppressWarnings("resource")
	private void readFile(){
		File boardFile = new File("CluedoBoard.txt");
		try {
			Scanner scan = new Scanner(boardFile);
			int lineNum = 0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				Scanner lineSc = new Scanner(line);
				int rowNum = 0;

				while(lineSc.hasNext()){

					String value = lineSc.next();
					if(value.equals("R")){
						board[lineNum][rowNum] = value;
					}
					else if(value.equals("W")){
						board[lineNum][rowNum] = value;
					}
					else if(value.equals("C")){
						board[lineNum][rowNum] = value;
					}
					else{
						throw new ArrayStoreException();
					}
					rowNum++;
				}
				lineNum++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 *Draws the board, for every square it draws the corresponding colour.
	 *@param g - The graphics object
	 */
	private void drawBoard(Graphics2D g){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j].equals("R")){
					g.setColor(Color.GRAY);
					g.fillRect(j*(width/23), i*(height/22), width/23, height/22);
				}
			}
		}
		g.setColor(Color.BLACK);
		for(Point door : gameBoard.getDoors()){
			g.fillRect(((int) door.getX()*(width/23)), ((int) door.getY()*(height/22)), width/23, height/22);
		}
		g.fillRect(width/23, 0, 1, height);
		for(int i = 0; i < board.length; i++){
			g.setColor(Color.BLACK);
			g.fillRect(0, (i+1)*(height/22), width, 1);
			g.fillRect((i+2)*(width/23), 0, 1, height);
		}
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j].equals("C")){
					g.setColor(Color.RED);
					g.fillRect(j*(width/23), i*(height/22), width/23, height/22);
				}
			}
		}
		g.setColor(new Color(0x796145));
		for(Point p : gameBoard.getStairwells()){
			g.fillRect(((int) p.getX()*(width/23)), ((int) p.getY()*(height/22)), width/23, height/22);
		}
		Font myFont = new Font("Serif", Font.ITALIC, width/60);
		g.setFont(myFont);
		g.setColor(Color.RED);
		for(Room room : gameBoard.getRooms()){
			g.drawString(room.getName(),((int) room.getLocation().getX()*(width/23)), ((int) room.getLocation().getY()*(height/22)));
		}
	}

	private void drawIcons(Graphics2D g){
		for(Player p : players){
			g.setColor(p.getColor());
			g.fillOval((int) p.getLocation().getX()*(width/23), (int) p.getLocation().getY()*(height/22), width/23, height/22);
		}
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}
}