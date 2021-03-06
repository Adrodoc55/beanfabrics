/*
 * Beanfabrics Framework Copyright (C) by Michael Karneim, beanfabrics.org
 * Use is subject to license terms. See license.txt.
 */
package org.beanfabrics.event;

import org.beanfabrics.model.Options;

/**
 * The listener interface for receiving {@link ListEvent}s.
 * <p>
 * A listener object created from this class can be registered with a
 * {@link Options} using the {@link Options#addOptionsListener(OptionsListener)}
 * method.
 * 
 * @author Michael Karneim
 */
public interface OptionsListener {
    /**
     * Invoked after some change has occured.
     * 
     * @param evt
     */
    public void changed(OptionsEvent evt);
}