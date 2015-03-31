/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as.action;

import as.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import pkginterface.CMD;
import pkginterface.Product;

/**
 *
 * @author GonçaloSilva
 */
public class OrderAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        /*
        System.out.println(getBean().getOrderFirstName());
        System.out.println(getBean().getOrderLastName());
        System.out.println(getBean().getOrderAddress());
        System.out.println(getBean().getOrderPhoneNumber());
        getBean().doOrder(getBean().getOrderCart());
        */
        
        if (!getBean().getCheckList().isEmpty() &&
        getBean().getOrderFirstName()!= null &&
        getBean().getOrderLastName()!= null &&
        getBean().getOrderAddress() != null &&
        getBean().getOrderPhoneNumber() != null)        
        {
            if(getBean().doWebOrders() == CMD.OK){
                getBean().setCheckList(new ArrayList<Product>());
                getBean().setOrderCart("");
                getBean().setOrderFirstName("");
                getBean().setOrderLastName("");
                getBean().setOrderAddress("");
                getBean().setOrderPhoneNumber("");
                getBean().setOrderTotalCost(0);
            }            
        }        
        
        if (getBean() == null) {
            addActionError(getText("login.expire"));
            return "login";
        }
        if (getBean().doLoadProducts()> 0) {
            addActionError(getText("order.ok"));
            return SUCCESS;
        }

        addActionError(getText("order.error"));
        return ERROR;
    }

    public Bean getBean() {
        return (Bean) session.get("bean");
    }

    public void setBean(Bean bean) {
        this.session.put("bean", bean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
