package guiCluedo.ui;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import guiCluedo.game.Board;
import guiCluedo.game.Player;
import guiCluedo.game.Room;

class MoveAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	String direction;
	Player player;
	BoardCanvas canvas;
	Board board;
	Room currentRoom;
	UI ui;

	MoveAction(String direction, Player player, BoardCanvas canvas, Board board, UI ui) {

		this.direction = direction;
		this.player = player;
		this.canvas = canvas;
		this.board = board;
		this.ui = ui;
	}

	/**
	 * Calls whenever a key is pressed, updates the location of the player
	 * and handles executing the shortcuts
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(direction.equals("N"))
		{
			ui.endTurnActionPerformed(null);
		}
		else if(direction.equals("A"))
		{
			ui.accusButtonActionPerformed(null);
		}
		else if(direction.equals("S"))
		{
			ui.guessButtonActionPerformed(null);
		}
		else if(direction.equals("G"))
		{
			ui.newGameActionPerformed(null);
		}
		
		if(player.getRoll()  <= 0){
			return;
		}
		Point location = player.getLocation();
		if (direction.equals("Left")){
			if(location.getX() > 0){
				Point newLocation = new Point((int) location.getX()-1, (int) location.getY());
				if(isValidMove(newLocation)){
					newLocation = checkStairwell(newLocation);
					board.getUsedSquares().remove(location);
					board.getUsedSquares().add(newLocation);
					board.getPlayerSquares().remove(location);
					board.getPlayerSquares().add(newLocation);
					location.setLocation(newLocation);
					player.setLocation(location);
					Room room = null;
					for(Room r : board.getRooms()){
						if(r.getBoundingBox().contains(newLocation)){
							room = r;
						}
					}
					player.setRoom(room);
					player.setRoll(player.getRoll()-1);
				}
			}
		}
		else if (direction.equals("Up")){
			if(location.getY() > 0){
				Point newLocation = new Point((int) location.getX(), (int) location.getY()-1);
				if(isValidMove(newLocation)){
					newLocation = checkStairwell(newLocation);
					board.getUsedSquares().remove(location);
					board.getUsedSquares().add(newLocation);
					board.getPlayerSquares().remove(location);
					board.getPlayerSquares().add(newLocation);
					location.setLocation(newLocation);
					player.setLocation(location);
					Room room = null;
					for(Room r : board.getRooms()){
						if(r.getBoundingBox().contains(newLocation)){
							room = r;
						}
					}
					player.setRoom(room);
					player.setRoll(player.getRoll()-1);
				}
			}
		}
		else if (direction.equals("Right")){
			if(location.getX() < 22){
				Point newLocation = new Point((int) location.getX()+1, (int) location.getY());
				if(isValidMove(newLocation)){
					newLocation = checkStairwell(newLocation);
					board.getUsedSquares().remove(location);
					board.getUsedSquares().add(newLocation);
					board.getPlayerSquares().remove(location);
					board.getPlayerSquares().add(newLocation);
					location.setLocation(newLocation);
					player.setLocation(location);
					Room room = null;
					for(Room r : board.getRooms()){
						if(r.getBoundingBox().contains(newLocation)){
							room = r;
						}
					}
					player.setRoom(room);
					player.setRoll(player.getRoll()-1);
				}
			}
		}
		else if (direction.equals("Down")){
			if(location.getY() < 21){
				Point newLocation = new Point((int) location.getX(), (int) location.getY()+1);
				if(isValidMove(newLocation)){
					newLocation = checkStairwell(newLocation);
					board.getUsedSquares().remove(location);
					board.getUsedSquares().add(newLocation);
					board.getPlayerSquares().remove(location);
					board.getPlayerSquares().add(newLocation);
					location.setLocation(newLocation);
					player.setLocation(location);
					Room room = null;
					for(Room r : board.getRooms()){
						if(r.getBoundingBox().contains(newLocation)){
							room = r;
						}
					}
					player.setRoom(room);
					player.setRoll(player.getRoll()-1);
				}
			}
		}
		
		this.canvas.repaint();
	}
	
	/**
	 * If player ends on a stairwell, update their location
	 * respectively.
	 */
	private Point checkStairwell(Point newLocation){
		if(newLocation.equals(board.getStairwells().get(0))){
			return board.getStairwells().get(2);
		}
		else if(newLocation.equals(board.getStairwells().get(1))){
			return board.getStairwells().get(3);
		}
		else if(newLocation.equals(board.getStairwells().get(2))){
			return board.getStairwells().get(0);
		}
		else if(newLocation.equals(board.getStairwells().get(3))){
			return board.getStairwells().get(1);
		}
		return newLocation;
	}

	/**
	 * Check if the current move is valid.
	 * @param newLocation - The location the player is trying to move
	 * @return boolean - whether the new location goes through a wall
	 */
	private boolean isValidMove(Point newLocation){
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
		for(Room room : this.board.getRooms()){
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
		for(Room room : this.board.getRooms()){
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