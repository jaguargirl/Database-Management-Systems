package proiect_sgbd;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class F5 extends javax.swing.JFrame {

    private Workspace w = Workspace.getInstance();
    private ArrayList<String> columns = new ArrayList<String>();
    private String tabN;
    private String colN;

    public F5(ArrayList<String> tablesList) {
        initComponents();
        this.setLocation(400, 200);
        for (int i = 0; i < tablesList.size(); i++) {
            cmbTable.addItem(tablesList.get(i));//adauga in combobox-ul tables, numele tabelelor
        }
        mesaj.setText("");
        mesaj2.setText("");
        btnAdd.setEnabled(false);
        cmbCol.setEnabled(false);
        cmbTable.setSelectedIndex(-1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        tableName = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        cmbTable = new javax.swing.JComboBox<>();
        cmbCol = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        mesaj = new javax.swing.JLabel();
        mesaj2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SGBD Project");

        jLabel1.setText("Add NOT NULL columns");

        tableName.setText("Table");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        cmbTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTableActionPerformed(evt);
            }
        });

        cmbCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbColActionPerformed(evt);
            }
        });

        jLabel2.setText("Column");

        mesaj.setText("jLabel3");

        mesaj2.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(242, 242, 242)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
            .addGroup(layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(tableName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(156, 156, 156))
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbTable, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(cmbCol, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mesaj2)
                            .addComponent(mesaj))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tableName)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(mesaj)
                .addGap(10, 10, 10)
                .addComponent(mesaj2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdd)
                .addGap(73, 73, 73))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTableActionPerformed
        mesaj.setText("");
        mesaj2.setText("");
        if (cmbTable.getItemCount() == 0) {//daca nu mai exista tabele de corectat, se afiseaza un mesaj corespunzator 
            JOptionPane.showMessageDialog(null, "All the requested contraints of existence were successfuly added", "Attention", JOptionPane.OK_OPTION);
            this.dispose();//fereastra curenta se inchide
            new F1().setVisible(true);//se afiseaza prima fereastra
        }
        if (cmbTable.getSelectedIndex() > -1) {//daca este selectat un nume de tabel
            cmbCol.removeAllItems();//se goleste comboBox-ul de coloane
            cmbCol.setEnabled(true);
            cmbCol.setSelectedIndex(-1);
            tabN = cmbTable.getSelectedItem().toString();
            if (!columns.isEmpty()) {
                columns.clear();
            }
            columns = w.getColumns(tabN);//se cauta coloanele respective numelui tabelului
            if (columns != null) {
                for (int i = 0; i < columns.size() - 1; i++) {
                    cmbCol.addItem(columns.get(i));
                }
            }
            if (columns.get(columns.size() - 1) == "G") {
                mesaj.setText("Tabel " + tabN + " contains columns with no null fields.");
                mesaj.setForeground(new Color(23, 150, 0));
                mesaj2.setText("Select a column for adding constraint of existence");
                mesaj2.setForeground(new Color(23, 150, 0));
            } else {
                mesaj.setText("Table " + tabN + " does not contain columns with no null fields");
                mesaj.setForeground(new Color(186, 0, 37));
                mesaj2.setText("Select a column to fill with values and add constraint of existence");
                mesaj2.setForeground(new Color(186, 0, 37));
            }
        }
        cmbCol.setSelectedIndex(-1);
    }//GEN-LAST:event_cmbTableActionPerformed

    private void cmbColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColActionPerformed
        if (cmbCol.getSelectedIndex() > -1) {
            colN = cmbCol.getSelectedItem().toString();
            btnAdd.setEnabled(true);
        }
    }//GEN-LAST:event_cmbColActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (columns.get(columns.size() - 1) == "G") {
            int rsp = w.addNOTNULL(tabN, colN);
            if (rsp == 1) {
                cmbTable.removeItem(tabN);
            }
        }
        else{
            int rsp=w.completeNULLvalues(tabN, colN);
            if(rsp==1){
                cmbTable.removeItem(tabN);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JComboBox<String> cmbCol;
    private javax.swing.JComboBox<String> cmbTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel mesaj;
    private javax.swing.JLabel mesaj2;
    private javax.swing.JLabel tableName;
    // End of variables declaration//GEN-END:variables
}
