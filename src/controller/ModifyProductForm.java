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
import java.util.ResourceBundle;

/** This is the ModifyProductForm class that works with ModifyProductForm FXML to modify products. */
public class ModifyProductForm implements Initializable {

    Stage stage;
    Parent scene;
    int productIndex = 0;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** When user clicks button, it allows users to add associated parts to a product. */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectedPart = modifyProductTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No part selected");
            alert.setContentText("Please select a part to add!");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
            modifyProductCurrentTableView.setItems(associatedParts);
        }
    }

    /** This allows user to input data in a search box to find matching part.  */
    @FXML
    void onActionModifySearchPart(ActionEvent event) {
        String searchPart = modifyPartSearchBar.getText();
        ObservableList<Part> parts = Inventory.lookupPart(searchPart);
        modifyProductTableView.setItems(parts);
    }

    /** When user clicks button, user will return to main form. */
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

    /** When user clicks button, associated part will be removed. */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part selectedAssociatedPart = modifyProductCurrentTableView.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No Part Selected");
            alert.setContentText("Please select a part to delete!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
            Optional<ButtonType> results = alert.showAndWait();
                if (results.isPresent() && results.get() == ButtonType.OK) {
                        associatedParts.remove(selectedAssociatedPart);
                        modifyProductCurrentTableView.setItems(associatedParts);
                    }
                }
}
    /** Retrieves all data for the selected product.
     @param index finds an index of selected product
     @param product  finds selected product */
    public void detailedProduct (int index, Product product)
    {
        productIndex = index;
        modifyProductIdTxt.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        for (Part part: product.getAllAssociatedParts()) {
            associatedParts.add(part);
        }

    }

    /** When user clicks save button, code will determine if input is valid and will save if valid.
     RUNTIME ERROR: I ran into a runtime error, NumberFormatException, when I was trying to save data with the
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
     @return goes back to current ModifyProductForm so user can input valid data in text fields. */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        if( modifyProductNameTxt.getText().isEmpty() || modifyProductPriceTxt.getText().isEmpty() ||
                modifyProductInvTxt.getText().isEmpty() ||  modifyProductMinTxt.getText().isEmpty() ||
                modifyProductMaxTxt.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Empty Fields");
            alert.setContentText("Please enter information for each text field!");
            alert.showAndWait();
            return;
        }

        try {
            Double.parseDouble(modifyProductPriceTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Price is not a double!");
            alert.showAndWait();
            return;

        }
        try {
            Integer.parseInt(modifyProductInvTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Inventory is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(modifyProductMinTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Minimum is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(modifyProductMaxTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Maximum is not an integer!");
            alert.showAndWait();
            return;
        }

            int id = Integer.parseInt(modifyProductIdTxt.getText());
            String name = modifyProductNameTxt.getText();
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int stock = Integer.parseInt(modifyProductInvTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());

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

            Product updateProduct = new Product(id, name, price, stock, min, max);
            for (Part part: associatedParts) {
                updateProduct.addAssociatedPart(part);
                modifyProductCurrentTableView.setItems(associatedParts);
            }
            updateProduct.getAllAssociatedParts();
            Inventory.updateProduct(productIndex, updateProduct);

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
        modifyProductTableView.setItems(Inventory.getAllParts());
        modifyPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        modifyProductCurrentTableView.setItems(associatedParts);
        modifyAssocPartIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        modifyAssocPartNameCol.setCellValueFactory((new PropertyValueFactory<>("name")));
        modifyAssocPartPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));
        modifyAssocPartInventoryLevelCol.setCellValueFactory((new PropertyValueFactory<>("stock")));

    }

    @FXML
    private TableView<Part> modifyProductCurrentTableView;

    @FXML
    private TextField modifyPartSearchBar;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TableView<Part> modifyProductTableView;

    @FXML
    private TableColumn<Part, Integer> modifyPartIdCol;

    @FXML
    private TableColumn<Part, Integer> modifyPartInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> modifyPartNameCol;

    @FXML
    private TableColumn<Part, Double> modifyPartPriceCol;

    @FXML
    private TableColumn<Part, Integer> modifyAssocPartIdCol;

    @FXML
    private TableColumn<Part, Integer> modifyAssocPartInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> modifyAssocPartNameCol;

    @FXML
    private TableColumn<Part, Double> modifyAssocPartPriceCol;

}
