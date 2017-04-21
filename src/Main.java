import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import com.got.database.DBConnector;
import com.got.database.DatabaseType;
import com.got.event.EventMapper;
import com.got.login.LoginMethod;
import com.got.login.contracts.LoginSystemInterface;
import com.got.validator.ValidatorFactory;
import events.MovieWasFetched;
import javafx.application.Application;
import javafx.stage.Stage;
import listeners.LogFetchedMovie;
import validators.DateValidator;

public class Main extends Application {
    private static Container container = ContainerFactory.getDefaultContainer();

    public static void main(String[] args) {
        DBConnector.connect(DatabaseType.MYSQL, "localhost", "8889", "mts", "root", "root");
        EventMapper.map(MovieWasFetched.class, LogFetchedMovie.class);
        ValidatorFactory validatorFactory = container.make(ValidatorFactory.class);
        validatorFactory.addValidator(DateValidator.class, "date", "Field must be a valid date");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginSystemInterface loginSystem = container.make(LoginSystemInterface.class);
        loginSystem.setLoginMethod(LoginMethod.DATABASE);
        loginSystem.launch(primaryStage, "Movie Ticketing System", "views/MainScreen");
    }
}
