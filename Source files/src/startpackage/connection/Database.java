package startpackage.connection;

import java.sql.*;

public class Database {
    private String connectionURL;
    private Connection connection;
    private Statement statement;
    private ResultSet results;
    public Database(String database){
        connectionURL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName="+database+";integratedSecurity=true;";
        connection = null;
        statement = null;
        results = null;
    }
    public ResultSet run(String SQL){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionURL);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            results = statement.executeQuery(SQL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }
}
