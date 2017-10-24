package org.jdownloader.extensions.httpAPI.handlers;


import org.jdownloader.extensions.httpAPI.LinkController;
import org.jdownloader.extensions.httpAPI.models.AddLinkRequest;
import org.jdownloader.extensions.httpAPI.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;


public class JDServerGETHandler extends BaseHandler {

    public JDServerGETHandler(LinkController ctr) {
        super(ctr);
    }

    protected AddLinkRequest parseAddLinkParams(HttpServletRequest req) throws ParseException{
        AddLinkRequest data = new AddLinkRequest();

        try {
            URL url = new URL(req.getParameter("url"));
            if (url == null || url.equals("")) {
                throw new ParseException("Missing parameter 'url'");
            }
            data.url = url;
        }
        catch(MalformedURLException e) {
            throw new ParseException("Parameter 'url' is malformed");
        }

        String pkg = req.getParameter("packageName");
        if(pkg != null && !pkg.equals("")) {
            data.packageName = pkg;
        }

        String force = req.getParameter("forcePackageName");
        if(force != null && force.equals("true")) {
            data.forcePackageName = true;
        }

        return data;
    }

    protected String getAllowedMethod() {
        return "GET";
    }
}
