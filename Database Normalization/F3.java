package proiect_sgbd;

import javax.swing.JOptionPane;

public class F3 extends javax.swing.JFrame {

    private Workspace w = Workspace.getInstance();

    public F3() {
        initComponents();
        this.setLocation(400, 200);
        l2.setVisible(false);
        btnAdd.setVisible(false);
        l3.setVisible(false);
        btnVerify.setVisible(false);
        l4.setVisible(false);
        btnAddPK2.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p = new javax.swing.JPanel();
        lPK = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        l1 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        l3 = new javax.swing.JLabel();
        l2 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnVerify = new javax.swing.JButton();
        l4 = new javax.swing.JLabel();
        btnAddPK2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proiect SGBD");

        lPK.setText("Adăugare chei primare");

        l1.setText("Pas1. Pentru a adăuga chei primare, trebuie să găsim tabele la care acestea lipsesc");

        btnSearch.setText("Caută");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        l3.setText("Pas2. Verificați corectitudinea cheilor primare existente");

        l2.setText("Pas1.2 Adăugați chei primare surogat tabelelor la care acestea lipsesc");

        btnAdd.setText("Adaugă");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnVerify.setText("Verifică");
        btnVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyActionPerformed(evt);
            }
        });

        l4.setText("Pas2.1 Adăugați chei primare surogat tabelelor cu cheile primare gestionate greșit");

        btnAddPK2.setText("Adaugă");
        btnAddPK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPK2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pLayout = new javax.swing.GroupLayout(p);
        p.setLayout(pLayout);
        pLayout.setHorizontalGroup(
            pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pLayout.createSequentialGroup()
                        .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pLayout.createSequentialGroup()
                                .addGap(256, 256, 256)
                                .addComponent(lPK))
                            .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pLayout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(l4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAddPK2))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pLayout.createSequentialGroup()
                                    .addComponent(l1)
                                    .addGap(41, 41, 41)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pLayout.createSequentialGroup()
                                    .addComponent(l3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnVerify))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pLayout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(l2)
                                    .addGap(66, 66, 66)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(95, 95, 95))
                    .addGroup(pLayout.createSequentialGroup()
                        .addComponent(separator)
                        .addContainerGap())))
        );
        pLayout.setVerticalGroup(
            pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lPK)
                .addGap(18, 18, 18)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l1)
                    .addComponent(btnSearch))
                .addGap(10, 10, 10)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l2)
                    .addComponent(btnAdd))
                .addGap(18, 18, 18)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l3)
                    .addComponent(btnVerify))
                .addGap(18, 18, 18)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l4)
                    .addComponent(btnAddPK2))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        int rsp = w.verifyTablesWithoutPK();
        String message;
        if (rsp == 0) {
            message = "Nu exista tabele fără chei primare în această bază de date";
            JOptionPane.showMessageDialog(p, message, "Ateție", JOptionPane.OK_OPTION);
            l1.setEnabled(false);
            btnSearch.setVisible(false);
            l3.setVisible(true);
            btnVerify.setVisible(true);
        } else if (rsp == 1) {
            message = "Exista tabele fără chei primare în această bază de date";
            JOptionPane.showMessageDialog(p, message, "Atenție", JOptionPane.OK_OPTION);
            l1.setEnabled(false);
            btnSearch.setVisible(false);
            l2.setVisible(true);
            btnAdd.setVisible(true);
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int ret = w.createSQ();
        if (ret == -1) {
            this.dispose();
            new F1().setVisible(true);
        }
        ret = w.addSurrogatePK();
        if (ret == -1) {
            this.dispose();
            new F1().setVisible(true);
        } else {
            String message = "Cheile primare surogat au fost adaugate cu succes tabelelor fara chei primare";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l2.setEnabled(false);
            btnAdd.setVisible(false);
            l3.setVisible(true);
            btnVerify.setVisible(true);
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyActionPerformed
        String message;
        int ret = w.verifyExistentPK();
        if (ret == -1) {
            this.dispose();
            new F1().setVisible(true);
        }
        if (ret == 0) {
            message = "Toate tabelele au cheile primare generate corect";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l3.setEnabled(false);
            btnVerify.setVisible(false);
            new F1().setVisible(true);
        }
        if (ret == 1) {
            message = "Există tabele cu chei primare pe mai multe atribute";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l3.setEnabled(false);
            btnVerify.setVisible(false);
            l4.setVisible(true);
            btnAddPK2.setVisible(true);
        }
        if (ret == 2) {
            message = "Există tabele cu chei primare care nu sunt numerice";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l3.setEnabled(false);
            btnVerify.setVisible(false);
            l4.setVisible(true);
            btnAddPK2.setVisible(true);
        }
        if (ret == 3) {
            message = "Există atât tabele cu chei primare pe mai multe atricute cât și chei primare care nu sunt numerice";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l3.setEnabled(false);
            btnVerify.setVisible(false);
            l4.setVisible(true);
            btnAddPK2.setVisible(true);
        }
    }//GEN-LAST:event_btnVerifyActionPerformed

    private void btnAddPK2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPK2ActionPerformed
        int r = w.addPK2();
        if (r == 1) {
            String message = "Toate tabelele au cheile primare gestionate corect.";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            this.dispose();
            new F1().setVisible(true);
        } else {
            this.dispose();
            new F1().setVisible(true);
        }
    }//GEN-LAST:event_btnAddPK2ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(F3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddPK2;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnVerify;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel l2;
    private javax.swing.JLabel l3;
    private javax.swing.JLabel l4;
    private javax.swing.JLabel lPK;
    private javax.swing.JPanel p;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables
}
