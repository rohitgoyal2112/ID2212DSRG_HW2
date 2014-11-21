/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreserver;
/**
 *
 * @author rohitgoyal
 */
public class Item {
    int customerId;
    String name;
    double price;
    public Item(int customerId,String name,double price){
        this.customerId=customerId;
        this.name=name;
        this.price=price;      
    }
    public int getCustomerId(){
        return customerId;
    }
    
    public String getName(){
        return name;
    }
}
