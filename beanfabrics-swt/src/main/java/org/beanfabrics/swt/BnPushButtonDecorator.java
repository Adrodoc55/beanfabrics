package org.beanfabrics.swt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.beanfabrics.IModelProvider;
import org.beanfabrics.Link;
import org.beanfabrics.ModelSubscriber;
import org.beanfabrics.Path;
import org.beanfabrics.View;
import org.beanfabrics.event.WeakPropertyChangeListener;
import org.beanfabrics.model.IOperationPM;
import org.beanfabrics.util.ExceptionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Michael Karneim
 */
public class BnPushButtonDecorator extends AbstractDecorator<Button> implements View<IOperationPM>, ModelSubscriber {
	private final Link link = new Link(this);
	private final PropertyChangeListener listener = new WeakPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			refreshUI();
		}
	};

	private Button button;
	private IOperationPM pModel;
    private boolean textSetManually = false;

	/**
	 * Create the BnPushButton2
	 */
	public BnPushButtonDecorator(Button button) {
		super(button);
		checkStyle(button.getStyle());
		this.button = button;
		if (button.getText().length() > 0) {
			textSetManually = true;
		}
		hookControl(button);
	}

	private void checkStyle(int style) {
		if ((style & SWT.PUSH) == 0) {
			throw new IllegalArgumentException("Button must be styled with SWT.PUSH");
		}
	}

	/**
	 * Create the BnPushButton2
	 *
	 * @param parent
	 * @param style
	 *            is ignored
	 */
	public BnPushButtonDecorator(Composite parent, int style) {
		this(new Button(parent, style));
	}

	/**
	 * Create the BnPushButton2
	 *
	 * @param parent
	 */
	public BnPushButtonDecorator(Composite parent) {
		this(new Button(parent, SWT.PUSH));
	}

	protected void hookControl(Button button) {
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent evt) {
				executeOperation();
			}

			public void widgetDefaultSelected(SelectionEvent evt) {
				// ignore
			}
		});
	}

	/** {@inheritDoc} */
	public IOperationPM getPresentationModel() {
		return pModel;
	}

	/** {@inheritDoc} */
	public void setPresentationModel(IOperationPM pModel) {
		if (this.pModel != null) {
			this.pModel.removePropertyChangeListener(listener);
		}
		this.pModel = pModel;
		if (this.pModel != null) {
			this.pModel.addPropertyChangeListener(listener);
		}
		refreshUI();
	}

	protected void refreshUI() {
		refreshText();
		refreshEnabled();
		refreshTooltip();
	}

	private void refreshText() {
		if (textSetManually == false) {
			if (pModel != null && pModel.getTitle() != null) {
				button.setText(pModel.getTitle());
			} else {
				button.setText("");
			}
		}
    }

	protected void refreshEnabled() {
		if (pModel != null) {
			button.setEnabled(pModel.isEnabled());
		} else {
			button.setEnabled(false);
		}
	}

	protected void refreshTooltip() {
		if (pModel != null) {
			if (pModel.isValid() == false) {
				button.setToolTipText(pModel.getValidationState().getMessage());
			} else {
				button.setToolTipText(pModel.getDescription());
			}
		} else {
			button.setToolTipText(null);
		}
	}

	private void executeOperation() {
		try {
			if (pModel != null) {
				pModel.execute();
			}
		} catch (Throwable e) {
			ExceptionUtil.getInstance().handleException("", e);
		}
	}

	/** {@inheritDoc} */
	public void setModelProvider(IModelProvider provider) {
		this.link.setModelProvider(provider);
	}

	/** {@inheritDoc} */
	public IModelProvider getModelProvider() {
		return link.getModelProvider();
	}

	/** {@inheritDoc} */
	public void setPath(Path path) {
		this.link.setPath(path);
	}

	/** {@inheritDoc} */
	public Path getPath() {
		return link.getPath();
	}

	/**
	 * @deprecated use {@link #getControl()}
	 */
	public Button getButton() {
		return getControl();
	}
}