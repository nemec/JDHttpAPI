package org.jdownloader.extensions.httpAPI;


import org.jdownloader.extensions.ExtensionConfigPanel;

import jd.gui.swing.jdgui.views.settings.panels.advanced.AdvancedConfigTableModel;
import jd.gui.swing.jdgui.views.settings.panels.advanced.AdvancedTable;

public class HttpAPIConfigPanel extends ExtensionConfigPanel<HttpAPIExtension> {

    private AdvancedConfigTableModel model;

    public HttpAPIConfigPanel(HttpAPIExtension extension){
        super(extension);

        add(new AdvancedTable(model= new AdvancedConfigTableModel("HttpAPIConfigPanel"){
            @Override
            public void refresh(String filterText){ _fireTableStructureChanged(register(), true);}
        }));
        model.refresh("HttpAPIConfigPanel");
    }

    @Override
    public void save() {
    }

    @Override
    public void updateContents() {

    }
}
