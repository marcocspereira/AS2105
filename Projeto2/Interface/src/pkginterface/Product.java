/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkginterface;

/**
 *
 * @author Marco
 */
public class Product {
    private String product_code;
    private String description;
    private int quantity;
    private float price;  /*multiplicar por 100 qdo vem da BD. Dividir por 100 quando vai para a BD*/
    
    public Product(String product_code, String description, int quantity, float price){
        this.product_code = product_code;
        this.description = description;
        this.quantity = quantity;
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
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }
    
    
}
