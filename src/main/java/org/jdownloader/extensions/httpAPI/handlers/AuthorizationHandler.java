package org.jdownloader.extensions.httpAPI.handlers;

import org.appwork.utils.encoding.Base64;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorizationHandler extends AbstractHandler {
    private final String password;
    private final String authenticationHeader;

    public AuthorizationHandler(String password) {
        this.password = password;
        authenticationHeader = "Basic " + Base64.encode(":" + password);
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.equals(authenticationHeader)) {
            response.addHeader("WWW-Authenticate", "Basic");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType("text/plain; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("Login to access API");

            baseRequest.setHandled(true);
        }
    }
}
