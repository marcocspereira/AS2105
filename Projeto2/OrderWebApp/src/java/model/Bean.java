/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GonçaloSilva
 */
public class Bean {
    
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String INVENTORY_DB_URL = "jdbc:mysql://localhost/inventory";
   static final String ORDERINFO_DB_URL = "jdbc:mysql://localhost/orderinfo";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
    
    static Statement statement_orderinfo;
    static Statement statement_inventory;
    static Connection connection_orderinfo;
    static Connection connection_inventory;
    
    private String loginUser;
    private String loginPass;
    
    private String registName;
    private String registEmail;
    private String registUser;
    private String registPass;
    private String registPass2;

    public Bean() {
       try {
           
           Class.forName("com.mysql.jdbc.Driver");
           
           connection_orderinfo = DriverManager.getConnection(ORDERINFO_DB_URL, USER, PASS);
           statement_orderinfo = connection_orderinfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
           connection_inventory = DriverManager.getConnection(INVENTORY_DB_URL, USER, PASS);
           statement_inventory = connection_inventory.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
           
           
       } catch (SQLException ex) {
           Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getRegistName() {
        return registName;
    }

    public void setRegistName(String registName) {
        this.registName = registName;
    }

    public String getRegistEmail() {
        return registEmail;
    }

    public void setRegistEmail(String registEmail) {
        this.registEmail = registEmail;
    }

    public String getRegistUser() {
        return registUser;
    }

    public void setRegistUser(String registUser) {
        this.registUser = registUser;
    }

    public String getRegistPass() {
        return registPass;
    }

    public void setRegistPass(String registPass) {
        this.registPass = registPass;
    }

    public String getRegistPass2() {
        return registPass2;
    }

    public void setRegistPass2(String registPass2) {
        this.registPass2 = registPass2;
    }
    
    private String md5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            BigInteger i = new BigInteger(1,m.digest());
            return String.format("%1$032x", i);         
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int doLogin() throws SQLException {
        String query = "Select * from " + CMD.usersTable + " where username ='" + loginUser + "'";
        System.out.println(query);
        
        try {
            
        Class.forName("com.mysql.jdbc.Driver");
           connection_orderinfo = DriverManager.getConnection(ORDERINFO_DB_URL, USER, PASS);
           statement_orderinfo = connection_orderinfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
       } catch (SQLException ex) {
           Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        ResultSet result = statement_orderinfo.executeQuery(query);

        if (!result.next()) {
            return CMD.ERROR;
        }

        String password = result.getString("password");
        
//        System.out.println(password+" " + md5(loginPass));
        if (password.equalsIgnoreCase(md5(loginPass))) {
            
            System.out.println("sucesso no login");

            int clientId = result.getInt("id");
            System.out.println("clientId " + clientId);

            //nao funciona
//                PreparedStatement ps = connection.prepareStatement("Update " + CMD.usersTable + " set \"ONLINE\" = 1 where userid = " + clientId);
//                ps.execute();
//                ps.close();
            result.close();
            return clientId;
        }

        return CMD.ERROR;
    }

    public int doLogout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
