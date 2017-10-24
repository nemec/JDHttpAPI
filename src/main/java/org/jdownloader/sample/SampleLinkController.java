package org.jdownloader.sample;

import org.jdownloader.extensions.httpAPI.LinkController;

public class SampleLinkController implements LinkController {
    public void AddLink(String url) {
        System.out.println("Adding URL " + url);
    }

    public void AddLink(String url, String packageName, boolean forcePackageName) {
        if(forcePackageName) {
            System.out.println("Adding URL " + url + " with forced package " + packageName);
        }
        else {
            System.out.println("Adding URL " + url + " in package " + packageName);
        }
    }
}
