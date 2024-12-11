package databil;

import databil.ui.MainControlPane;
import databil.ui.UpdateContactForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;

public class Main extends Application {

    private CreateContactMove contactService;
    private CheckActionMove checkActionMove;
    private SearchActionMove searchActionMove;
    private SearchActionByPrefixMove searchActionByPrefixMove;
    private UpdateActionMove updateActionMove;

    @Override
    public void start(Stage stage) throws Exception {

        this.contactService = new CreateContactMove();
        this.checkActionMove = new CheckActionMove();
        this.searchActionMove = new SearchActionMove();
        this.searchActionByPrefixMove = new SearchActionByPrefixMove();
        this.updateActionMove = new UpdateActionMove();


        Scene scene = new Scene(new MainControlPane(contactService, checkActionMove, searchActionMove, searchActionByPrefixMove, updateActionMove), 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
