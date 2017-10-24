package org.jdownloader.extensions.httpAPI.handlers;


import com.google.gson.JsonSyntaxException;
import org.jdownloader.extensions.httpAPI.LinkController;
import org.jdownloader.extensions.httpAPI.ParseException;
import org.jdownloader.extensions.httpAPI.models.AddLinkRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JDServerPOSTHandler extends BaseHandler {

    public JDServerPOSTHandler(LinkController ctr) {
        super(ctr);
    }

    protected AddLinkRequest parseAddLinkParams(HttpServletRequest req) throws ParseException{
        try {
            AddLinkRequest data = jparser.fromJson(req.getReader(), AddLinkRequest.class);

            if (data.url == null || data.url.equals("")) {
                throw new ParseException("Missing parameter 'url'");
            }

            return data;
        }
        catch(IOException e) {
            throw new ParseException(e.getMessage());
        }
        catch(JsonSyntaxException e) {
            throw new ParseException(e.getMessage());
        }
    }

    protected String getAllowedMethod() {
        return "POST";
    }
}
