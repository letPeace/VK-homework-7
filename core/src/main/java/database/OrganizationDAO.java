package database;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.ORGANIZATION;

public class OrganizationDAO {

    private final @NotNull Connection connection;
    private final @NotNull DSLContext context;

    public OrganizationDAO(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public @NotNull Integer existsOrSave(@NotNull String name){
        if(!exists(name)) return save(name);
        return 0;
    }

    public @NotNull Boolean exists(@NotNull String name){
        return context
                .select()
                .from(ORGANIZATION)
                .where(ORGANIZATION.NAME.eq(name))
                .fetchOne() == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public @NotNull Integer save(@NotNull String name) {
        return context
                .insertInto(
                        ORGANIZATION,
                        ORGANIZATION.NAME
                )
                .values(name)
                .execute();
    }

}
