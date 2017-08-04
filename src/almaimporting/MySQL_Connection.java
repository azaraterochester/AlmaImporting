package almaimporting;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class MySQL_Connection {
    
    private Connection connection = null;
    private   MySQL_Query_Controller queryController = new MySQL_Query_Controller(this.connection);
    private int last_id = 3054;
    
    
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
        for(int i = 2; i<=last_id; i++){ 
        try {
            if(i<=last_id){
            queryController.readData(i);
            }
            else{
                queryController.last_record = true;
            }
         } catch (Exception ex) {
             System.out.println("Error executing query:" + ex.getMessage());
         }
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
