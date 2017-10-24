package org.jdownloader.extensions.httpAPI;

import jd.plugins.ExtensionConfigInterface;
import org.appwork.storage.config.annotations.AboutConfig;
import org.appwork.storage.config.annotations.DefaultBooleanValue;
import org.appwork.storage.config.annotations.DefaultIntValue;

public interface HttpAPIConfig extends ExtensionConfigInterface {

    @AboutConfig
    @DefaultIntValue(8297)
    int getPort();
    void setPort(int port);

    @AboutConfig
    @DefaultBooleanValue(false)
    boolean getAllowGet();
    void setAllowGet(boolean enabled);

    @AboutConfig
    @DefaultBooleanValue(false)
    boolean getUsePassword();
    void setUsePassword(boolean enabled);

    @AboutConfig
    String getPassword();
    void setPassword(String password);
}
