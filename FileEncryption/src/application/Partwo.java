package application;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Partwo implements Initializable {

    @FXML
    private Button close;

    @FXML
    private TextArea inserter;

    @FXML
    private Button logoutr;

    @FXML
    private AnchorPane mainPage;

    @FXML
    private Button minimize;

    @FXML
    private TextArea reciever;

    @FXML
    private ComboBox<String> steps;

    @FXML
    private Button submit;
    
    @FXML
    private Button createAdd;
    
	private MouseDrag mousedrag = new MouseDrag();
	private AlertMessage alert = new AlertMessage();
	private Stage stage = new Stage();
	
	private Path path = Paths.get(System.getProperty("user.home"), "Desktop", "file.txt");
    private IvParameterSpec iv;
    private String result = "";
    boolean dolittle = false;
    
    public Partwo() {
        byte[] ivBytes = new byte[16]; 
        new SecureRandom().nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);
    }
    
	public void submit() throws Exception {
		String selectedOption = steps.getValue();

		if (inserter.getText().isEmpty()) {
			alert.errorm("Text is empty");
			return;
		}
		String info = inserter.getText();
		if ("Encrypt".equals(selectedOption)) {
			reciever.clear();
			result = ListInfo.encrypt(info, iv);
			reciever.setText(result);
			reciever.setDisable(false);
		} else if ("Decrypt".equals(selectedOption)) {
			result = ListInfo.decrypt(info, iv);
			reciever.clear();
			reciever.setText(result);
			reciever.setDisable(false);
		}
	}

	public void createAdt() throws Exception {
		if (inserter.getText().isEmpty()) {
			alert.errorm("Text is empty");
			return;
		}
		submit();
		if (Files.exists(path)) {
			dolittle = alert.doyouWanna("Do you wanna delete your file");
			if (dolittle) {
				deleteFile();
				createFile(reciever.getText());
			} else {return;}
		} else {
			createFile(reciever.getText());
		}
	}

    public void createFile(String text) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write(text);
        } catch (IOException e) {
            System.err.println("Failed to create file: " + e.getMessage());
        }
    }
    
    public void deleteFile() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }
    }
	
	
    public void logoutr() {
		try {
			Image icon = new Image(getClass().getResource("/design/encryption.png").toExternalForm());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/base.fxml"));
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
    
	public void minimize() {
		Stage stage = (Stage) mainPage.getScene().getWindow();
		stage.setIconified(true);
	}

	public void close() {
		Platform.exit();
	}
	
	public void hardreset() {
		reciever.setDisable(true);
		inserter.setDisable(true);
		inserter.clear();
		reciever.clear();
	}
	
    public void switchForm(ActionEvent event) {
    	switch(steps.getSelectionModel().getSelectedItem()) {
    	case "Encrypt":
    		hardreset();
    		inserter.setDisable(false);
    		break;
    	case "Decrypt":
    		hardreset();
    		inserter.setDisable(false);
    		break;
    	}
    }
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		steps.setItems(FXCollections.observableArrayList("Encrypt", "Decrypt"));
		hardreset();
	}
}
