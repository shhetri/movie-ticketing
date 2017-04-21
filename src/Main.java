import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import com.got.database.DBConnector;
import com.got.database.DatabaseType;
import com.got.event.EventMapper;
import com.got.login.LoginMethod;
import com.got.login.contracts.LoginSystemInterface;
import events.MovieWasFetchedSample;
import javafx.application.Application;
import javafx.stage.Stage;
import listeners.LogFetchedMovieSample;

public class Main extends Application {
    private static Container container = ContainerFactory.getDefaultContainer();

    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginSystemInterface loginSystem = container.make(LoginSystemInterface.class);
        loginSystem.setLoginMethod(LoginMethod.DATABASE);
        loginSystem.launch(primaryStage, "Movie Ticketing System", "views/MainScreen");
    }


    public static void main(String[] args) {
        DBConnector.connect(DatabaseType.MYSQL, "172.17.2.179", "8889", "mts", "root", "root");
        EventMapper.map(MovieWasFetchedSample.class, LogFetchedMovieSample.class);
        launch(args);
    }
}
