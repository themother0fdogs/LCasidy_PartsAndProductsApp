package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/** This is the AddProductForm class that works with AddProductForm FXML to add new products. */
public class AddProductForm implements Initializable {
    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** When user clicks button, user will return to main form.
     @param event main menu event */
    @FXML
    void onActionMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to leave this page without saving?");
        Optional<ButtonType> results = alert.showAndWait();
        if(results.isPresent() && results.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

   /** When user clicks button, it allows users to add associated parts to a product. */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        Part selectedPart = addProductTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
        {Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No part selected");
            alert.setContentText("Please select a part to add!");
            alert.showAndWait();}
        else{
            associatedParts.add(selectedPart);
            addProductCurrentTableView.setItems(associatedParts);
        }
    }

    /** This allows user to input data in a search box to find matching part. */
    @FXML
    void onActionAddSearchPart(ActionEvent event) {
        String searchPart = addPartSearchBar.getText();
        ObservableList<Part> parts = Inventory.lookupPart(searchPart);
        addProductTableView.setItems(parts);
    }

    /** When user clicks button, associated part will be removed. */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part selectedAssociatedPart = addProductCurrentTableView.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No Part Selected");
            alert.setContentText("Please select a part to delete!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                associatedParts.remove(selectedAssociatedPart);
                addProductCurrentTableView.setItems(associatedParts);
            }
        }
    }

    /** When user clicks save button, code will determine if input is valid and will save if valid.
     RUNTIME ERROR: RUNTIME ERROR: I ran into a runtime error, NumberFormatException, when I was trying to save data with the
     incorrect data type in a text field or if a text field was empty. I corrected this error by implementing a
     try/catch code that reads each individual text field and will return the appropriate error message for that input.
     However, I wanted the program to check if all data fields are filled out first (regardless if the values were valid,)
     so I added an if-statement to populate an error message if at least one text field is empty.
     LOGICAL ERROR: When all text fields are empty when I click the save button, it did not populate the correct
     error message. The error message I got was the invalid input for "price" because it is the first individual text
     field with the try/catch statements. As stated above, I added an if-statement to check for any empty text field
     and if there is at least one empty, an "Empty Fields" error will occur. Once all text fields have input, the
     program will run the try/catch statements for each of the text fields. Users will not be able to save until all
     text field values are filled out and of the correct data type.
     @return goes back to current AddProductForm so user can input valid data in text fields. */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        if( addProductNameTxt.getText().isEmpty() || addProductPriceTxt.getText().isEmpty() ||
                addProductInvTxt.getText().isEmpty() ||  addProductMinTxt.getText().isEmpty() ||
                addProductMaxTxt.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Empty Fields");
            alert.setContentText("Please enter information for each text field!");
            alert.showAndWait();
            return;
        }

        try {
            Double.parseDouble(addProductPriceTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Price is not a double!");
            alert.showAndWait();
            return;

        }
        try {
            Integer.parseInt(addProductInvTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Inventory is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(addProductMinTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Minimum is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(addProductMaxTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Maximum is not an integer!");
            alert.showAndWait();
            return;
        }
            Random randGen = new Random();
            int id;
            id = randGen.nextInt(1500) + 1;

            String name = addProductNameTxt.getText();
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int stock = Integer.parseInt(addProductInvTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Min is greater than Max");
            alert.setContentText("Max must be greater than Min!");
            alert.showAndWait();
            return;
        } else if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Inventory Limit");
            alert.setContentText("Inventory must be within the Min and Max!");
            alert.showAndWait();
            return;
        }

            Product newProduct = new Product(id, name, price, stock, min, max);
            for (Part part: associatedParts) {
                newProduct.addAssociatedPart(part);
                addProductCurrentTableView.setItems(associatedParts);
            }
            newProduct.getAllAssociatedParts();
            Inventory.addProduct(newProduct);


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    /** Initializes both tables with data of its matching column.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTableView.setItems(Inventory.getAllParts());
        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        addProductCurrentTableView.setItems(associatedParts);
        addAssocPartIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        addAssocPartNameCol.setCellValueFactory((new PropertyValueFactory<>("name")));
        addAssocPartPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));
        addAssocPartInventoryLevelCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
    }

    @FXML
    private TableView<Part> addProductCurrentTableView;

    @FXML
    private TextField addPartSearchBar;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TableView<Part> addProductTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIdCol;

    @FXML
    private TableColumn<Part, Integer> addPartInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Double> addPartPriceCol;

    @FXML
    private TableColumn<Part, Integer> addAssocPartIdCol;

    @FXML
    private TableColumn<Part, Integer> addAssocPartInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> addAssocPartNameCol;

    @FXML
    private TableColumn<Part, Double> addAssocPartPriceCol;

}
