package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Product class that stores all the data for products and associated parts. */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     @return the product id
    */
    public int getId() {
        return id;
    }

    /**
    @param id the id to be set
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     @return product name
     */
    public String getName() {
        return name;
    }

    /**
     @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     @return the price of product
     */
    public double getPrice() {
        return price;
    }

    /**
     @param price the price to be set
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     @return stock of product
     */
    public int getStock() {
        return stock;
    }

    /**
     @param stock the stock to be set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     @return the minimum allowed in inventory
     */
    public int getMin() {
        return min;
    }

    /**
     @param min the min to be set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     @return the maximum allowed in inventory
     */
    public int getMax() {
        return max;
    }

    /**
     @param max the max to be set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds associated parts to a product.
     @param part adds associated part */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** This method deletes associated part of a product.
     @param selectedAssociatedPart selects an associated part to be deleted
     @return true when associated part is deleted and false when it is not deleted*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (selectedAssociatedPart == associatedParts) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /** This method gets all associated parts.
     @return associated parts for a product */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
