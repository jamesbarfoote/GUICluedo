
package guiCluedo.ui;

import guiCluedo.game.Board;

public class UI extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 1L;

    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
        Board b = new Board();
        UICanvas canvas = new UICanvas(b);
        this.add(canvas);
    }

    /**
     * 
     */
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jSeparator1 = new javax.swing.JSeparator();
        rollDice = new javax.swing.JButton();
        diceRolled = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        GameMenu = new javax.swing.JMenu();

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

        diceRolled.setEditable(false);
        diceRolled.setText("You rolled: ");

        jTextField2.setEditable(false);
        jTextField2.setText("Your hand:");
        jTextField2.setName(""); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rollDice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(diceRolled))
                .addGap(64, 64, 64)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(325, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(311, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rollDice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diceRolled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
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
    private javax.swing.JTextField diceRolled;
    private javax.swing.JMenu fileMenu;
    private static javax.swing.JFrame jFrame1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JMenuItem newGame;
    private javax.swing.JButton rollDice;    
}
