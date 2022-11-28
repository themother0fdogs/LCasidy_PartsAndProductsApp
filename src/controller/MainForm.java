package controller;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the MainForm class that works with MainForm FXML to show current parts and products. */
public class MainForm implements Initializable {
    Stage stage;
    Parent scene;

    /** When user clicks button, it allows users to go to the AddPartForm screen to add part. */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** When user clicks button, it allows users to go to the AddProductForm screen to add product. */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** When user clicks button, it will delete a part that is selected. */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectDeletePart = partsTableView.getSelectionModel().getSelectedItem();
            if(selectDeletePart ==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error - No Part Selected");
                alert.setContentText("Please select a part to delete!");
                alert.showAndWait();
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
                Optional<ButtonType> results = alert.showAndWait();
                if (results.isPresent() && results.get() == ButtonType.OK)
                {
                    Inventory.deletePart(selectDeletePart);
                }
             }
    }

    /** When user clicks button, it will delete a product that is selected. */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
      Product selectDeleteProduct = productsTableView.getSelectionModel().getSelectedItem();
        if(selectDeleteProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No Product Selected");
            alert.setContentText("Please select a product to delete!");
            alert.showAndWait();
        }
           else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
                Optional<ButtonType> results = alert.showAndWait();
                if (selectDeleteProduct.getAllAssociatedParts().size() > 0)
                {
                    Alert deleteError = new Alert(Alert.AlertType.ERROR);
                    deleteError.setTitle("Error - Product has Associated Parts");
                    deleteError.setContentText("There are parts associated with this product! \r\n Please remove associated parts before deleting!");
                    deleteError.showAndWait();
                }
                else if (results.isPresent() && results.get() == ButtonType.OK)
                {
                    Inventory.deleteProduct(selectDeleteProduct);
                }
            }

    }

    /** When user clicks button, it will exit out of the whole database. */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> results = alert.showAndWait();
        if(results.isPresent() && results.get() == ButtonType.OK)
        {
            System.exit(0);
        }
    }

    /** When user clicks button, it allows users to go to the ModifyPartForm screen to modify a selected part. */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartForm MPFController = loader.getController();
            MPFController.detailedPart(partsTableView.getSelectionModel().getSelectedIndex(), partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No Part Selected");
            alert.setContentText("Please select a part to modify!");
            alert.showAndWait();
        }
    }

    /** When user clicks button, it allows users to go to the ModifyProductForm screen to modify a selected product. */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductForm MPRFController = loader.getController();
            MPRFController.detailedProduct(productsTableView.getSelectionModel().getSelectedIndex(), productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - No Product Selected");
            alert.setContentText("Please select a product to modify!");
            alert.showAndWait();
        }
    }

    /** This allows user to input data in a search box to find matching product.  */
    @FXML
    void onActionSearchProduct(ActionEvent event) {
        String productName = productSearchBar.getText();
        ObservableList<Product> products = Inventory.lookupProduct(productName);
        productsTableView.setItems(products);

    }

    /** This allows user to input data in a search box to find matching part.  */
    @FXML
    void onActionSearchPart(ActionEvent event) {
        String partName = partSearchBar.getText();
        ObservableList<Part> parts = Inventory.lookupPart(partName);
        partsTableView.setItems(parts);
    }

    /** Initializes both tables with data of its matching column.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TextField productSearchBar;
}

