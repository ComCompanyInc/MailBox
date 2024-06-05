import java.sql.*;
import java.util.Arrays;

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


            /*
            RegistrationStarter(statement,
                    "DirectoryAddress",
                    "MailAddress",
                    "com.company.incii@gmail.com", //data1
                    "Account",
                    new String[]{"FirstName", "LastName", "DateOfRegistration", "Password", "IdDirectoryAddress"},
                    new String[]{"name1", "name2", "2004-04-04", "12333"} //data2
                    );

            SendMessage(statement,
                    "DirectoryAddress",
                    "Mail",
                    new String[]{"DescriptionMail", "DateOfSend", "IdDirectoryAddressOut", "IdDirectoryAddressIn"},
                    new String[]{"Привет, давно не виделись!", "2015-11-01"},
                    "com.company.inc@gmail.com",
                    "com.company.inc@gmail.com"
                    );
        */

            //Authorization(statement,"com.company.inc@gmail.com", "123");

            SelectionPeople(statement, "co");
        }
        catch(SQLException e)
        {
            System.out.println("Server was unreachable");
        }
    }

    public void InsertDirectorySenderTable(Statement statement,
                                           String NameOfTable,
                                           String attribute,
                                           String data) throws SQLException
    {
        String query = "INSERT INTO " + NameOfTable +" (" + attribute +") VALUES ('"+data+"')";
        System.out.println(query);
        statement.executeUpdate(query);
    }

    public void InsertUniversalTable(Statement statement,
                                     String NameOfTable,
                                     String[] attributes,
                                     String[] data,
                                     int[] idOther) throws SQLException
    {
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
                query += "'" + data[i] + "',";
        }

        for(int i = 0; i < idOther.length; i++)
        {
            if(i != idOther.length -1) {
                query += "'" + idOther[i] + "',";
            }
            else if(i == idOther.length -1)
            {
                query += "'" + idOther[i] + "')";
            }
        }

        System.out.println(query);

        statement.executeUpdate(query); // execute query
    }

    public void RegistrationStarter(Statement statement,
                                    String NameDir,
                                    String attributeDir,
                                    String dataDir,
                                    String NameOfTable,
                                    String[] attributes,
                                    String[] data) throws SQLException
    {
        //create the address post
        String address = dataDir;
        InsertDirectorySenderTable(statement, NameDir, attributeDir, dataDir);

        //create the account
        int[] idOther = new int[]
                {
                        SearchInt(statement,
                                NameDir,
                                "Id",
                                "MailAddress",
                                address,
                                "Id")
                };

        InsertUniversalTable(statement, NameOfTable, attributes, data, idOther);
    }

    public void SendMessage(Statement statement,
                            String NameDir,
                            String NameOfTable,
                            String[] attributes,
                            String[] data,
                            String senderAddress,
                            String receiverAddress
                            ) throws SQLException
    {
        int[] idOther = new int[2];

        idOther[0] =
                SearchInt(statement,
                        NameDir,
                        "Id",
                        "MailAddress",
                        senderAddress,
                        "Id");

        idOther[1] =
                SearchInt(statement,
                        NameDir,
                        "Id",
                        "MailAddress",
                        receiverAddress,
                        "Id");

        InsertUniversalTable(statement, NameOfTable, attributes, data, idOther);
    }

    public boolean Authorization(Statement statement, String address, String password) throws SQLException
    {
        int idVerification =
                        SearchInt(statement,
                                "DirectoryAddress",
                                "Id",
                                "MailAddress",
                                address,
                                "Id");

        String verificationPassword =
                SearchStr(statement,
                        "Account",
                        "Password",
                        "IdDirectoryAddress",
                        String.valueOf(idVerification),
                        "Password");

        if(password.equals(verificationPassword) == true)
        {
            System.out.println("Entry block successfully verified!");
            return true;
        }
        else if(password.equals(verificationPassword) != true)
        {
            System.out.println("Entry block not verified!");
            return false;
        }
        return false;
    }

    public String[] SelectionPeople (Statement statement, String requestAddress) throws SQLException {
        String[] people = new String[1000];

        ResultSet resultSet = statement.executeQuery("SELECT MailAddress FROM DirectoryAddress WHERE MailAddress LIKE '%"+requestAddress+"%' ORDER BY MailAddress;");
        int j = 0;
        while(resultSet.next())
        {
            people[j] = resultSet.getString("MailAddress");
            j++;
        }

        for(int i = 0; i < people.length; i++)
        {
            if(people[i] == null)
            {
                break;
            }

            System.out.println(people[i]);
        }

        return people;
    }


    // methods for search
    public String SearchStr(Statement statement, String NameOfTable, String Select, String ColumnWhere ,String result, String columnLabel) throws SQLException {
        String res = "";

        ResultSet resultSet = statement.executeQuery("SELECT "+Select+" FROM "+NameOfTable+" WHERE "+ColumnWhere+" = '" + result +"';");
        while(resultSet.next()) // bruteforce the data for known Id
        {
            res = resultSet.getString(columnLabel);
            System.out.println(res);
        }

        return res;
    }

    public int SearchInt(Statement statement, String NameOfTable, String Select, String ColumnWhere ,String result, String columnLabel) throws SQLException {
        int res = 0;

        ResultSet resultSet = statement.executeQuery("SELECT "+Select+" FROM "+NameOfTable+" WHERE "+ColumnWhere+" = '" + result +"';");
        while(resultSet.next()) // bruteforce the data for known Id
        {
            res = resultSet.getInt(columnLabel);
            System.out.println(res);
        }

        return res;
    }
}
