package proiect_sgbd;

import javax.swing.JOptionPane;

public class F4 extends javax.swing.JFrame {

    private Workspace w = Workspace.getInstance();

    public F4() {
        initComponents();
        this.setLocation(400, 200);
        l2.setVisible(false);
        btnSearch.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p = new javax.swing.JPanel();
        lTitlu = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        l1 = new javax.swing.JLabel();
        btnVerify = new javax.swing.JButton();
        l2 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proiect SGBD");

        lTitlu.setText("Adăugare constrângeri NOT NULL ");

        l1.setText("Pas 1. Verificați existența tabelelor fără coloane NOT NULL");

        btnVerify.setText("Verifică");
        btnVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyActionPerformed(evt);
            }
        });

        l2.setText("Pas 2. Căutați tabele care au coloane cu toate câmpurile completate");

        btnSearch.setText("Caută");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pLayout = new javax.swing.GroupLayout(p);
        p.setLayout(pLayout);
        pLayout.setHorizontalGroup(
            pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator)
            .addGroup(pLayout.createSequentialGroup()
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pLayout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(lTitlu))
                    .addGroup(pLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(l1)
                            .addComponent(l2))
                        .addGap(67, 67, 67)
                        .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVerify)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        pLayout.setVerticalGroup(
            pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lTitlu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l1)
                    .addComponent(btnVerify))
                .addGap(22, 22, 22)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l2)
                    .addComponent(btnSearch))
                .addContainerGap(188, Short.MAX_VALUE))
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

    private void btnVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyActionPerformed
        int rsp=w.checkTablesNOTNULL();//Verifica daca exista tabele cu insuficiente coloane not null
        String message;
        if(rsp==0){//daca toate tabelele sunt proiectate corect, fereastra curenta se inchide si se deschide fereastra initiala
            message="Toate tabelele au cel puțin o coloană NOT NULL (în afară de PK)";
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l1.setEnabled(false);
            btnVerify.setEnabled(false);
            this.dispose();
            new F1().setVisible(true);
        }
        if(rsp==1){
            message="Există tabele fără coloane NOT NULL";//daca exista tabele proiectate gresit, se afiseaza mesaj corespunzator si se face vizibila optiunea urmatoare
            JOptionPane.showMessageDialog(null, message, "Atenție", JOptionPane.OK_OPTION);
            l1.setEnabled(false);
            btnVerify.setEnabled(false);
            l2.setVisible(true);
            btnSearch.setVisible(true);
        }
    }//GEN-LAST:event_btnVerifyActionPerformed
    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        w.showBadTables();//afiseaza in fereastra noua tabelele proiectate greesit
        this.dispose();//fereastra curenta se inchide
    }//GEN-LAST:event_btnSearchActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F4().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnVerify;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel l2;
    private javax.swing.JLabel lTitlu;
    private javax.swing.JPanel p;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables
}
