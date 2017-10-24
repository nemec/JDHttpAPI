package org.jdownloader.extensions.httpAPI;

import org.appwork.storage.config.handler.BooleanKeyHandler;
import org.appwork.storage.config.handler.IntegerKeyHandler;
import org.appwork.storage.config.handler.StorageHandler;
import org.appwork.storage.config.handler.StringKeyHandler;
import org.appwork.utils.Application;
import org.appwork.storage.config.ConfigUtils;
import org.appwork.storage.config.JsonConfig;

public class CFG_HTTPAPI {
    public static void main(String[] args) {
        ConfigUtils.printStaticMappings(HttpAPIConfig.class, "Application.getResource(\"cfg/\" + " + HttpAPIExtension.class.getSimpleName() + ".class.getName())");
    }

    public static final HttpAPIConfig                   CFG             = JsonConfig.create(Application.getResource("cfg/" + HttpAPIExtension.class.getName()), HttpAPIConfig.class);

    public static final StorageHandler<HttpAPIConfig>   SH              = (StorageHandler<HttpAPIConfig>) CFG._getStorageHandler();

    public static final BooleanKeyHandler               ENABLED         = SH.getKeyHandler("Enabled", BooleanKeyHandler.class);

    public static final IntegerKeyHandler               PORT            = SH.getKeyHandler("Port", IntegerKeyHandler.class);

    public static final BooleanKeyHandler               ALLOW_GET       = SH.getKeyHandler("AllowGet", BooleanKeyHandler.class);

    public static final BooleanKeyHandler               USE_PASSWORD    = SH.getKeyHandler("UsePassword", BooleanKeyHandler.class);

    public static final StringKeyHandler                PASSWORD        = SH.getKeyHandler("Password", StringKeyHandler.class);
}
