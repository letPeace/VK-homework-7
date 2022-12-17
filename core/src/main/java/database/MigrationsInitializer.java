package database;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

public class MigrationsInitializer {

    private static final @NotNull JDBCCredentials CREDENTIALS = JDBCCredentials.DEFAULT_CREDENTIALS;
    private static final @NotNull String LOCATIONS = "db/migrations";
    private static final @NotNull String SCHEMA_PUBLIC = "public";
    private static final @NotNull String SCHEMA_CORE = "core";
    private static final @NotNull String SCHEMA_SECURITY = "security";

    public static void initialize() {
        initialize(CREDENTIALS);
    }

    public static void initialize(@NotNull JDBCCredentials jdbcCredentials){
        final Flyway flyway = Flyway.configure()
                .dataSource(
                        jdbcCredentials.url(),
                        jdbcCredentials.login(),
                        jdbcCredentials.password()
                )
                .schemas(SCHEMA_PUBLIC, SCHEMA_CORE, SCHEMA_SECURITY)
                .cleanDisabled(false)
                .locations(LOCATIONS)
                .load();
        flyway.clean();
        flyway.migrate();
    }

}
