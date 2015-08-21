package guiCluedo.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import guiCluedo.game.Player;

class MoveAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
		String direction;
        Player player;
        BoardCanvas canvas;

        MoveAction(String direction, Player player, BoardCanvas canvas) {

            this.direction = direction;
            this.player = player;
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // Same as the move method in the question code.
            // Player can be detected by e.getSource() instead and call its own move method.
        	Point location = player.getLocation();
    		if (direction.equals("Left")){
    			location.setLocation(location.getX()-1, location.getY());
    			player.setLocation(location);
    		}
    		else if (direction.equals("Up")){
    			location.setLocation(location.getX(), location.getY()-1);
    			player.setLocation(location);
    		}
    		else if (direction.equals("Right")){
    			location.setLocation(location.getX()+1, location.getY());
    			player.setLocation(location);
    		}
    		else if (direction.equals("Down")){
    			location.setLocation(location.getX(), location.getY()+1);
    			player.setLocation(location);
    		}
    		this.canvas.repaint();
    		
    		//If player location is contained within a room, then show the suggestion button on the UI
        }
    }