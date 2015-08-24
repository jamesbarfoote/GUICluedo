package guiCluedo.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

import guiCluedo.game.Board;

public class shortcutKeys  extends AbstractAction {
	UI ui;
	String key;
	BoardCanvas canvas;
	Board board;

	public shortcutKeys(String key, UI ui, BoardCanvas canvas, Board b) {
		this.ui = ui;
		this.key = key;
		this.canvas = canvas;
		this.board = b;
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(key.equals("Next"))
		{
			ui.endTurnActionPerformed(null);
		}

	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEnabled(boolean arg0) {
		// TODO Auto-generated method stub

	}

}
