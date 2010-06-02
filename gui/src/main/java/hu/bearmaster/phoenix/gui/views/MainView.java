package hu.bearmaster.phoenix.gui.views;

import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.services.PersistenceService;
import hu.bearmaster.phoenix.common.util.DiscItemType;
import hu.bearmaster.phoenix.gui.components.tables.DiscItemTable;
import hu.bearmaster.phoenix.gui.executor.EditDiscExecutor;
import hu.bearmaster.phoenix.gui.executor.EditDiscItemExecutor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;
import org.springframework.binding.value.ValueModel;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.list.ListSelectionValueModelAdapter;
import org.springframework.richclient.list.ListSingleSelectionGuard;
import org.springframework.richclient.tree.FocusableTreeCellRenderer;

public class MainView extends AbstractView {
	
	private static final Logger LOG = Logger.getLogger(MainView.class);

	private PersistenceService persistenceService;
	private DefaultTreeModel catalogueModel;
	private JTree catalogueTree;
	private DiscItemTable itemsTable;
	
	private final EditDiscExecutor discPropertyExecutor = new EditDiscExecutor(this);
	private final EditDiscItemExecutor discItemPropertyExecutor = new EditDiscItemExecutor(this);
	
	private PropertiesExecutor propertiesExecutor = new PropertiesExecutor(); 
	
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@Override
	protected JComponent createControl() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Phoenix is alive!"), BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		createCatalogueTree();
		
		JScrollPane editorScrollPane = new JScrollPane(catalogueTree);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//TODO stupid hack?!
		Dimension prefSize = new Dimension(550, editorScrollPane.getPreferredSize().height);
		editorScrollPane.setPreferredSize(prefSize);
		prefSize.width = 350;
		editorScrollPane.setMinimumSize(prefSize);
		splitPane.setLeftComponent(editorScrollPane);
		
		itemsTable = new DiscItemTable(Collections.EMPTY_LIST);

		JScrollPane tableScrollPane = new JScrollPane(itemsTable.getControl());
		tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane.setRightComponent(tableScrollPane);

		
		CommandGroup popup = new CommandGroup();
		popup.add((ActionCommand) getWindowCommandManager().getCommand("editDiscItemCommand", ActionCommand.class));
		itemsTable.setPopupCommandGroup(popup);
		
		ValueModel selectionHolder = new ListSelectionValueModelAdapter(itemsTable.getSelectionModel());
		new ListSingleSelectionGuard(selectionHolder, discItemPropertyExecutor);
		itemsTable.setDoubleClickHandler(discItemPropertyExecutor);
		//updateCommands();
		
		panel.add(splitPane, BorderLayout.CENTER);
		
		propertiesExecutor.setEnabled(true);
		
		return panel;
	}

	private void createCatalogueTree() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(getMessage("catalogue.root.label"));
		
		List<Disc> discs= persistenceService.getAllDiscs();
		
		for (Disc d : discs) {
			addDiscToNode(rootNode, d);
		}
		
		catalogueModel = new DefaultTreeModel(rootNode);
		catalogueTree = new JTree(catalogueModel);
		
		catalogueTree.setShowsRootHandles(true);
		catalogueTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		catalogueTree.setCellRenderer(getCellRenderer());
		
		catalogueTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) catalogueTree
						.getLastSelectedPathComponent();
				if (node == null) {
					return;
				}
				if(node.isRoot()) {
					getStatusBar().setMessage(node.getUserObject().toString() + "(" + node.getChildCount() + ")");
				}
				else {
					Object userObj = node.getUserObject();
					if (userObj instanceof Disc) {
						Disc disc = (Disc) node.getUserObject();
						getStatusBar().setMessage(disc.getName() + " - " + disc.getVolumeName() + " (" + node.getChildCount() + ")");
						//TODO caching?
						itemsTable.setItemList(persistenceService.loadDiscItems(disc));
						return;
					}
					if (userObj instanceof DiscItem) {
						DiscItem discItem = (DiscItem)userObj;
						int row = itemsTable.getRowOfItem(discItem);
						itemsTable.showCell(row, 0);
					}
					
				}
			}

		});
		
		catalogueTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && propertiesExecutor.isEnabled()) {
                	propertiesExecutor.execute();
                }
            }
        });
	}
	
	private void addDiscToNode(DefaultMutableTreeNode rootNode, Disc disc) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(disc);
		rootNode.add(node);
		
		List<DiscItem> discItems = persistenceService.loadDiscItems(disc);
		
		for (DiscItem item : discItems) {
			addDiscItemToNode(node, item);
		}
	}

	private void addDiscItemToNode(DefaultMutableTreeNode rootNode, DiscItem item) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(item);
		rootNode.add(node);
	}

	public TreeCellRenderer getCellRenderer() {
		return treeCellRenderer;
	}

	private DefaultTreeCellRenderer treeCellRenderer = new FocusableTreeCellRenderer() {
		//generated
		private static final long serialVersionUID = -8197423894156689727L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.isRoot()) {
				this.setIcon(getIconSource().getIcon("root.icon"));
			} else {
				Object userObject = node.getUserObject();
				Icon icon = null;
				if (userObject instanceof Disc) {
					Disc d = (Disc) userObject;
					this.setText(d.getName() + " [" + d.getType() + "]");
					icon = getIconSource().getIcon(d.getType().getName().toLowerCase() + ".icon");
				} else {
					if ( userObject instanceof DiscItem ) {
						DiscItem di = (DiscItem) userObject;
						this.setText(di.getName());
						icon = getIconSource().getIcon(DiscItemType.determineType(di.getExtension()).toString().toLowerCase() + ".icon");
					}
				}
				if(!leaf) {					
					if (icon == null) {
						icon = getIconSource().getIcon("parent.icon");
					}
					this.setIcon(icon);
				} else {
					if (icon == null) {
						icon = getIconSource().getIcon("child.icon");
					}
					this.setIcon(icon);
				}
			}
			return this;
		}
	};

	public DefaultMutableTreeNode getSelectedDiscNode() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)catalogueTree.getLastSelectedPathComponent();
		if (node == null || !(node.getUserObject() instanceof Disc)) {
			return null;
		}
		return node;
	}
	
	public Disc getSelectedDisc() {
		DefaultMutableTreeNode node = getSelectedDiscNode();
		if (node != null) {
			return (Disc) node.getUserObject();
		}		
		return null;
	}
	
	public DefaultMutableTreeNode getSelectedDiscItemNode() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)catalogueTree.getLastSelectedPathComponent();
		if (node == null || !(node.getUserObject() instanceof DiscItem)) {
			return null;
		}
		return node;
	}
	
	public DiscItem getSelectedDiscItem() {
		DefaultMutableTreeNode node = getSelectedDiscItemNode();
		if (node != null) {
			return (DiscItem) node.getUserObject();
		}
		return getSelectedDiscItemInTable();
	}
	
	public DiscItem getSelectedDiscItemInTable() {
		return itemsTable.getSelectedItem();
	}
	
	private class PropertiesExecutor extends AbstractActionCommandExecutor {
		public void execute() {
            if (getSelectedDisc() != null) {
                discPropertyExecutor.execute();
            }
            else {
                discItemPropertyExecutor.execute();
            }
        }
	}
	
	@Override
	protected void registerLocalCommandExecutors(PageComponentContext context) {
		context.register(GlobalCommandIds.PROPERTIES, propertiesExecutor);
		context.register("editDiscItemCommand", discItemPropertyExecutor);
		/*
		context.register("newItemCommand", newAccountExecutor);
		context.register("removeFiltersCommand", removeFilterExecutor);
  		context.register("filterAccountRangeCommand", filterExecutor);
  		context.register("searchAccountCommand", null);
  		*/
		
	}
}
