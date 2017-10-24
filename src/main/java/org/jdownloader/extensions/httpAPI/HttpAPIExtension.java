package org.jdownloader.extensions.httpAPI;

import jd.controlling.linkcollector.LinkCollector;
import org.appwork.storage.config.ValidationException;
import org.appwork.utils.Application;
import jd.plugins.AddonPanel;
import org.appwork.storage.config.events.GenericConfigEventListener;
import org.appwork.storage.config.handler.KeyHandler;
import org.appwork.utils.logging2.LogSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.jdownloader.extensions.AbstractExtension;
import org.jdownloader.extensions.StartException;
import org.jdownloader.extensions.StopException;
import org.jdownloader.extensions.httpAPI.handlers.AjaxHandler;
import org.jdownloader.extensions.httpAPI.handlers.AuthorizationHandler;
import org.jdownloader.extensions.httpAPI.handlers.JDServerGETHandler;
import org.jdownloader.extensions.httpAPI.handlers.JDServerPOSTHandler;
import org.jdownloader.gui.IconKey;
import org.jdownloader.logging.LogController;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HttpAPIExtension extends AbstractExtension<HttpAPIConfig, HttpAPITranslation> implements Runnable, GenericConfigEventListener<Integer> {
    private LogSource logger;
    private HttpAPIConfigPanel configPanel;
    private Server server;

    @Override
    protected void initExtension() throws StartException {
        logger = LogController.CL(HttpAPIExtension.class);
        if (!Application.isHeadless()) {
            configPanel = new HttpAPIConfigPanel(this);
        }
    }

    @Override
    protected void start() throws StartException {
        CFG_HTTPAPI.PORT.getEventSender().addListener(this, true);
        startServer();
    }

    protected void stop() throws StopException {
        //ContainerPluginController.getInstance().remove(plugin.getAndSet(null));
        stopServer();
    }

    @Override
    public String getIconKey() {
        return IconKey.ICON_FOLDER_ADD;
    }


    public void run() {
        startServer();
    }

    @Override
    public boolean isHeadlessRunnable() {
        return true;
    }

    @Override
    public boolean hasConfigPanel() {
        return true;
    }

    @Override
    public HttpAPIConfigPanel getConfigPanel() {
        return configPanel;
    }

    @Override
    public AddonPanel<HttpAPIExtension> getGUI() {
        return null;
    }

    @Override
    public String getDescription() {
        return T.description();
    }

    public void onConfigValidatorError(KeyHandler<Integer> keyHandler, Integer invalidValue, ValidationException validateException) {
    }

    public synchronized void onConfigValueModified(KeyHandler<Integer> keyHandler, Integer newValue) {
        stopServer();
        startServer();
    }

    private void startServer() {
        HttpAPIConfig cfg = getSettings();
        server = new Server(cfg.getPort());
        HandlerList lst = new HandlerList();
        LinkController ctr = new JDLinkController(LinkCollector.getInstance());
        if(cfg.getUsePassword() && cfg.getPassword() != null && !cfg.getPassword().equals("")) {
            lst.addHandler(new AuthorizationHandler(cfg.getPassword()));
        }
        lst.addHandler(new AjaxHandler(cfg.getAllowGet()));
        if(cfg.getAllowGet()) {
            lst.addHandler(new JDServerGETHandler(ctr));
        }
        lst.addHandler(new JDServerPOSTHandler(ctr));
        server.setHandler(lst);
        try {
            server.start();
        }
        catch(Exception e) {
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    private void stopServer() {
        if(server != null) {
            try {
                server.stop();
            }
            catch(Exception e) {
                logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
            }
        }
        server = null;
    }
}
