/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkginterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author GonçaloSilva
 */
public interface RMIRemote extends Remote{
    int doLogin(String loginUser, String loginPass) throws  RemoteException, SQLException;
    int doRegist(String registUser, String registEmail, String registPass, String registFirstName, String registLastName, String registAddress, String registPhone)throws RemoteException;
    ArrayList<Product> returnProduts(String table)throws RemoteException;
    
}