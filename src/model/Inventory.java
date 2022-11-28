package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Inventory class that stores all the data for parts and products. */
public class Inventory {

    /**  This method populates parts in an observable list. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** This method adds a part to the parts list.
     @param part adds part */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /** This method gets all parts.
     @return all parts */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** This method searches for matching ID that the user inputs in a search bar.
     @param partId looks up part wiht matching partId
     @return results of searched part */
    public static Part lookupPart (int partId) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId()==(partId)) {
                return part;
            }
        }
        return null;
    }

    /** This method searches for matching strings that a user inputs in a search bar.
     @param partName looks up part with matching partName
     @return results of searched part*/
    public static ObservableList<Part> lookupPart (String partName) {
        ObservableList<Part> partResults = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part part : allParts) {
            if (part.getName().contains(partName) || String.valueOf(part.getId()).contains(partName)) {
                partResults.add(part);
            }
        }
        return partResults;
    }

    /** This method updates the part when modified.
     @param index finds an index of selected part
     @param selectedPart  finds selected part  */
    public static void updatePart (int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /** This method deletes a part.
     @param selectedPart deletes the selected part
     @return true when part is deleted and false when part is not deleted */
    public static boolean deletePart (Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**This method populates products in an observable list. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method adds a product to the product list.
     @param product adds product */
    public static void addProduct(Product product){
        allProducts.add(product);}

    /**This method gets all products.
     @return all products */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** This method searches for matching ID that the user inputs in a search bar.
     @param productId looks up product with matching productId
     @return results of searched product */
    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** This method searches for matching strings that a user inputs in a search bar.
     @param productName looks up part with matching productName
     @return results of searched product */
    public static ObservableList<Product> lookupProduct (String productName){
            ObservableList<Product> productResults = FXCollections.observableArrayList();
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            for (Product product : allProducts) {
                if (product.getName().contains(productName) || String.valueOf(product.getId()).contains(productName)) {
                    productResults.add(product);
                }
            }
            return productResults;
        }

    /** This method updates the product when modified.
     @param index finds an index of selected product
     @param selectedProduct  finds selected product */
    public static void updateProduct (int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }

    /** This method deletes a product.
     @param selectedProduct deletes the selected product
     @return true when product is deleted and false when product is not deleted */
    public static boolean deleteProduct (Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

}
