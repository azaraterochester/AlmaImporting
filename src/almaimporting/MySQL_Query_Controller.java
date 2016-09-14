package almaimporting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MySQL_Query_Controller {
    
    private Connection connection = null;
    private Statement statement1 = null;
    private PreparedStatement statement2 = null;
    private ResultSet resultSet = null;
    public int id = 0;
    public String grade_level = "";
    public String code = "";
    public String hierarchy = "";
    public String description = "";
    public String grade_level_id = "";
    
    
    public MySQL_Query_Controller(Connection connection) {
        this.connection = connection;
    }
    
    public void readData(int i) throws Exception {
        
        try {
            statement1 = connection.createStatement();
            String query = "select * "
                            + "from Proficiencies "
                            + "where id = "+i+";";
            resultSet = statement1.executeQuery(query);
            this.getResults(resultSet);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        } finally {
           System.out.println("Record "+i+" read");
        }
       
    }
    
     private void getResults(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            grade_level = resultSet.getString("grade_level");
            code = resultSet.getString("code");
            hierarchy = resultSet.getString("hierarchy");
            description = resultSet.getString("description");
            grade_level_id = resultSet.getString("grade_level_id");
        }
        this.splitDescription(hierarchy,description);
    }
     
    private void splitDescription(String hierarchy, String description) throws SQLException{
                
        String school = "";
        String subject = "";
        String strand = "";
        String [] hierarchyArray = description.split("::");
        String learningExpectation = description;
        
        for(int i = 0; i < hierarchyArray.length; i++){
            
        }
        
        
        this.insertResults(grade_level,code,hierarchy,description,grade_level_id);

    }
     
    public void insertResults(String a, String b, String c, String d, String e) throws SQLException{
    String query2 = "INSERT INTO Proficiencies_Final(grade_level,code,hierarchy,description,grade_level_id)"
                            +"values (?, ?, ?, ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setString(1,a);
            statement2.setString(2,b);
            statement2.setString(3,c);
            statement2.setString(4,d);
            statement2.setString(5,e);
            
            System.out.println("WILL INSERT:"+grade_level+","+code+","+hierarchy+","+description+","+grade_level_id+");");
            statement2.execute();
            close();
    
    
    }
     
    
    
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement1 != null){
                statement1.close();
            }
            if (statement2 != null){
                statement2.close();
            }
            
        } catch (Exception e) {
            System.out.println();
        }
    }
    
}
