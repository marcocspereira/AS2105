/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as.model;

import com.opensymphony.xwork2.Preparable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import pkginterface.Product;
import pkginterface.RMIRemote;

/**
 *
 * @author GonçaloSilva
 */
public class Bean {

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
    
    private String product_code;
    private String product_name;
    private int product_quantity;
    private float product_price; 

    private ArrayList<Product> trees = new ArrayList<Product>();
    private ArrayList<Product> seeds = new ArrayList<Product>();
    private ArrayList<Product> shrubs = new ArrayList<Product>();

    private ArrayList<Product> checkList = new ArrayList<Product>();

    ObjectInputStream in;
    ObjectOutputStream out;
    RMIRemote server = null;

    Socket clientSocket;
    int threadNumber;
    Boolean online = false;
    int clientId = 0;

    String RMI = "";

    public void init() {
        if (RMI.compareTo("") == 0) {
            Properties prop = new Properties();
            try {
                prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
                //            prop.load(new FileInputStream("config.properties"config));
                RMI = prop.getProperty("RMI");
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (server == null) {
            try {
                try {
                    System.getProperties().put("java.security.policy", "policy.all");
                    server = (RMIRemote) Naming.lookup("rmi://" + RMI + "/server");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (AccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (NotBoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

//    Preparable.prepare() {
////        try {
//        Properties prop = new Properties();
//        try {
//            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
////            prop.load(new FileInputStream("config.properties"config));
//            RMI = prop.getProperty("RMI");
//        } catch (FileNotFoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        try {
//
//            try {
//                System.getProperties().put("java.security.policy", "policy.all");
//                server = (RMIRemote) Naming.lookup("rmi://" + RMI + "/server");
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        } catch (AccessException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } catch (RemoteException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } catch (NotBoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        try{
//            clientSocket = aClientSocket;
//            out = new ObjectOutputStream(clientSocket.getOutputStream());
//            in = new ObjectInputStream(clientSocket.getInputStream());
//            this.start();
//        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
//        try {
//            Properties prop = new Properties();
//            prop.load(new FileInputStream("config.properties"));
//            host = prop.getProperty("RMI");
//        } catch (IOException ex) {
//            Logger.getLogger(DirestrutsBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
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
    
    public void setProductCode(String product_code){
        this.product_code=product_code;
    }
    
    public String getProductCode(){
        return product_code;
    }
    
    public void setProductName(String product_name){
        this.product_name=product_name;
    }
    
    public String getProductName(){
        return product_name;
    }
    
    public void setProductPrice(float product_price){
        this.product_price=product_price;
    }
    
    public float getProductPrice(){
        return product_price;
    }
    
    public void setProductQuantity(int product_quantity){
        this.product_quantity=product_quantity;
    }
    
    public float getProductPQuantity(){
        return product_quantity;
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

//    private String md5(String s) {
//        try {
//            MessageDigest m = MessageDigest.getInstance("MD5");
//            m.update(s.getBytes(), 0, s.length());
//            BigInteger i = new BigInteger(1, m.digest());
//            return String.format("%1$032x", i);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
    public int doLogin() throws RemoteException, SQLException {
//        main();
        init();
        return server.doLogin(loginUser, loginPass);
    }
//    public int doLogin() throws SQLException {
//        String query = "Select * from " + CMD.usersTable + " where username ='" + loginUser + "'";
//        System.out.println(query);
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection_orderinfo = DriverManager.getConnection(ORDERINFO_DB_URL, USER, PASS);
//            statement_orderinfo = connection_orderinfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        } catch (SQLException ex) {
//            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ResultSet result = statement_orderinfo.executeQuery(query);
//
//        if (!result.next()) {
//            return CMD.ERROR;
//        }
//
//        String password = result.getString("password");
//
//        if (password.equalsIgnoreCase(md5(loginPass))) {
//
//            System.out.println("sucesso no login");
//
//            int clientId = result.getInt("id");
//            System.out.println("clientId " + clientId);
//
//            result.close();
//            return clientId;
//        }
//        return CMD.ERROR;
//    }

    public int doLogout() throws RemoteException {
        init();
        return server.doLogout();
//        return CMD.OK;
    }

    public int doRegist() throws RemoteException {
        init();
        return server.doRegist(registUser, registEmail, registPass, registFirstName, registLastName, registAddress, registPhone);
    }
//    public int doRegist() {
//        String query = "Select username from " + CMD.usersTable + " where username ='" + registUser + "'";
//        System.out.println(query);
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection_orderinfo = DriverManager.getConnection(ORDERINFO_DB_URL, USER, PASS);
//            statement_orderinfo = connection_orderinfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        } catch (SQLException ex) {
//            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        try {
//            ResultSet result = statement_orderinfo.executeQuery(query);
//
//            if (!result.next()) {
//
//                PreparedStatement pst = connection_orderinfo.prepareStatement("Insert into " + CMD.usersTable
////                        + " (name,username,email,password,deicoins,\"ONLINE\")"
//                        + "(username, email, password, first_name, last_name, address, phone)"
//                        + " values(?,?,?,?,?,?,?)");
//
//                pst.setString(1, registUser);
//                pst.setString(2, registEmail);
//                pst.setString(3, registPass);
//                pst.setString(4, registFirstName);
//                pst.setString(5, registLastName);
//                pst.setString(6, registAddress);
//                pst.setString(7, registPhone);
//                pst.executeUpdate();
//
//                result.close();
//                pst.close();
//                return CMD.OK;
//            } else {
//                result.close();
//                return CMD.ERROR;
//            }
//
//        } catch (SQLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        return CMD.ERROR;
//    }
//    
//    public ArrayList<Product> returnProduts(String table) {
//        String query = "Select * from " + table;
//        ResultSet result = null;
//        ArrayList<Product> products = new ArrayList<>();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection_inventory = DriverManager.getConnection(INVENTORY_DB_URL, USER, PASS);
//            statement_inventory = connection_inventory.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            result = statement_inventory.executeQuery(query);
//            
//            while (result.next())
//            {
////                TODO Correct ParseFloat to last parameter
//                Product product = new Product(result.getString(1), result.getString(2), Integer.parseInt(result.getString(3)), Float.parseFloat(result.getString(4)));
//                products.add(product);
//            } // while
//                    
//        } catch (SQLException ex) {
//            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return products;
//    }

    public int doLoadProducts() throws RemoteException{
        init();
        trees = server.returnProduts(CMD.treesTable);
        seeds = server.returnProduts(CMD.seedsTable);
        shrubs = server.returnProduts(CMD.shrubsTable);
        
        System.out.println(trees.size());
        System.out.println(seeds.size());
        System.out.println(shrubs.size());
        
        return CMD.OK;
    }
    
    
     public int DoOrder(String products){
        String outdelim = "[;]";
        String innerdelim = "[|]";
        String[] outString = products.split(outdelim);
        String[] innerString;
        int i = 0;
       
        for(String temp : outString){            
            innerString = temp.split(innerdelim);
            
            for(String token : innerString){           
                if(i == 0){
                    token = token.replaceAll("\\s","");
                    product_code=token;
                }

                if(i == 1){
                    product_name=token;
                }

                if(i == 2){
                    token = token.replaceAll("\\s","");
                    product_quantity = Integer.parseInt(token);
                }

                if(i == 3){
                    token = token.replaceAll("\\s","");
                    product_price=Float.valueOf(token);
                }
                i++;
            }
            i=0;
        }

        return CMD.OK;
    }
    
    
    
}

//Thread para tratar de cada canal de comunicacao com um cliente
class Connection extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out;
    RMIRemote server;

    Socket clientSocket;
    int threadNumber;
    Boolean online = false;
    int clientId = 0;

    public Connection(Socket aClientSocket, int numero) {

        threadNumber = numero;
        String RMI = "";

//        try {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config.properties"));
            RMI = prop.getProperty("RMI");
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {

            try {
                System.getProperties().put("java.security.policy", "policy.all");
                server = (RMIRemote) Naming.lookup("rmi://" + RMI + "/ideabroker");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (AccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (NotBoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            clientSocket = aClientSocket;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    private void exitThread() throws IOException {
//    	System.out.println("T["+threadNumber + "] Recebeu: "+CMD.mainMenu.EXIT.toString());
        in.close();
        out.close();
        this.clientSocket.close();
        return;
    }

//    public void run(){
//        Object received = null;
//        try{
//            while(clientSocket != null){
//            	if(!online)
//            	{
//        			online = false;
//            		received = in.readObject();
//	            	if (received == null)
//	            	{
//	            		System.out.println("Client disconnected");
//	            		break;
//	            	}
//
//	            	int option = (Integer) received;
//	            	if (option == CMD.mainMenu.LOGIN.ordinal()){
//	            		login();
//	            	}
//	            	else if (option == CMD.mainMenu.REGISTER.ordinal()){
//	            		register();
//	                }
//	            	else if (option == CMD.mainMenu.EXIT.ordinal()){
//	            		exitThread();
//	            		System.out.println("T["+threadNumber + "] Recebeu: "+CMD.mainMenu.EXIT.toString()+" OK");
//            			break;
//	            	}
//            	}
//            	else
//            	{
//            		int deiCoins = h.deiCoins(clientId);
//            		out.writeObject(deiCoins);
//
//                	received = in.readObject();
//                	if (received == null)
//                	{
//                		System.out.println("Client disconnected");
//                		online = false;
//                		break;
//                	}
//
//                	int option = (Integer) received;
//
//            		if(clientId == CMD.ROOT){
//            			showSecundaryRootMenu(option);
//            		}
//
//            		else{
//                    	showSecundaryClientMenu(option);
//            		}
//
//
//            	}
//            }
//        }catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			System.out.println(e.getCause());
//			System.out.println(e.getMessage());
//			online = false;
//		}
//    }
}
