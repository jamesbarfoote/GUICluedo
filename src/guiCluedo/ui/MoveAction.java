package guiCluedo.ui;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

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

	MoveAction(String direction, Player player, BoardCanvas canvas, Board board) {

		this.direction = direction;
		this.player = player;
		this.canvas = canvas;
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(player.getRoll()  <= 0){
			return;
		}
		Point location = player.getLocation();
		if (direction.equals("Left")){
			if(location.getX() > 0){
				Point newLocation = new Point((int) location.getX()-1, (int) location.getY());
				if(isValidMove(newLocation)){
					location.setLocation(newLocation);
					player.setLocation(location);
					//player.setRoll(player.getRoll()-1);
				}
			}
		}
		else if (direction.equals("Up")){
			if(location.getY() > 0){
				Point newLocation = new Point((int) location.getX(), (int) location.getY()-1);
				if(isValidMove(newLocation)){
					location.setLocation(newLocation);
					player.setLocation(location);
					//player.setRoll(player.getRoll()-1);
				}
			}
		}
		else if (direction.equals("Right")){
			if(location.getX() < 22){
				Point newLocation = new Point((int) location.getX()+1, (int) location.getY());
				if(isValidMove(newLocation)){
					location.setLocation(newLocation);
					player.setLocation(location);
					//player.setRoll(player.getRoll()-1);
				}
			}
		}
		else if (direction.equals("Down")){
			if(location.getY() < 21){
				Point newLocation = new Point((int) location.getX(), (int) location.getY()+1);
				if(isValidMove(newLocation)){
					location.setLocation(newLocation);
					player.setLocation(location);
					//player.setRoll(player.getRoll()-1);
				}
			}
		}
		this.canvas.repaint();
	}
	//	Point p = new Point(0,0);
	//	stairwells.add(p);
	//	p = new Point(20,0);
	//	stairwells.add(p);
	//	p = new Point(18,21);
	//	stairwells.add(p);
	//	p = new Point(0,16);
	//	stairwells.add(p);
	private void checkStairwells(){
		if(player.getLocation().getX() == 0 && player.getLocation().getY() == 0){
			//player.setLocation(p);
		}
		else if(player.getLocation().getX() == 20 && player.getLocation().getY() == 0){

		}
		else if(player.getLocation().getX() == 18 && player.getLocation().getY() == 21){

		}
		else if(player.getLocation().getX() == 0 && player.getLocation().getY() == 16){

		}
	}

	/**
	 * Check if the current move is valid.
	 * @param newLocation - The location the player is trying to move
	 * @return boolean - whether the new location goes through a wall
	 */
	private boolean isValidMove(Point newLocation){
		if(board.getCentre().contains(newLocation)){
			return false;
		}
		for(Room room : this.board.getRooms()){
			Polygon boundingBox = room.getBoundingBox();
			if(boundingBox.contains(player.getLocation())){
				if(!boundingBox.contains(newLocation)){
					if(board.getDoors().contains(player.getLocation())){
						return true;
					}
					else{
						return false;
					}
				}
				return true;
			}
		}
		for(Room room : this.board.getRooms()){
			Polygon boundingBox = room.getBoundingBox();
			if(boundingBox.contains(newLocation)){
				if(!board.getDoors().contains(newLocation)){
					return false;
				}
				else{
					return true;
				}
			}
		}
		return true;
	}
}