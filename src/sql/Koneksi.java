package sql;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author aditjoos
 */
public class Koneksi {
    public Connection con;
    public Statement stm;
    
    public void config(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/petz_ta", "root", "bagusaditamapp");
            stm = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal : "+e.getMessage());
        }
    }
}
