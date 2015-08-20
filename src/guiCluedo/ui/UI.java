
package guiCluedo.ui;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import guiCluedo.game.Board;

public class UI extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 1L;

    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
        Board b = new Board();
        BoardCanvas canvas = new BoardCanvas(b, boardArea.getWidth(), boardArea.getHeight());
        boardArea.add(canvas);
        
        this.addComponentListener(new ComponentAdapter() 
        {  
                public void componentResized(ComponentEvent evt) {
                    Component c = (Component)evt.getSource();
                    System.out.println("Redrawn");
                    System.out.println("Width = " + boardArea.getWidth());
                    System.out.println("Height = " + boardArea.getHeight());
                    BoardCanvas canvas = new BoardCanvas(b, boardArea.getWidth(), boardArea.getHeight());
                }
        });
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
            .addGap(0, 500, Short.MAX_VALUE)
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

    private void rollDiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rollDiceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rollDiceActionPerformed

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
}
