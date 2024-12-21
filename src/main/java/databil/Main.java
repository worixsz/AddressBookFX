package databil;

import databil.ui.MainControlPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;

public class Main extends Application {

    private CreateServiceImpl contactCreateServiceImpl;
    private ViewerServiceImpl contactViewerServiceImpl;
    private DataProcessorImpl dataProcessorImpl;
    private SearchServiceImpl searchServiceImpl;
    private SearchPrefixServiceImpl contactSearcherPrefix;
    private UpdateServiceImpl updateByPhoneMove;
    private DeleteServiceImpl deleteService;


    @Override
    public void start(Stage stage) throws Exception {

        this.contactCreateServiceImpl = new CreateServiceImpl();
        this.contactViewerServiceImpl = new ViewerServiceImpl();
        this.dataProcessorImpl = new DataProcessorImpl();
        this.searchServiceImpl = new SearchServiceImpl();
        this.contactSearcherPrefix = new SearchPrefixServiceImpl();
        this.updateByPhoneMove = new UpdateServiceImpl();
        this.deleteService = new DeleteServiceImpl();

        Scene scene = new Scene(new MainControlPane(
                contactCreateServiceImpl, contactViewerServiceImpl, dataProcessorImpl,
                searchServiceImpl, contactSearcherPrefix, updateByPhoneMove, deleteService),
                500, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
