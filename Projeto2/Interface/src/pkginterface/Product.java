/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkginterface;

import java.io.Serializable;

/**
 *
 * @author Marco
 */
public class Product implements Serializable{
    
    private static final long serialVersionUID = -1208949373134085399L;
    
    private String product_code;
    private String description;
    private int quantity;
    private String price;  /*multiplicar por 100 qdo vem da BD. Dividir por 100 quando vai para a BD*/
    
    public Product(String product_code, String description, int quantity, String price){
        this.product_code = product_code;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
    
    public Product(String product_code, String description, String price){
        this.product_code = product_code;
        this.description = description;
        this.quantity = 1;
        this.price = price;
    }

    /**
     * @return the product_code
     */
    public String getProduct_code() {
        return product_code;
    }

    /**
     * @param product_code the product_code to set
     */
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }
    
    
}