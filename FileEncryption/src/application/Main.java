package application;
	
import controllers.MouseDrag;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            
            MouseDrag mousedrag = new MouseDrag();
            Image icon = new Image(getClass().getResource("/design/encryption.png").toExternalForm());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/base.fxml"));

            String css = getClass().getResource("/design/base.css").toExternalForm();

            Parent root = loader.load();
            Scene scene = new Scene(root);
            mousedrag.enableDrag(root, stage);

            stage.initStyle(StageStyle.TRANSPARENT);
            
            stage.setResizable(false);
            stage.getIcons().add(icon);
            scene.getStylesheets().add(css);
            stage.setTitle("File Encryption");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {e.printStackTrace();}
    }
}
