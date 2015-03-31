/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import pkginterface.CMD;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkginterface.Product;
import pkginterface.RMIRemote;

/**
 *
 * @author GonçaloSilva
 */
public class Server extends UnicastRemoteObject implements RMIRemote, Serializable {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static Statement statement_orderinfo;
    static Connection connection_orderinfo;
    
    static Statement statement_inventory;
    static Connection connection_inventory;
    
    static Statement statement_users;
    static Connection connection_users;
  
    private ArrayList<Product> trees = new ArrayList<Product>();
    private ArrayList<Product> seeds = new ArrayList<Product>();
    private ArrayList<Product> shrubs = new ArrayList<Product>();
    
    public Server() throws RemoteException {
        super();
    }
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        Properties prop = new Properties();

        //Load do ficheiro config.properties
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        //Ligacao a BD
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            String dbHostInventory = prop.getProperty("INVENTORY_DB_URL");
            String userInventory = prop.getProperty("USER_INVENTORY_DB");
            String passInventory = prop.getProperty("PASS_INVENTORY_DB");
            connection_inventory = DriverManager.getConnection(dbHostInventory, userInventory, passInventory);
            statement_inventory = connection_inventory.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          
            System.out.println("Ligado a BD Inventory com sucesso");
            
            String dbHostOrderInfo = prop.getProperty("ORDERINFO_DB_URL");
            String userOrderInfo = prop.getProperty("USER_ORDERINFO_DB");
            String passOrderInfo = prop.getProperty("PASS_ORDERINFO_DB");
            connection_orderinfo = DriverManager.getConnection(dbHostOrderInfo, userOrderInfo, passOrderInfo);
            statement_orderinfo = connection_orderinfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            System.out.println("Ligado a BD OrderInfo com sucesso");
            
            String dbHostUsers = prop.getProperty("USERS_DB_URL");
            String userUsers = prop.getProperty("USER_USERS_DB");
            String passUsers = prop.getProperty("PASS_USERS_DB");
            connection_users = DriverManager.getConnection(dbHostUsers, userUsers, passUsers);
            statement_users = connection_users.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            System.out.println("Ligado a BD Users com sucesso");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //Iniciar o servidor RMI
        try {
            System.getProperties().put("java.security.policy", "policy.all");
            Server s = new Server();
            Registry r = LocateRegistry.createRegistry(7000);
            r.rebind("server", s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    
    public int doLogin(String loginUser, String loginPass) throws RemoteException, SQLException {
        String query = "Select * from " + CMD.usersTable + " where username ='" + loginUser + "'";
        System.out.println(query);

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

    public int doLogout() throws RemoteException{
        return CMD.OK;
    }
    
    public int doRegist(String registUser, String registEmail, String registPass, String registFirstName, String registLastName, String registAddress, String registPhone) throws RemoteException {
        String query = "Select username from " + CMD.usersTable + " where username ='" + registUser + "'";
        System.out.println(query);

        try {
            ResultSet result = statement_users.executeQuery(query);

            if (!result.next()) {

                PreparedStatement pst = connection_users.prepareStatement("Insert into " + CMD.usersTable
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
                   
                System.out.println(pst);
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
    
    public ArrayList<Product> returnProduts(String table) throws RemoteException {
        String query = "Select * from " + table;
        ResultSet result = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            result = statement_inventory.executeQuery(query);
            while (result.next())
            {
    //                TODO Correct ParseFloat to last parameter
                Product product = new Product(result.getString(1), result.getString(2), Integer.parseInt(result.getString(3)), Float.parseFloat(result.getString(4)));
                products.add(product);
            } // while
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public int doLoadProducts() throws RemoteException{
        trees = returnProduts(CMD.treesTable);
        seeds = returnProduts(CMD.seedsTable);
        shrubs = returnProduts(CMD.shrubsTable);
        
        System.out.println(trees.size());
        System.out.println(seeds.size());
        System.out.println(shrubs.size());
        
        return CMD.OK;
    }
}
