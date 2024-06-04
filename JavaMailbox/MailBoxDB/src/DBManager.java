import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    public void Start() throws ClassNotFoundException, SQLException
    {
        String userName = "root";
        String password = "root";

        String connectionUrl = "jdbc:mysql://localhost:3306/mbox";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement())
        {
            //Start connect
            System.out.println("We`re connected!");

            InsertDirectorySenderTable(statement, "DirectoryAddress", "MailAddress", "com.company.inc@gmail.com");
            //InsertAccountTable(statement,"account",new String[]{"FirstName", "LastName"}, new String[]{"KKK", "PPP"});
        }
        catch(SQLException e)
        {
            System.out.println("Server was unreachable");
        }
    }

    public void InsertDirectorySenderTable(Statement statement, String NameOfTable, String attribute, String data) throws SQLException {
        String query = "INSERT INTO " + NameOfTable +" (" + attribute +") VALUES ('"+data+"')";
        System.out.println(query);
        statement.executeUpdate(query);
    }

    public void InsertAccountTable(Statement statement, String NameOfTable, String[] attributes, String[] data) throws SQLException {

        //create a query generation
        String query = "INSERT INTO " + NameOfTable + "(";

        for(int i = 0; i < attributes.length; i++)
        {
            if(i != attributes.length -1) {
                query += attributes[i] + ",";
            }
            else if(i == attributes.length -1)
            {
                query += attributes[i]+")";
            }
        }
        query +=" VALUES (";

        for(int i = 0; i < data.length; i++)
        {
            if(i != data.length -1) {
                query += "'" + data[i] + "',";
            }
            else if(i == data.length -1)
            {
                query += "'" + data[i] + "')";
            }
        }
        System.out.println(query);

        statement.executeUpdate(query); // execute query
    }
}
