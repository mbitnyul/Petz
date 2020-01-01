package sql;

import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aditjoos
 */
public class DB {
    public Connection con;
    public Statement stmt;
    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/petz_ta", "root", "bagusaditamapp");
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal : "+e.getMessage());
        }
    }
    
    public void DataAdd(String table, String[] fields, String[] values, String successMessage){
        Connect();
        
        try {
            String query = "INSERT INTO "+table+"(";
            
            for (int i = 0; i < fields.length; i++) {
                if(i != fields.length-1) query += fields[i] + ",";
                else query += fields[i] + ") VALUES (";
            }
            
            for (int i = 0; i < values.length; i++) {
                if(i != values.length-1) query += values[i] + ",";
                else query += values[i] + ")";
            }
            
            try (PreparedStatement p_stm = (PreparedStatement)con.prepareStatement(query)) {
                p_stm.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(null, successMessage);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan = "+e.getMessage());
        }
    }
    
    public void DataGet(javax.swing.JTable JTableName, String table, String[] getColumn){
        DefaultTableModel model = new DefaultTableModel();
        JTableName.setModel(model);
            
        for (String column : getColumn) {
            model.addColumn(column);
        }

        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Connect();
            Statement stm = con.createStatement();
            String query = "SELECT * FROM "+table;
            ResultSet result = stm.executeQuery(query);

            while(result.next()){
                Object[] obj = new Object[getColumn.length];
                for(int i = 0; i < getColumn.length; i++){
                    obj[i] = result.getString(getColumn[i]);
                }

                model.addRow(obj);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan = "+e.getMessage());
        }
    }
    
    public void DataUpdate(){
        
    }
    
    public void DataRemove(String table, String whereClause, String whereValue){
        try {
            Connect();
            
            String query = "DELETE FROM "+table+" WHERE "+whereClause+" = '"+whereValue+"'";
            try (PreparedStatement p_stm = (PreparedStatement)con.prepareStatement(query)) {
                p_stm.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan = "+e.getMessage()+", "+e);
        }
    }
    
    public String[] TableClick(javax.swing.JTable JTableName, String table, String whereClause, String[] getColumns){
        int i = JTableName.getSelectedRow();
        
        try{
            Connect();
            
            String id = (String) JTableName.getModel().getValueAt(i,0);
        
            String query = "SELECT * FROM "+table+" WHERE "+whereClause+" = "+id+"";
            Statement stm = con.createStatement();
            ResultSet result = stm.executeQuery(query);
            
            if (result.next()) {
                String[] resultArray = new String[getColumns.length];
                for(int j = 0; j < getColumns.length; j++){
                    resultArray[j] = result.getString(getColumns[j]);
                }
                
                return resultArray;
            }else{
                JOptionPane.showMessageDialog(null, "Data tidak ada.");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan = "+e);
        }
        return null;
    }
}
