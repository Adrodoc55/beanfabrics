package org.beanfabrics.swing.customizer.util;

import static org.junit.Assert.assertEquals;

import org.beanfabrics.ModelProvider;
import org.beanfabrics.Path;
import org.beanfabrics.meta.PathNode;
import org.beanfabrics.model.PresentationModel;
import org.junit.Test;

public class CustomizerUtil_GetSubscribedNode_Test {

    @Test
    public void testViewIsUnbound() {
        // Given:
        SampleListPanel comp = new SampleListPanel();

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(null, actual);
    }

    @Test
    public void testViewIsBoundToPM() {
        // Given:
        SampleListPanel comp = new SampleListPanel();
        SampleListPM<SampleRowPM> pm = new SampleListPM<SampleRowPM>();
        comp.setPresentationModel(pm);

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(null, actual);
    }

    @Test
    public void testViewIsBoundToExtendedPM() {
        // Given:
        SampleListPanel comp = new SampleListPanel();
        ExtendedSampleListPM<ExtendedSampleRowPM> pm = new ExtendedSampleListPM<ExtendedSampleRowPM>();
        comp.setPresentationModel(pm);
        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(null, actual);
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredPMType() {
        // Given:
        ModelProvider provider = new ModelProvider();
        provider.setPresentationModelType(ExtendedSampleListPM.class);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(ExtendedSampleListPM.class, actual.getTypeInfo().getJavaType());
    }

    @Test
    public void testViewIsBoundToProviderHavingNoConfiguredPMType() {
        // Given:
        ModelProvider provider = new ModelProvider();
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(PresentationModel.class, actual.getTypeInfo().getJavaType());
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredPM() {
        // Given:
        ModelProvider provider = new ModelProvider();
        PresentationModel pm = new SampleListPM<SampleRowPM>();
        provider.setPresentationModel(pm);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(SampleListPM.class, actual.getTypeInfo().getJavaType());
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredExtendedPM() {
        // Given:
        ModelProvider provider = new ModelProvider();
        PresentationModel pm = new ExtendedSampleListPM<ExtendedSampleRowPM>();
        provider.setPresentationModel(pm);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(ExtendedSampleListPM.class, actual.getTypeInfo().getJavaType());
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredOwnerPMType() {
        // Given:
        ModelProvider provider = new ModelProvider();
        provider.setPresentationModelType(OwnerPM.class);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this.list"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(SampleListPM.class, actual.getTypeInfo().getJavaType());
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredOwnerPM() {
        // Given:
        ModelProvider provider = new ModelProvider();
        PresentationModel pm = new OwnerPM();
        provider.setPresentationModel(pm);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this.list"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(SampleListPM.class, actual.getTypeInfo().getJavaType());
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredBadPath() {
        // Given:
        ModelProvider provider = new ModelProvider();
        PresentationModel pm = new OwnerPM();
        provider.setPresentationModel(pm);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this.xxx"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(null, actual); // TODO: we could expect SampleListPM here
    }

    @Test
    public void testViewIsBoundToProviderHavingConfiguredBadPath2() {
        // Given:
        ModelProvider provider = new ModelProvider();
        provider.setPresentationModelType(OwnerPM.class);
        SampleListPanel comp = new SampleListPanel();
        comp.setModelProvider(provider);
        comp.setPath(new Path("this.xxx"));

        // When:
        PathNode actual = CustomizerUtil.getSubscribedNode(comp);

        // Then:
        assertEquals(null, actual); // TODO: we could expect SampleListPM here
    }

}
