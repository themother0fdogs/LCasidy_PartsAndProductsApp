package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.util.Optional;

/** This is the ModifyPartForm class that works with ModifyPartForm FXML to modify parts. */
public class ModifyPartForm {
    Stage stage;
    Parent scene;
    Label label;
    int partIndex = 0;

    /** When user clicks button, user will return to main form.
     @param event main menu event */
    @FXML
    void onActionMainForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to leave this page without saving?");
        Optional<ButtonType> results = alert.showAndWait();
        if(results.isPresent() && results.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Retrieves all data for the selected part.
     @param index finds an index of selected part
     @param part  finds selected part  */
    public void detailedPart (int index, Part part)
    {
        partIndex = index;
        modifyPartIdTxt.setText(String.valueOf(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyPartInvTxt.setText(String.valueOf(part.getStock()));
        modifyPartMinTxt.setText(String.valueOf(part.getMin()));
        modifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        modifyPartPriceTxt.setText(String.valueOf(part.getPrice()));

        if (part instanceof Outsourced) {
            modifyPartOutsourcedRbtn.setSelected(true);
            modifyPartMachineIdTxt.setText(((Outsourced) part).getCompanyName());
            label = machineIdLbl;
            label.setText("Company Name");
        }
        else {
            modifyPartInhouseRbtn.setSelected(true);
            modifyPartMachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            label = machineIdLbl;
            label.setText("Machine ID");

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
     @return goes back to current ModifyPartForm so user can input valid data in text fields. */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        if( modifyPartNameTxt.getText().isEmpty() || modifyPartPriceTxt.getText().isEmpty() ||
                modifyPartInvTxt.getText().isEmpty() ||  modifyPartMinTxt.getText().isEmpty() ||
                modifyPartMaxTxt.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Empty Fields");
            alert.setContentText("Please enter information for each text field!");
            alert.showAndWait();
            return;
        }

        try {
            Double.parseDouble(modifyPartPriceTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Price is not a double!");
            alert.showAndWait();
            return;

        }
        try {
            Integer.parseInt(modifyPartInvTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Inventory is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(modifyPartMinTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Minimum is not an integer!");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(modifyPartMaxTxt.getText());
        } catch (
                NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid Input");
            alert.setContentText("Maximum is not an integer!");
            alert.showAndWait();
            return;
        }

            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int stock = Integer.parseInt(modifyPartInvTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());

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

            if (modifyPartOutsourcedRbtn.isSelected()) {
                companyName = modifyPartMachineIdTxt.getText();
                Outsourced updatePart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(partIndex, updatePart);
            }
            if (modifyPartInhouseRbtn.isSelected()) {
                machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
                InHouse updatePart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(partIndex, updatePart);
            }


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    /** When outsourced radio button is pressed, label will change to Company Name. */
    @FXML
    void onActionOutsourcedRbtn(ActionEvent event) {
        if(modifyPartOutsourcedRbtn.isSelected()) {
            label = machineIdLbl;
            label.setText("Company Name");
        }
    }

    /** When in-house radio button is pressed, label will change to Machine ID. */
    @FXML
    void onActionInHouseRbtn(ActionEvent event) {
        if(modifyPartInhouseRbtn.isSelected()) {
            label = machineIdLbl;
            label.setText("Machine ID");
        }
    }

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInhouseRbtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartMachineIdTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedRbtn;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private Label machineIdLbl;

}
