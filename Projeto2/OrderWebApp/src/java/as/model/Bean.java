/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

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

    private String registEmail;
    private String registUser;
    private String registPass;
    private String registPass2;
    private String registFirstName;
    private String registLastName;
    private String registAddress;
    private String registPhone;
    
    private ArrayList<Product> trees = new ArrayList<Product>();
    private ArrayList<Product> seeds = new ArrayList<Product>();
    private ArrayList<Product> shrubs = new ArrayList<Product>();
    

    @PostConstruct
    void initialize() {
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
//        try {
//            Properties prop = new Properties();
//            prop.load(new FileInputStream("config.properties"));
//            host = prop.getProperty("RMI");
//        } catch (IOException ex) {
//            Logger.getLogger(DirestrutsBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
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

    public static Connection getConnection_orderinfo() {
        return connection_orderinfo;
    }

    public static void setConnection_orderinfo(Connection connection_orderinfo) {
        Bean.connection_orderinfo = connection_orderinfo;
    }

    public String getRegistFirstName() {
        return registFirstName;
    }

    public void setRegistFirstName(String registFirstName) {
        this.registFirstName = registFirstName;
    }

    public String getRegistLastName() {
        return registLastName;
    }

    public void setRegistLastName(String registLastName) {
        this.registLastName = registLastName;
    }

    public String getRegistAddress() {
        return registAddress;
    }

    public void setRegistAddress(String registAddress) {
        this.registAddress = registAddress;
    }

    public String getRegistPhone() {
        return registPhone;
    }

    public void setRegistPhone(String registPhone) {
        this.registPhone = registPhone;
    }
    
    public ArrayList<Product> getTrees() {
        return trees;
    }

    public ArrayList<Product> getSeeds() {
        return seeds;
    }

    public ArrayList<Product> getShrubs() {
        return shrubs;
    }

    public void setTrees(ArrayList<Product> trees) {
        this.trees = trees;
    }

    public void setSeeds(ArrayList<Product> seeds) {
        this.seeds = seeds;
    }

    public void setShrubs(ArrayList<Product> shrubs) {
        this.shrubs = shrubs;
    }

    private String md5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            BigInteger i = new BigInteger(1, m.digest());
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

        if (password.equalsIgnoreCase(md5(loginPass))) {

            System.out.println("sucesso no login");

            int clientId = result.getInt("id");
            System.out.println("clientId " + clientId);

            result.close();
            return clientId;
        }
        return CMD.ERROR;
    }

    public int doLogout() {
        return CMD.OK;
    }
    
    public int doRegist() {
        String query = "Select username from " + CMD.usersTable + " where username ='" + registUser + "'";
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
        
        try {
            ResultSet result = statement_orderinfo.executeQuery(query);

            if (!result.next()) {

                PreparedStatement pst = connection_orderinfo.prepareStatement("Insert into " + CMD.usersTable
//                        + " (name,username,email,password,deicoins,\"ONLINE\")"
                        + "(username, email, password, first_name, last_name, address, phone)"
                        + " values(?,?,?,?,?,?,?)");

                pst.setString(1, registUser);
                pst.setString(2, registEmail);
                pst.setString(3, registPass);
                pst.setString(4, registFirstName);
                pst.setString(5, registLastName);
                pst.setString(6, registAddress);
                pst.setString(7, registPhone);
                pst.executeUpdate();

                result.close();
                pst.close();
                return CMD.OK;
            } else {
                result.close();
                return CMD.ERROR;
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return CMD.ERROR;
    }
    
    public ArrayList<Product> returnProduts(String table) {
        String query = "Select * from " + table;
        ResultSet result = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection_inventory = DriverManager.getConnection(INVENTORY_DB_URL, USER, PASS);
            statement_inventory = connection_inventory.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result = statement_inventory.executeQuery(query);
            
            while (result.next())
            {
//                TODO Correct ParseFloat to last parameter
                Product product = new Product(result.getString(1), result.getString(2), Integer.parseInt(result.getString(3)), Float.parseFloat(result.getString(4)));
                products.add(product);
            } // while
                    
        } catch (SQLException ex) {
            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public int doLoadProducts(){
        trees = returnProduts(CMD.treesTable);
        seeds = returnProduts(CMD.seedsTable);
        shrubs = returnProduts(CMD.shrubsTable);
        
        System.out.println(trees.size());
        System.out.println(seeds.size());
        System.out.println(shrubs.size());
        
        return CMD.OK;
    }
}
