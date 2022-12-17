package utils;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class FileCopier {

    public static void copy(
            @NotNull Class<? extends HttpServlet> resourceClass,
            @NotNull String resourceName,
            @NotNull String fileName,
            @NotNull HttpServletResponse response
    ) throws IOException {
        final URL resource = resourceClass.getResource("/" + resourceName);
        String path = resource.toExternalForm().substring(6); // to make "file:/<path>" -> "<path>"
        File file = new File(path + "/" + fileName);
        FileUtils.copyFile(file, response.getOutputStream());
    }

}
