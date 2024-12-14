package databil;

import databil.ui.MainControlPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;

public class Main extends Application {

    private CreateContactMove createContactMove;
    private CheckActionMove checkActionMove;
    private SearchActionMove searchActionMove;
    private SearchActionByPrefixMove searchActionByPrefixMove;
    private UpdateActionMove updateByPhoneMove;


    @Override
    public void start(Stage stage) throws Exception {

        this.createContactMove = new CreateContactMove();
        this.checkActionMove = new CheckActionMove();
        this.searchActionMove = new SearchActionMove();
        this.searchActionByPrefixMove = new SearchActionByPrefixMove();
        this.updateByPhoneMove = new UpdateActionMove();


        Scene scene = new Scene(new MainControlPane(createContactMove, checkActionMove, searchActionMove, searchActionByPrefixMove, updateByPhoneMove), 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
