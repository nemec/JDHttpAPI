package org.jdownloader.extensions.httpAPI;

import jd.controlling.linkcollector.LinkCollectingJob;
import jd.controlling.linkcollector.LinkCollector;
import jd.controlling.linkcollector.LinkOrigin;
import jd.controlling.linkcollector.LinkOriginDetails;
import jd.controlling.linkcrawler.CrawledLink;
import jd.controlling.linkcrawler.CrawledLinkModifier;
import jd.controlling.linkcrawler.modifier.PackageNameModifier;

public class JDLinkController implements LinkController {
    private LinkCollector collector;

    public JDLinkController(LinkCollector collector){
        this.collector = collector;
    }

    public void AddLink(String url) {
        LinkCollectingJob job = new LinkCollectingJob(LinkOriginDetails.getInstance(LinkOrigin.EXTENSION, "HTTPAPI"), url);
        collector.addCrawlerJob(job);
    }

    public void AddLink(String url, String packageName, boolean forcePackageName) {
        LinkCollectingJob job = new LinkCollectingJob(LinkOriginDetails.getInstance(LinkOrigin.EXTENSION, "HTTPAPI"), url);
        job.addPostPackagizerModifier(new PackageNameModifier(packageName, forcePackageName));
        collector.addCrawlerJob(job);
    }
}
