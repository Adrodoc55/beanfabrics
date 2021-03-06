package org.beanfabrics.swing.customizer.table;

import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.beanfabrics.context.Context;
import org.beanfabrics.meta.PathNode;
import org.beanfabrics.swing.customizer.util.CustomizerUtil;

public class ColumnListConfigurationConstroller {
    private ColumnListConfigurationPM presentationModel;
    private ColumnListConfigurationDialog view;
    private Context context;
    private PathNode rootPathInfo;

    public ColumnListConfigurationConstroller(Context context, PathNode rootPathInfo) {
        this.context = context;
        this.rootPathInfo = rootPathInfo;
    }

    public ColumnListConfigurationPM getPresentationModel() {
        if (presentationModel == null) {
            presentationModel = new ColumnListConfigurationPM();
            presentationModel.getContext().addParent(context);
            presentationModel.setRootPathInfo(rootPathInfo);
        }
        return presentationModel;
    }

    public ColumnListConfigurationDialog getView() {
        if (view == null) {
            view = ColumnListConfigurationDialog.create(getRootWindow());
            view.setPresentationModel(getPresentationModel());
            view.setSize(700, 400);
            view.setLocationRelativeTo(view.getParent());
            view.setIconImage(getBeanfabricsIcon());
        }
        return view;
    }

    private Window getRootWindow() {
        return CustomizerUtil.locateRootWindow(context);
    }
    
    private BufferedImage getBeanfabricsIcon() {
        try {
            return ImageIO.read(getClass().getResource("/org/beanfabrics/swing/beanfabrics48c.png"));
        } catch (Exception e) {
            return null;
        }
    }
}
