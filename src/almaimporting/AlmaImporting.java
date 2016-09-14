package almaimporting;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlmaImporting {

    public static Connection connection = null;

    public static void main(String[] args) {

        String connectionString = "jdbc:mysql://10.0.3.8/"
                + "alma_integrations?"
                + "user=root&"
                + "password=irc4Quag&"
                + "useSSL=false";
        try {
            MySQL_Connection myConnection = new MySQL_Connection(connectionString);
            connection = DriverManager.getConnection(connectionString);
            myConnection.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AlmaImporting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
