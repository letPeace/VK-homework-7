/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.tables.Organization;
import generated.tables.Product;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Core extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>core</code>
     */
    public static final Core CORE = new Core();

    /**
     * The table <code>core.organization</code>.
     */
    public final Organization ORGANIZATION = Organization.ORGANIZATION;

    /**
     * The table <code>core.product</code>.
     */
    public final Product PRODUCT = Product.PRODUCT;

    /**
     * No further instances allowed
     */
    private Core() {
        super("core", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Organization.ORGANIZATION,
            Product.PRODUCT
        );
    }
}