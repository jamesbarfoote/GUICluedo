package guiCluedo.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import guiCluedo.game.Board;
import guiCluedo.game.Player;

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
    				location.setLocation(location.getX()-1, location.getY());
        			player.setLocation(location);
        			player.setRoll(player.getRoll()-1);
    			}
    		}
    		else if (direction.equals("Up")){
    			if(location.getY() > 0){
    				location.setLocation(location.getX(), location.getY()-1);
        			player.setLocation(location);
        			player.setRoll(player.getRoll()-1);
    			}
    		}
    		else if (direction.equals("Right")){
    			if(location.getX() < 21){
    				location.setLocation(location.getX()+1, location.getY());
        			player.setLocation(location);
        			player.setRoll(player.getRoll()-1);
    			}
    		}
    		else if (direction.equals("Down")){
    			if(location.getY() < 19){
    				player.setLocation(location);
    				location.setLocation(location.getX(), location.getY()+1);
    				player.setRoll(player.getRoll()-1);
    			}
    		}
    		this.canvas.repaint();
    		
    		//If player location is contained within a room, then show the suggestion button on the UI
        }
        
        private boolean isValidMove(Point newLocation){
//        	for(Polygon room : this.board.getRooms()){
//        		
//        	}
        	
        	return true;
        }
    }