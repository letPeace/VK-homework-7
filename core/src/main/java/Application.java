import database.MigrationsInitializer;
import login.LoginJDBC;

public final class Application {

    public static void main(String[] args) throws Exception {
        MigrationsInitializer.initialize();
        LoginJDBC.start();
    }

}
