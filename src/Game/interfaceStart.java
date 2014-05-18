package Game;

import Sockets.ServerRead;
import Sockets.ServerConnection;
import Utility.Constants;

/**
 *
 * @author Madawa
 */
public class interfaceStart extends javax.swing.JFrame {

    /** Creates new form interfaceStart */
    public interfaceStart() {
        initComponents();
        setLocationRelativeTo(this);
        setResizable(false);
        setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        quitText = new javax.swing.JButton();
        serverHostTF = new javax.swing.JTextField();
        hostLabel = new javax.swing.JLabel();
        writePortLabel = new javax.swing.JLabel();
        writePortTF = new javax.swing.JTextField();
        readPortTF = new javax.swing.JTextField();
        readPortLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tank Battle");

        startButton.setText("CONNECT");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        quitText.setText("QUIT");
        quitText.setToolTipText("");
        quitText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitTextActionPerformed(evt);
            }
        });

        serverHostTF.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        serverHostTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        serverHostTF.setText("127.0.0.1");
        serverHostTF.setToolTipText("Skip to use default values");

        hostLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hostLabel.setText("Server Host IP");
        hostLabel.setToolTipText("");

        writePortLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        writePortLabel.setText("Write Port");
        writePortLabel.setToolTipText("");

        writePortTF.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        writePortTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        writePortTF.setText("6000");
        writePortTF.setToolTipText("Skip to use default values");

        readPortTF.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        readPortTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        readPortTF.setText("7000");
        readPortTF.setToolTipText("Skip to use default values");

        readPortLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        readPortLabel.setText("Listen Port");
        readPortLabel.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(startButton)
                        .addGap(45, 45, 45)
                        .addComponent(quitText))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(readPortLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(readPortTF, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(writePortLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(writePortTF, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(hostLabel)
                            .addGap(36, 36, 36)
                            .addComponent(serverHostTF, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverHostTF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hostLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(writePortTF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(writePortLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(readPortTF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(readPortLabel))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quitText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        
        try{
            Constants.setHost(serverHostTF.getText());
            Constants.setWritePort(Integer.parseInt(writePortTF.getText()));
            Constants.setReadPort(Integer.parseInt(readPortTF.getText()));
        }catch(Exception e){
            Constants.setHost(null);
            Constants.setWritePort(-1);
            Constants.setReadPort(-1);
        }
        
        ServerConnection connection = new ServerConnection();
        Thread sConnection = new Thread(connection);
        sConnection.start();
        ServerRead in = new ServerRead();
        Thread readThread = new Thread(in);
        readThread.start();
        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_startButtonActionPerformed

    private void quitTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitTextActionPerformed
        System.exit(0);
    }//GEN-LAST:event_quitTextActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interfaceStart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hostLabel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton quitText;
    private javax.swing.JLabel readPortLabel;
    private javax.swing.JTextField readPortTF;
    private javax.swing.JTextField serverHostTF;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel writePortLabel;
    private javax.swing.JTextField writePortTF;
    // End of variables declaration//GEN-END:variables
}
