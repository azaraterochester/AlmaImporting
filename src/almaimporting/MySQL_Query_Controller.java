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
                            + "where id = 2;";
            resultSet = statement1.executeQuery(query);
            this.insertResults(resultSet);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        } finally {
           
        }
    }
    
     private void insertResults(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            grade_level = resultSet.getString("grade_level");
            code = resultSet.getString("code");
            hierarchy = resultSet.getString("hierarchy");
            description = resultSet.getString("description");
            grade_level_id = resultSet.getString("grade_level_id");
            System.out.printf("%nid %d grade_level: %s code: %s hierarchy: %s description: %s grade_level_id: %s", id, grade_level, code, hierarchy, description, grade_level_id);
            
            String query2 = "INSERT INTO Proficiencies_Final(grade_level,code,hierarchy,description,grade_level_id)"
                            +"values (?, ?, ?, ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setString(1,grade_level);
            statement2.setString(2,code);
            statement2.setString(3,hierarchy);
            statement2.setString(4,description);
            statement2.setString(5,grade_level_id);
            
            System.out.println("WILL INSERT:"+grade_level+","+code+","+hierarchy+","+description+","+grade_level_id+");");
            statement2.execute();
       
        }
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
