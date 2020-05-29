/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.awt.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    @FXML
    void doSimula(ActionEvent event) {

    }
    
    @FXML
    void riverSelected(ActionEvent event) { // devo caricare le letture relative al fiume
    	Map<String, String> valori = new HashMap<> ( model.getUltimiValori( boxRiver.getValue() ) );
    	txtStartDate.setText( valori.get("firstDate") );
    	txtEndDate.setText( valori.get("lastDate") );
    	txtNumMeasurements.setText( valori.get("nItems") );
    	txtFMed.setText( valori.get("avg_flow") );
    	boxRiver.getValue().setFlowAvg( Double.valueOf(valori.get("avg_flow")) );
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxRiver.getItems().addAll(model.getAllRivers()); // inizializzo la comboBox qui, non posso nel'init perchè ancora non ho il Model attivo.
    }
}
