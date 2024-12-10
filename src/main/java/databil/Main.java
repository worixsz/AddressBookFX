package databil;

import databil.ui.MainControlPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.CheckActionMove;
import repository.CreateContactMove;
import repository.SearchActionByPrefixMove;
import repository.SearchActionMove;

public class Main extends Application {

    private CreateContactMove contactService;
    private CheckActionMove checkActionMove;
    private SearchActionMove searchActionMove;
    private SearchActionByPrefixMove searchActionByPrefixMove;

    @Override
    public void start(Stage stage) throws Exception {

        this.contactService = new CreateContactMove();
        this.checkActionMove = new CheckActionMove();
        this.searchActionMove = new SearchActionMove();
        this.searchActionByPrefixMove = new SearchActionByPrefixMove();


        Scene scene = new Scene(new MainControlPane(contactService, checkActionMove, searchActionMove, searchActionByPrefixMove), 440, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
