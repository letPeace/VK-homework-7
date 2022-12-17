package login;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servers.DefaultServer;
import servlets.CoreServlet;
import servlets.StaticServlet;

public final class LoginJDBC {

    public static final String STATIC_FOLDER = "/static";
    public static final String GUEST_FOLDER = "/guest";
    public static final String MANAGER_FOLDER = "/manager";
    public static final String INFO_FOLDER = "/info";
    public static final String ROOT_FOLDER = "/";
    public static final String ALL_FOLDERS = "/*";
    public static final String STATIC = "static";
    public static final String GUEST = "guest";
    public static final String MANAGER = "manager";
    public static final String INFO = "info";
    public static final String DEFAULT = "default";
    public static final String LOGIN = "login";
    public static final String JDBC_CONFIG = "/configs/jdbc_config";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";

    public static void start() throws Exception {

        final Server server = new DefaultServer().build();

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath(ROOT_FOLDER);

        context.addServlet(new ServletHolder(GUEST, new CoreServlet(GUEST)), GUEST_FOLDER);
        context.addServlet(new ServletHolder(MANAGER, new CoreServlet(MANAGER)), MANAGER_FOLDER+MANAGER_FOLDER);
        context.addServlet(new ServletHolder(INFO, StaticServlet.class), INFO_FOLDER);
        context.addServlet(new ServletHolder(DEFAULT, DefaultServlet.class), ALL_FOLDERS);

        final String jdbcConfig = LoginJDBC.class.getResource(JDBC_CONFIG).toExternalForm();
        final JDBCLoginService jdbcLoginService = new JDBCLoginService(LOGIN, jdbcConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(jdbcLoginService);

        securityHandler.setHandler(context);

        server.setHandler(securityHandler);
        server.addBean(jdbcLoginService);
        server.start();

    }
}