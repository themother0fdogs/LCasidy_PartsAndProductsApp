package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Inventory;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

/** This is the AddPartForm class that works with AddPartForm FXML to add new parts. */
public class AddPartForm {

    Stage stage;
    Parent scene;
    Label label;


    /** When user clicks button, user will return to main form.
     @param event main menu event*/
    @FXML
    void onActionMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to leave this page without saving?");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
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
     @return goes back to current AddPartForm so user can input valid data in text fields.
     @param event saves input */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        if( addPartNameTxt.getText().isEmpty() || addPartPriceTxt.getText().isEmpty() ||
                addPartInvTxt.getText().isEmpty() ||  addPartMinTxt.getText().isEmpty() ||
                addPartMaxTxt.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Empty Fields");
            alert.setContentText("Please enter information for each text field!");
            alert.showAndWait();
            return;
        }

        try {
            Double.parseDouble(addPartPriceTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Price is not a double!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(addPartInvTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Inventory is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(addPartMinTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Minimum is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(addPartMaxTxt.getText());
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
            id = randGen.nextInt(100) + 1;

            String name = addPartNameTxt.getText();
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int stock = Integer.parseInt(addPartInvTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());

            int machineId = 0;
            String companyName;

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


        if (addOutsourcedRbtn.isSelected()) {
                companyName = addPartMachineIdTxt.getText();
                Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(addPart);
            }
            if (addInhouseRbtn.isSelected()) {
                machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                InHouse addPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(addPart);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

    }

    /** When outsourced radio button is pressed, label will change to Company Name. */
    @FXML
    void onActionOutSourcedRbtn(ActionEvent event) {
        if(addOutsourcedRbtn.isSelected())
        {
        label = machineIdLbl;
        label.setText("Company Name");
        }
    }

    /** When in-house radio button is pressed, label will change to Machine ID. */
    @FXML
    void onActionInHouseRbtn(ActionEvent event) {
        if(addInhouseRbtn.isSelected()) {
            label = machineIdLbl;
            label.setText("Machine ID");
        }
    }

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartMachineIdTxt;

    @FXML
    private RadioButton addInhouseRbtn;

    @FXML
    private RadioButton addOutsourcedRbtn;

    @FXML
    private Label machineIdLbl;

}
