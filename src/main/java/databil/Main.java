package databil;

import databil.ui.MainControlPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.CheckActionMove;
import repository.CreateContactMove;

public class Main extends Application {

    private CreateContactMove contactService;
    private CheckActionMove checkActionMove;

    @Override
    public void start(Stage stage) throws Exception {

        this.contactService = new CreateContactMove();
        this.checkActionMove = new CheckActionMove();


        Scene scene = new Scene(new MainControlPane(contactService, checkActionMove), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
