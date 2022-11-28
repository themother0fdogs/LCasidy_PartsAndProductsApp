package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/* Author: Lynh Casidy, Student ID: 001390025
   Javadoc is sent in a different zip file than the project. It is named Javadoc. */
/** This class creates the Inventory Management System Database.
 FUTURE ENHANCEMENT: For the future, I would correct the case sensitivity of the returning search results
 from the search bar. Currently, when searching for a part or product, you would have to capitalize the first letter
 for it to populate in the table. This would make the database more functional if users wanted to input data in
 all lowercase letters and still get the correct results.
 FUTURE ENHANCEMENT: Currently, when deleting a product, it must not have any associated parts attached to it.
 However, that doesn't apply to the parts if it is associated with a product. To enhance the user experience,
 I would write code that would not allow a user to delete a part if a product is associated with it. */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1065, 400));
        stage.show();
    }

/** This is the main method. This fills each table with data when the program first opens.
    @param args args*/
    public static void main(String[] args){

        Part brakes = new InHouse(1, "Brakes", 15.99, 10, 1, 50, 111);
        Part wheel = new InHouse (2, "Wheel", 24.99, 16, 1, 50, 123);
        Part seat = new Outsourced (3, "Seat", 14.99, 10, 1, 50, "Mike's Bikes");
        Inventory.addPart(brakes);
        Inventory.addPart(wheel);
        Inventory.addPart(seat);

        Product giantBike = new Product (1000, "Giant Bike", 299.99, 10, 1, 50);
        Product tricycle = new Product (1001, "Tricycle", 199.99, 16, 1, 50);
        Product unicycle = new Product(1002, "Unicycle", 99.99, 15, 1, 50);
        Inventory.addProduct(giantBike);
        Inventory.addProduct(tricycle);
        Inventory.addProduct(unicycle);

        launch(args);
    }
}

