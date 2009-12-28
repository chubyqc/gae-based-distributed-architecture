package chubyqc.gaeDistributed.server.client.widgets;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

public class Loading extends AbstractWidget {
	
	private static final String STYLE_CONTAINER = "loadingPanel";
	private static final String STYLE_POSITION = "position";
	private static final String STYLE_ABSOLUTE = "absolute";
	
	private static final String LOADING_TEXT = "<h1>Loading ...</h1>";

	@Override
	protected void init() {
		Grid grid = new Grid(1, 1);
		grid.setWidget(0, 0, new HTML(LOADING_TEXT));
		grid.addStyleName(STYLE_CONTAINER);
		addStyleName(STYLE_CONTAINER);
		grid.getElement().getStyle().setProperty(STYLE_POSITION, STYLE_ABSOLUTE);
		getElement().getStyle().setProperty(STYLE_POSITION, STYLE_ABSOLUTE);
		add(grid);
	}

}
