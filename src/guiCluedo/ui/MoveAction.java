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
            			player.setRoll(player.getRoll()-1);
    				}
    			}
    		}
    		else if (direction.equals("Up")){
    			if(location.getY() > 0){
    				Point newLocation = new Point((int) location.getX(), (int) location.getY()-1);
    				if(isValidMove(newLocation)){
    					location.setLocation(newLocation);
            			player.setLocation(location);
            			player.setRoll(player.getRoll()-1);
    				}
    			}
    		}
    		else if (direction.equals("Right")){
    			if(location.getX() < 21){
    				Point newLocation = new Point((int) location.getX()+1, (int) location.getY());
    				if(isValidMove(newLocation)){
    					location.setLocation(newLocation);
            			player.setLocation(location);
            			player.setRoll(player.getRoll()-1);
    				}
    			}
    		}
    		else if (direction.equals("Down")){
    			if(location.getY() < 19){
    				Point newLocation = new Point((int) location.getX(), (int) location.getY()+1);
    				if(isValidMove(newLocation)){
    					location.setLocation(newLocation);
        				player.setLocation(location);
        				player.setRoll(player.getRoll()-1);
    				}
    			}
    		}
    		this.canvas.repaint();
    		
    		//If player location is contained within a room, then show the suggestion button on the UI
        }
        
        /**
         * Check if the current move is valid by checking if it is a wall or not.
         * @param newLocation - The location the player is trying to move
         * @return boolean - whether the new location goes through a wall
         */
        private boolean isValidMove(Point newLocation){
        	boolean isWall = false;
        	for(Room room : this.board.getRooms()){
        		Polygon boundingBox = room.getBoundingBox();
        		if(boundingBox.contains(newLocation) && (!board.getDoors().contains(newLocation))){
        			isWall = true;
        		}
        	}
        	return !isWall;
        }
    }