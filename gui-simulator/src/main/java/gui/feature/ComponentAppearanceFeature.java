package gui.feature;

import gui.bridge.ComponentWrapper;
import gui.main.CustomGraph;

import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

public class ComponentAppearanceFeature implements mxIEventListener {

	private mxStackLayout layout;

	public ComponentAppearanceFeature(mxGraph graph) {
		this.layout = new mxStackLayout(graph, true, 20, 20, 0, 3);
		graph.setCollapseToPreferredSize(true);
	}

	@Override
	public void invoke(Object obj, mxEventObject e) {
		CustomGraph graph = (CustomGraph) obj;
		mxCell cell = (mxCell) ((Object[]) e.getProperty("cells"))[0];
		switch (e.getName()) {
			case "foldCells":
				// change style (image or shape) + auto layout
				cellsFolded(graph, cell);
				break;
		}
	}

	private void cellsFolded(CustomGraph graph, mxCell cell) {
		if (cell != null && cell.getValue() instanceof ComponentWrapper) {

			ComponentWrapper wrapper = (ComponentWrapper) cell.getValue();
			if (cell.isCollapsed()) {
				cell.setStyle(wrapper.getCollapsedStyle());

				layout.execute(cell);
			}
			else {
				cell.setStyle(wrapper.getExpandedStyle());

				layout.execute(cell);
				graph.updateGroupBounds(new Object[] { cell }, 30, false);
			}

			if (!cell.getParent().getId().equals("1")) {
				layout.execute(cell.getParent());
				graph.updateGroupBounds(new Object[] { cell.getParent() }, 30, false);
			}

		}
	}
}
