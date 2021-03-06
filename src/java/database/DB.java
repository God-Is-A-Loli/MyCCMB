package database;

import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */
public class DB {
//    private static final String driver = "com.mysql.cj.jdbc.Driver";
//    private static final String user = "root";
//    private static final String pswd= "12345678";
//    private static final String dbURL = "jdbc:mysql://localhost/users?useLegacyDatetimeCode=false&serverTimezone=Australia/Melbourne";
    private static final String connectionUrl = "jdbc:sqlserver://SQLTST.cancercare.mb.ca\\SQL2;" +
			"databaseName=Referral;integratedSecurity=false;";
    private static final String user = "skunkworks";
    private static final String password = "agp4skunk!";
    private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
    private Connection conn;
    
    public DB() throws SQLException {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connectionUrl, user, password);
        } catch(ClassNotFoundException e) {
            System.err.println("driver error: " + e.getMessage());
        }
    }
    
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement smt = conn.createStatement();
        return smt.executeQuery(sql);
    }
    
    public int executeUpdate(String sql) throws SQLException {
        Statement smt = conn.createStatement();
        return smt.executeUpdate(sql);
    }
    
    public void close() throws SQLException {
        conn.close();
    }
}
