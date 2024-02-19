package application;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

import javax.crypto.spec.IvParameterSpec;

import controllers.AlertMessage;
import controllers.ListInfo;
import controllers.MouseDrag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Operations implements Initializable {

	@FXML
	private AnchorPane mainPage;

	@FXML
	private Button close;

	@FXML
	private TextField insert;

	@FXML
	private Button minimize;

	@FXML
	private TextField recieve;

	@FXML
	private ComboBox<String> steps;

	@FXML
	private Button submit;

	@FXML
	private Button genFiler;
	
	private MouseDrag mousedrag = new MouseDrag();
	private AlertMessage alert = new AlertMessage();
	private Stage stage = new Stage();
	private IvParameterSpec iv;
    
    public Operations() {
        byte[] ivBytes = new byte[16]; 
        new SecureRandom().nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);
    }
	
    public void submit() throws Exception {
        String selectedOption = steps.getValue();

        if ("Encrypt".equals(selectedOption)) {
            if (insert.getText().isEmpty()) {
                alert.errorm("Text is empty");
                setFunction(true);
            } else {
                String info = insert.getText();
                recieve.setText(ListInfo.encrypt(info, iv));
                setFunction(false);
            }
        } else if ("Decrypt".equals(selectedOption)) {
            if (insert.getText().isEmpty()) {
                alert.errorm("Text is empty");
                setFunction(true);
            } else {
                String info = insert.getText();
                recieve.setText(ListInfo.decrypt(info, iv));
                setFunction(false);
            }
        }
    }
    
    public void switchForm(ActionEvent event) {
    	switch(steps.getSelectionModel().getSelectedItem()) {
    	case "Encrypt":
    		setFunction(true);
    		recieve.clear();
    		break;
    	case "Decrypt":
    		setFunction(true);
    		recieve.clear();
    		break;
    	}
    }
    
	
    public void setFunction(boolean diss) {
    	recieve.setDisable(diss);
    }
    
	public void minimize() {
		Stage stage = (Stage) mainPage.getScene().getWindow();
		stage.setIconified(true);
	}

	public void close() {
		Platform.exit();
	}

	public void genFiler() {
		try {
			Image icon = new Image(getClass().getResource("/design/encryption.png").toExternalForm());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/fileGeneration.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			mousedrag.enableDrag(root, stage);

			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setTitle("Big text en/decryption");
			stage.setResizable(false);
			stage.getIcons().add(icon);
			stage.setScene(scene);

			mainPage.getScene().getWindow().hide();
			stage.show();

		} catch (Exception e) {e.printStackTrace();}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		steps.setItems(FXCollections.observableArrayList("Encrypt", "Decrypt"));
		setFunction(true);
	}
}
