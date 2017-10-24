package org.jdownloader.extensions.httpAPI;

import org.appwork.txtresource.Default;
import org.appwork.txtresource.Defaults;
import org.appwork.txtresource.TranslateInterface;

@Defaults(lngs = { "en" })
public interface HttpAPITranslation extends TranslateInterface {
    @Default(lngs = { "en" }, values = { "HTTP API" })
    String title();

    @Default(lngs = { "en" }, values = { "Bundled HTTP server to add new files" })
    String description();
}
