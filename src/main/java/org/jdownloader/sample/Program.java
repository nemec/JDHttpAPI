package org.jdownloader.sample;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.jdownloader.extensions.httpAPI.LinkController;
import org.jdownloader.extensions.httpAPI.handlers.AjaxHandler;
import org.jdownloader.extensions.httpAPI.handlers.AuthorizationHandler;
import org.jdownloader.extensions.httpAPI.handlers.JDServerGETHandler;
import org.jdownloader.extensions.httpAPI.handlers.JDServerPOSTHandler;


public class Program {
    public static void main(String[] args) {
        int port = 8297;
        HandlerList lst = new HandlerList();

        String password = null;//"pass";
        Server server = new Server(port);
        JDServerPOSTHandler hnd;
        LinkController ctr = new SampleLinkController();
        if(password != null) {
            lst.addHandler(new AuthorizationHandler(password));
        }
        lst.addHandler(new AjaxHandler(true));
        lst.addHandler(new JDServerGETHandler(ctr));
        lst.addHandler(new JDServerPOSTHandler(ctr));
        server.setHandler(lst);
        try {
            server.start();
            server.join();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
