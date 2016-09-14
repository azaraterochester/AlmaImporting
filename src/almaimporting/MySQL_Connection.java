package almaimporting;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class MySQL_Connection {
    
    private Connection connection = null;
    private   MySQL_Query_Controller queryController = new MySQL_Query_Controller(this.connection);
    public MySQL_Connection(String connectionString) throws SQLException{
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(connectionString);
         queryController = new MySQL_Query_Controller(this.connection);
      } catch (ClassNotFoundException | SQLException e) {
         System.out.println("Error creating connection from driver (MySQL_Query constructor):" + e.getMessage());
      } 
    }
    
    public void executeQuery(){
         try { 
            queryController.readData(2);
         } catch (Exception ex) {
             System.out.println("Error executing query:" + ex.getMessage());
         }  
      close();   
    }
    
    
    
    private void close() {
      try {
         if (connection != null) {
            connection.close();
         }
      } catch (Exception e) {
          System.out.println(e);
      }
   }  
}
