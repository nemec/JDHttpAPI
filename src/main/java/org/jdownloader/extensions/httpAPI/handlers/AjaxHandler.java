package org.jdownloader.extensions.httpAPI.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxHandler extends AbstractHandler {
    private final boolean allowGet;

    public AjaxHandler(boolean allowGet) {
        this.allowGet = allowGet;
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        if(baseRequest.getMethod().equals("OPTIONS")) {

            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Access-Control-Allow-Origin", "*");

            String methods = "POST, OPTIONS";
            if(allowGet) methods += ", GET";
            response.setHeader("Access-Control-Allow-Methods", methods);
            response.setHeader("Access-Control-Max-Age", "1000");
            String req = request.getHeader("Access-Control-Request-Headers");
            if(req != null) {
                response.setHeader("Access-Control-Allow-Headers", req);
            }

            baseRequest.setHandled(true);
        }
    }
}
