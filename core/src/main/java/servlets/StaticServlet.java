package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginJDBC;
import org.jetbrains.annotations.NotNull;
import utils.FileCopier;

import java.io.IOException;

public class StaticServlet extends HttpServlet {

    @Override
    protected void doGet( @NotNull HttpServletRequest request,
                          @NotNull HttpServletResponse response ) throws IOException {
        response.setContentType(LoginJDBC.TEXT_HTML);
        response.setStatus(HttpServletResponse.SC_OK);
        FileCopier.copy(getClass(), LoginJDBC.STATIC, LoginJDBC.INFO+".html", response);
    }

}
