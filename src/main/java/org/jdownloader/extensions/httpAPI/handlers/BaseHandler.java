package org.jdownloader.extensions.httpAPI.handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.jdownloader.extensions.httpAPI.LinkController;
import org.jdownloader.extensions.httpAPI.models.AddLinkRequest;
import org.jdownloader.extensions.httpAPI.models.AddLinkResponse;
import org.jdownloader.extensions.httpAPI.ParseException;
import org.jdownloader.extensions.httpAPI.models.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseHandler extends AbstractHandler {

    protected final Gson jparser = new Gson();

    protected final LinkController controller;

    protected BaseHandler(LinkController ctr) {
        super();
        controller = ctr;
    }

    protected abstract AddLinkRequest parseAddLinkParams(HttpServletRequest req) throws ParseException;

    protected abstract String getAllowedMethod();

    private void writeResponse(Request baseRequest, HttpServletResponse response, Object data) throws IOException{
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8");

        String origin = baseRequest.getHeader("Origin");
        if(origin != null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        PrintWriter out = response.getWriter();
        out.println(jparser.toJson(data));
        baseRequest.setHandled(true);
    }

    private void writeError(Request baseRequest, HttpServletResponse response, String message) throws IOException{
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json; charset=utf-8");
        ErrorResponse resp = new ErrorResponse();
        resp.errorMessage = message;
        PrintWriter out = response.getWriter();
        out.println(jparser.toJson(resp));
        baseRequest.setHandled(true);
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        if(!baseRequest.getMethod().equals(getAllowedMethod())) {
            return;
        }

        String path = request.getPathInfo();

        if(path.equals("/addLink")) {
            AddLinkRequest data;
            try {
                data = parseAddLinkParams(request);
            }
            catch (ParseException e) {
                writeError(baseRequest, response, e.getMessage());
                return;
            }

            if(data.packageName != null && !data.packageName.equals("")) {
                controller.AddLink(data.url.toString(), data.packageName, data.forcePackageName);
            }
            else {
                controller.AddLink(data.url.toString());
            }

            AddLinkResponse resp = new AddLinkResponse();
            resp.success = true;
            writeResponse(baseRequest, response, resp);
        }
    }
}
