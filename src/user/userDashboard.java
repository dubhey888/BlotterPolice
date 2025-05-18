/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import config.Session;
import java.awt.Color;
import javax.swing.JOptionPane;
import LoginPage.loginForm;
import config.dbConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author mypc
 */
public class userDashboard extends javax.swing.JFrame {

    /**
     * Creates new form userDashboard
     */
    public userDashboard() {
        initComponents();
         loadUserProfile();
         loadReportsData();
    }
    Color shok = new Color(255,255,255);
    Color redd = new Color(0,0,153);
private void loadUserProfile() {
    dbConnector dbc = new dbConnector();
    Session sess = Session.getInstance();

    String query = "SELECT u_image FROM tbl_users WHERE u_id = ?";

    try (Connection conn = dbc.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, sess.getUid());
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String imagePath = rs.getString("u_image");

            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                u_image.setIcon(icon);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        JOptionPane.showMessageDialog(this, "Error loading profile image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}   
    
    private void logoutUser(String username) {
    dbConnector connector = new dbConnector();
    try (Connection con = connector.getConnection()) {

        String updateQuery = "UPDATE tbl_log SET log_status = 'Inactive', logout_time = NOW() " +
                             "WHERE LOWER(u_username) = LOWER(?) AND log_status = 'Active'";

        try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
            System.out.println("Logging out user: " + username); // Debug
            stmt.setString(1, username);
            int updatedRows = stmt.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "User " + username + " has logged out successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No active session found for " + username);
            }
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error logging out: " + ex.getMessage());
    }
}
     public void displayReportData() {
    try {
        dbConnector dbc = new dbConnector();
        Session sess = Session.getInstance();
        int currentUserId = sess.getUid();

        String query = "SELECT report_id, full_name, incident_type, description, location, " +
                       "CONCAT(date_of_incident, ' ', time_of_incident) AS datetime, status " +
                       "FROM reports WHERE u_id = ?";

        PreparedStatement pst = dbc.getConnection().prepareStatement(query);
        pst.setInt(1, currentUserId);

        ResultSet rs = pst.executeQuery();
        tblblotter.setModel(DbUtils.resultSetToTableModel(rs));

        rs.close();
        pst.close();
    } catch (SQLException ex) {
        System.out.println("Errors: " + ex.getMessage());
    }
}




  

    
 public void tableChanged(TableModelEvent e) {
    if (e.getType() == TableModelEvent.UPDATE) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (row == -1 || column == -1) {
            return;
        }

        updateReportDatabase(row, column);
    }

    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel();
    model.setRowCount(0);

    String sql = "SELECT report_id, full_name, incident_type, description, location, date_of_incident, time_of_incident, status FROM reports WHERE u_id = ?";

    try (Connection connect = new dbConnector().getConnection();
         PreparedStatement pst = connect.prepareStatement(sql)) {

        Session sess = Session.getInstance();
        int userId = sess.getUid();

        pst.setInt(1, userId);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("report_id"),
                    rs.getString("full_name"),
                    rs.getString("incident_type"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getDate("date_of_incident"),
                    rs.getTime("time_of_incident"),
                    rs.getString("status")
                };
                model.addRow(row);
            }
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    
private void updateReportDatabase(int row, int column) {
    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel(); 
    try {
        int reportId = Integer.parseInt(model.getValueAt(row, 0).toString()); // Assuming ID is column 0
        String columnName = model.getColumnName(column);
        Object newValue = model.getValueAt(row, column);

        String sql = "UPDATE reports SET " + columnName + " = ? WHERE report_id = ?";
        try (Connection conn = new dbConnector().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setObject(1, newValue);
            pst.setInt(2, reportId);
            pst.executeUpdate();
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Update Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void loadReportsData() {
    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel();
    model.setRowCount(0);

    String sql = "SELECT report_id, full_name, incident_type, description, location, date_of_incident, time_of_incident, status FROM reports WHERE u_id = ?";

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blotter", "root", "");
         PreparedStatement pst = con.prepareStatement(sql)) {

        Session sess = Session.getInstance();
        int userId = sess.getUid();

        pst.setInt(1, userId);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("report_id"),
                    rs.getString("full_name"),
                    rs.getString("incident_type"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getDate("date_of_incident"),
                    rs.getTime("time_of_incident"),
                    rs.getString("status")
                });
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading report data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        UserName = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        u_image = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblblotter = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("USER'S DASHBOARD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(294, 294, 294))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 30));

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, 20, 280));

        UserName.setBackground(new java.awt.Color(204, 204, 204));
        UserName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        UserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UserName.setText("USER");
        jPanel3.add(UserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 110, 20));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        u_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        u_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-user-50.png"))); // NOI18N
        jPanel7.add(u_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 110, 110));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 130, 110));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 29, 29));
        jPanel6.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("USERS");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 10));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 90, 90));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ACCOUNT");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, 40));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 150, 40));

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("BLOTTER");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 40));

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 150, 40));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13MouseExited(evt);
            }
        });
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LOG OUT");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel13.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 10));

        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 68, 30));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 150, 370));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblblotter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Full name", "Incident Type", "Description", "Location", "Date of Incident", "Time of Incident", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblblotter);

        jPanel12.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 490, 310));

        getContentPane().add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 550, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        accountDetails acd = new accountDetails();
        acd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
      Session sess = Session.getInstance();
        if(sess.getUid()==0){ 
            JOptionPane.showMessageDialog(null, "No Account, Login First");
            loginForm lgf = new loginForm();
            lgf.setVisible(true);
            this.dispose();
        }else{
            UserName.setText(""+sess.getUsername());
    
        }
    }//GEN-LAST:event_formWindowActivated

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
         loginForm lgf = new loginForm();
         JOptionPane.showMessageDialog(null, "Logged out!!");
         lgf.setVisible(true);
         this.dispose();
         
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jPanel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseEntered
      
    }//GEN-LAST:event_jPanel13MouseEntered

    private void jPanel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseExited
       
    }//GEN-LAST:event_jPanel13MouseExited

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
     Session sess = Session.getInstance();
    if (sess.getUid() != 0) {
        logoutUser(sess.getUsername());  // Log out the current user // CLEAR
    }

    loginForm loginFrame = new loginForm();
    JOptionPane.showMessageDialog(null, "Log-out Success!");
    loginFrame.setVisible(true);
    this.dispose();
        
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
    UserReportForm urf = new UserReportForm();
        urf.setVisible(true);
        this.dispose();          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(userDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel UserName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblblotter;
    private javax.swing.JLabel u_image;
    // End of variables declaration//GEN-END:variables
}
