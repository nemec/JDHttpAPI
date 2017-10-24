package org.jdownloader.extensions.httpAPI;

public interface LinkController {

    public void AddLink(String url);

    public void AddLink(String url, String packageName, boolean forcePackageName);
}
