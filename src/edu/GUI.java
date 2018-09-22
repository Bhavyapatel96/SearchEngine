/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dayanarios
 */
public class GUI extends javax.swing.JFrame {
    private JFrame frame; 
    private static Path directoryPath; 
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private int xCoord = dim.width/2; 
    private int yCoord = dim.height/2;
    String indexingMsg = "Indexing Corpus";
    //private javax.swing.JButton indexingCorpusButton = new javax.swing.JButton(); 

    /**
     * Creates new form GUI
     */
    public GUI() {
        
        initComponents();
        this.setLocation(xCoord-this.getSize().width/2,yCoord-this.getSize().height/2);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DirectoryDialogBox = new javax.swing.JDialog();
        SearchDirectoriesButton = new javax.swing.JButton();
        DirectoryDirectionsLabel = new javax.swing.JLabel();
        indexingCorpusMessage = new javax.swing.JOptionPane();
        directoryChooser = new javax.swing.JFileChooser();
        indexingCorpusButton = new javax.swing.JButton();
        ProjectTitleLabel = new javax.swing.JLabel();
        SearchBarTextField = new javax.swing.JTextField();

        DirectoryDialogBox.setTitle("Select Directory");
        DirectoryDialogBox.setSize(new java.awt.Dimension(400, 246));

        SearchDirectoriesButton.setBackground(new java.awt.Color(0, 102, 204));
        SearchDirectoriesButton.setText("Directories");
        SearchDirectoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchDirectoriesButtonActionPerformed(evt);
            }
        });

        DirectoryDirectionsLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        DirectoryDirectionsLabel.setText("Please select a directory to index. ");

        DirectoryDialogBox.setLocation(xCoord-DirectoryDialogBox.getSize().width/2, yCoord-DirectoryDialogBox.getSize().height/2);
        DirectoryDialogBox.setVisible(true);

        javax.swing.GroupLayout DirectoryDialogBoxLayout = new javax.swing.GroupLayout(DirectoryDialogBox.getContentPane());
        DirectoryDialogBox.getContentPane().setLayout(DirectoryDialogBoxLayout);
        DirectoryDialogBoxLayout.setHorizontalGroup(
            DirectoryDialogBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DirectoryDialogBoxLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SearchDirectoriesButton)
                .addGap(147, 147, 147))
            .addGroup(DirectoryDialogBoxLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(DirectoryDirectionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        DirectoryDialogBoxLayout.setVerticalGroup(
            DirectoryDialogBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DirectoryDialogBoxLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(DirectoryDirectionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(SearchDirectoriesButton)
                .addGap(78, 78, 78))
        );

        indexingCorpusMessage.setVisible(false);
        indexingCorpusMessage.setLocation(xCoord-indexingCorpusMessage.getSize().width/2, yCoord-indexingCorpusMessage.getSize().height/2);

        directoryChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryChooserActionPerformed(evt);
            }
        });

        indexingCorpusButton.setText("Ok");
        indexingCorpusButton.setEnabled(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ProjectTitleLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        ProjectTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProjectTitleLabel.setText("Positional Inverted Search Engine");

        SearchBarTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        SearchBarTextField.setText("Search for query");
        SearchBarTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchBarTextField)
                    .addComponent(ProjectTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ProjectTitleLabel)
                .addGap(44, 44, 44)
                .addComponent(SearchBarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(191, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchDirectoriesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchDirectoriesButtonActionPerformed
        // TODO add your handling code here:
        directoryChooser = new JFileChooser(); 
        directoryChooser.setCurrentDirectory(new java.io.File("~")); //starts at root directory
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);
        
        if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
        { 
           DirectoryDialogBox.dispose();
           JOptionPane.showOptionDialog(indexingCorpusMessage, "Indexing corpus", "Indexing Corpus", javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE, null, null ,null);
           this.setVisible(true);
           //ProjectTitleLabel.setVisible(rootPaneCheckingEnabled);
           //SearchBarTextField.setVisible(rootPaneCheckingEnabled);
           File file = directoryChooser.getSelectedFile(); 
           directoryPath = file.toPath(); 
           
           //starts indexing 
           
            try {
                
                DocumentIndexer.startIndexing(directoryPath);
                
            } catch (Exception ex) {
                System.out.println("problem indexing"); 
            }
            
          
        }
        else 
        {
          System.out.println("No Selection ");
        }
        
        
        
    }//GEN-LAST:event_SearchDirectoriesButtonActionPerformed

    private void directoryChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directoryChooserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_directoryChooserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }

        try{

        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex){}
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(false);
                
                
                
            }
        });
        
        
       
        
        //System.exit(0); //need to dispose of current gui
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JDialog DirectoryDialogBox;
    private javax.swing.JLabel DirectoryDirectionsLabel;
    private javax.swing.JLabel ProjectTitleLabel;
    private javax.swing.JTextField SearchBarTextField;
    private javax.swing.JButton SearchDirectoriesButton;
    private javax.swing.JFileChooser directoryChooser;
    protected static javax.swing.JButton indexingCorpusButton;
    private javax.swing.JOptionPane indexingCorpusMessage;
    // End of variables declaration//GEN-END:variables
}
