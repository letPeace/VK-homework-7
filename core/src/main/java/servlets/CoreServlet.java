package servlets;

import database.ProductDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginJDBC;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;
import utils.FileCopier;

import java.io.IOException;
import java.sql.SQLException;

public class CoreServlet extends HttpServlet {

    private static final String ALL = "all";
    private static final String FAILED_SAVE_MESSAGE = "Failed to save!";
    private static final String SUCCEED_SAVE_MESSAGE = "The product has been saved!";
    private static final String NO_PRODUCTS_MESSAGE = "No such products!";
    private static final String NAME_PARAMETER = "name";
    private static final String ORGANIZATION_NAME_PARAMETER = "organization_name";
    private static final String QUANTITY_PARAMETER = "quantity";
    private final String pathSpec;

    public CoreServlet(@NotNull String pathSpec){
        this.pathSpec = pathSpec;
    }

    @Override
    protected void doGet( @NotNull HttpServletRequest request,
                          @NotNull HttpServletResponse response ) throws IOException
    {
        var params = request.getParameterMap();
        if(params.size() == 0){
            response.setContentType(LoginJDBC.TEXT_HTML);
            response.setStatus(HttpServletResponse.SC_OK);
            FileCopier.copy(getClass(), LoginJDBC.STATIC, pathSpec+".html", response);
            return;
        }
        try{
            var productDAO = new ProductDAO();
            Result<Record> records = params.containsKey(ALL) ? productDAO.getAll() : productDAO.getAll(params.keySet());
            response.setContentType(LoginJDBC.TEXT_PLAIN);
            response.setStatus(HttpServletResponse.SC_OK);
            // throw exception exactly after setHandled(true)
            if(records.isEmpty()) throw new RuntimeException(NO_PRODUCTS_MESSAGE);
            response.getWriter().println(records);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            throw new RuntimeException(e);
        } catch (RuntimeException e){
            response.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost( @NotNull HttpServletRequest request,
                           @NotNull HttpServletResponse response ) throws IOException
    {
        String name = request.getParameter(NAME_PARAMETER);
        String organization_name = request.getParameter(ORGANIZATION_NAME_PARAMETER);
        String quantityString = request.getParameter(QUANTITY_PARAMETER);
        if(name == null || organization_name == null || quantityString == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try{
            var productDAO = new ProductDAO();
            Integer quantity = Integer.valueOf(quantityString);
            int result = productDAO.save(name, organization_name, quantity);
            response.setContentType(LoginJDBC.TEXT_PLAIN);
            response.setStatus(HttpServletResponse.SC_OK);
            String output = result == 0 ? FAILED_SAVE_MESSAGE : SUCCEED_SAVE_MESSAGE;
            response.getWriter().println(output);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            throw new RuntimeException(e);
        } catch (RuntimeException e){
            response.getWriter().println(e.getMessage());
        }
    }

}
