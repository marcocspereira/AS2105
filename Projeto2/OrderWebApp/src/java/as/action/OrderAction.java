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
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import pkginterface.Product;

/**
 *
 * @author GonçaloSilva
 */
public class OrderAction extends ActionSupport implements SessionAware {

    //get log4j
    private static final Logger logger = Logger.getLogger(OrderAction.class);
        
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    @Override
    public void validate() {
        if (getBean() != null && getBean().getOrderFirstName()!= null && getBean().getOrderFirstName().length() == 0) {
            addFieldError("bean.orderFirstName", getText("orderFirstName.required"));
        }
        if (getBean() != null && getBean().getOrderLastName()!= null && getBean().getOrderLastName().length() == 0) {
            addFieldError("bean.orderLastName", getText("orderLastName.required"));
        }
        if (getBean() != null && getBean().getOrderAddress()!= null && getBean().getOrderAddress().length() == 0) {
            addFieldError("bean.orderAddress", getText("orderAddress.required"));
        }
        if (getBean() != null && getBean().getOrderPhoneNumber()!= null && getBean().getOrderPhoneNumber().length() == 0) {
            addFieldError("bean.orderPhoneNumber", getText("orderPhoneNumber.required"));
        }
        if (getBean() != null && getBean().getOrderCart()!= null && getBean().getOrderCart().length() == 0) {
            addFieldError("bean.orderCart", getText("orderCart.required"));
        }

    }
    
    @Override
    public String execute() throws Exception {
        if (getBean() == null) {
            addActionError(getText("login.expire"));
            return "login";
        }
        int result = 0;
        if((result = getBean().doWebOrders()) > 0){
            getBean().doLoadProducts();
            logger.info("Order by "+getBean().getLoginUser()+" with ID="+result+".");
            getBean().setCheckList(new ArrayList<Product>());
            getBean().setOrderCart("");
            getBean().setOrderFirstName("");
            getBean().setOrderLastName("");
            getBean().setOrderAddress("");
            getBean().setOrderPhoneNumber("");
            addActionMessage(getText("order.ok"));
            return SUCCESS;
        }            
        logger.error("Order by "+getBean().getLoginUser()+" fail.");
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
