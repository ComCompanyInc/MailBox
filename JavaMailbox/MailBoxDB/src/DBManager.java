import java.sql.*;

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

            //InsertDirectorySenderTable(statement, "DirectoryAddress", "MailAddress", "com.company.inc@gmail.com");
            //InsertAccountTable(statement,"account",new String[]{"FirstName", "LastName"}, new String[]{"KKK", "PPP"});


            RegistrationStarter(statement,
                    "DirectoryAddress",
                    "MailAddress",
                    "com.company.inc@gmail.com", //data1
                    "Account",
                    new String[]{"FirstName", "LastName", "DateOfRegistration", "Password", "IdDirectoryAddress"},
                    new String[]{"name1", "name2", "2004-04-04", "123"} //data2
                    );


            SendMessage(statement,
                    "DirectoryAddress",
                    "Mail",
                    new String[]{"DescriptionMail", "DateOfSend", "IdDirectoryAddressOut", "IdDirectoryAddressIn"},
                    new String[]{"Привет, давно не виделись!", "2015-11-01"},
                    "com.company.inc@gmail.com",
                    "com.company.inc@gmail.com"
                    );
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
            //if(i != data.length -1) {
                query += "'" + data[i] + "',";
            //}
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
        ResultSet idAccountAddress = statement.executeQuery("SELECT Id FROM "+NameDir+" WHERE MailAddress = '" + address+"';");

        int[] idOther = new int[0];
        while(idAccountAddress.next()) // bruteforce the data for known Id
        {
            idOther = new int[]{(idAccountAddress.getInt("Id"))};
            System.out.println(idOther[0]);
        }

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


        ResultSet idAccountAddress = statement.executeQuery("SELECT Id FROM "+NameDir+" WHERE MailAddress = '" + senderAddress+"';");

        while(idAccountAddress.next()) // bruteforce the data for known Id
        {
            idOther[0] = idAccountAddress.getInt("Id");
            System.out.println(idOther[0]);
        }

        ResultSet idReciverAccountAddress = statement.executeQuery("SELECT Id FROM "+NameDir+" WHERE MailAddress = '" + receiverAddress+"';");

        while(idReciverAccountAddress.next()) // bruteforce the data for known Id
        {
            idOther[1] = idReciverAccountAddress.getInt("Id");
            System.out.println(idOther[1]);
        }

        InsertUniversalTable(statement, NameOfTable, attributes, data, idOther);
    }
}
