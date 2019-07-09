package almaimporting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.regex.Pattern;


public class MySQL_Query_Controller {
    
    private Connection connection = null;
    private Statement statement1 = null;
    private PreparedStatement statement2 = null;
    private ResultSet resultSet = null;
    private String strand = "";
    public int id = 0;
    public int standardNumber = 0;
    public String grade_level = "";
    public String code = "";
    public String hierarchy = "";
    public String description = "";
    public String grade_level_id = "";
    public boolean last_record = false;
    
    public MySQL_Query_Controller(Connection connection) {
        this.connection = connection;
    }
    
    public void readData(int i) throws Exception {
        
        try {
            statement1 = connection.createStatement();
            String query = "select * "
                            + "from Proficiencies_Virtudes "
                            + "where id = "+i+";";
            resultSet = statement1.executeQuery(query);
            this.getResults(resultSet);
        } catch (Exception e) {
            //System.out.println("Error:" + e.getMessage());
        } finally {
           //System.out.println("Record "+i+" read");
        }
       
    }
    
     private void getResults(ResultSet resultSet) throws SQLException {
         //System.out.println(resultSet.getFetchSize());
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
                
        
        String [] harray = hierarchy.split(Pattern.quote("::"));
        String thisstrand = harray[2];
        //String thisstrand = harray[3];
        if(!last_record){
        
        for(int i = 0; i < harray.length; i++){
            if(!strand.equals(thisstrand) && !last_record){
                this.insertResults(grade_level,code,harray[0],harray[0],grade_level_id);//Rochester
                String a = harray[0]+"::"+harray[1];
                this.insertResults(grade_level,code,a,harray[1],grade_level_id);//Rochester::Subject
                String b = harray[0]+"::"+harray[1]+"::"+harray[2];
                this.insertResults(grade_level,code,b,harray[2],grade_level_id);//Rochester::Subject::Strand::
                //System.out.println(harray[2]+"::"+String.valueOf(i));
                
                //For another hierarchy level.
                //String c = harray[0]+"::"+harray[1]+"::"+harray[2]+"::"+harray[3];//Rochester::Subject::Stand::Substrand::
                //this.insertResults(grade_level,code,c,harray[3],grade_level_id);
                strand = thisstrand;
                standardNumber=0;
            }
        }
        standardNumber++;
        }
        
        String standard = harray[0]+"::"+harray[1]+"::"+harray[2]+"::"+standardNumber;
        //String standard = harray[0]+"::"+harray[1]+"::"+harray[2]+"::"+harray[3]+"::"+standardNumber;
        this.insertResults(grade_level,code,standard,harray[3],grade_level_id);//Rochester::Subject::Strand::Standard
        
    }
     
    public void insertResults(String a, String b, String c, String d, String e) throws SQLException{
    String query2 = "INSERT INTO Proficiencies_final_Virtudes(grade_level,code,hierarchy,description,grade_level_id)"
                            +"values (?, ?, ?, ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setString(1,a);
            statement2.setString(2,b);
            statement2.setString(3,c);
            statement2.setString(4,d);
            statement2.setString(5,e);
            
            //System.out.println("WILL INSERT:"+a+","+b+","+c+","+d+","+e+");");
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
            //System.out.println();
        }
    }
    
}
