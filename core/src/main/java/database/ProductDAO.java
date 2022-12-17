package database;

import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static generated.Tables.PRODUCT;

public class ProductDAO {

    private final @NotNull Connection connection;
    private final @NotNull DSLContext context;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public ProductDAO() throws SQLException {
        this(JDBCCredentials.getConnection());
    }

    public @NotNull Result<Record> getAll() {
        return context
                .select()
                .from(PRODUCT)
                .fetch();
    }

    public @NotNull Result<Record> getAll(@NotNull Collection<String> names) {
        Condition condition = DSL.falseCondition();
        for(String name : names){
            condition = condition.or(PRODUCT.NAME.eq(name));
        }
        return context
                .select()
                .from(PRODUCT)
                .where(condition)
                .fetch();
    }

    public @NotNull Integer save(@NotNull String name, @NotNull String organization_name, @NotNull Integer quantity) {
        OrganizationDAO organizationDAO = new OrganizationDAO(connection);
        organizationDAO.existsOrSave(organization_name);
        return context
                .insertInto(
                        PRODUCT,
                        PRODUCT.NAME,
                        PRODUCT.ORGANIZATION_NAME,
                        PRODUCT.QUANTITY
                )
                .values(
                        name,
                        organization_name,
                        quantity
                )
                .execute();
    }

}
