import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFileChooser;

public class GUI extends ApplicationWindow {
	
	ParseDB parseDB = null;
	filesParser FP = null;
	FindLocations FL = null;
	CalcLocation CL = null;
	private Text txtC;
	private Text txtC_1;
	private Text text;
	/**
	 * @wbp.nonvisual location=14,479
	 */
	private final JFileChooser fileChooser = new JFileChooser();
	private Text text_1;
	private Text text_2;
	/**
	 * Create the application window.
	 */
	public GUI() {
		super(null);
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		
		text_1 = new Text(container, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Label lblEnterGpsLogs = new Label(container, SWT.NONE);
		lblEnterGpsLogs.setText("Enter GPS Logs Folder:");
		new Label(container, SWT.NONE);
		
		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					txtC.setText(fileChooser.getSelectedFile().getPath().replaceAll("\\\\","\\\\\\\\"));
				}

			}
		});
		btnBrowse.setText("Browse");
		
		txtC = new Text(container, SWT.BORDER);
		txtC.setText("C:\\");
		txtC.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblEnterOutputFolder = new Label(container, SWT.NONE);
		lblEnterOutputFolder.setText("Enter Output Folder:");
		new Label(container, SWT.NONE);
		
		Button btnBroes = new Button(container, SWT.NONE);
		btnBroes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					txtC_1.setText(fileChooser.getSelectedFile().getPath().replaceAll("\\\\","\\\\\\\\"));
				}
			}
		});
		btnBroes.setText("Browse");
		
		txtC_1 = new Text(container, SWT.BORDER);
		txtC_1.setText("C:\\");
		txtC_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Button btnInfo = new Button(container, SWT.NONE);
		btnInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnInfo.getMenu().setVisible(true);
			}
		});
		btnInfo.setText("STEPS");
		
		Menu menu = new Menu(btnInfo);
		btnInfo.setMenu(menu);
		
		MenuItem mntmSelectAFolder = new MenuItem(menu, SWT.NONE);
		mntmSelectAFolder.setText("Select a folder with gps logs of wifi hotspots in our module");
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("Create a ComboFile of all Logs");
		
		MenuItem mntmThisFileWill = new MenuItem(menu, SWT.NONE);
		mntmThisFileWill.setText("this file will be used to make calculation of a MAC address location");
		
		MenuItem mntmForABest = new MenuItem(menu, SWT.NONE);
		mntmForABest.setText("for a best calculation, enter only MAC in arguments");
		
		MenuItem mntmForWeightedCalculation = new MenuItem(menu, SWT.NONE);
		mntmForWeightedCalculation.setText("for weighted calculation enter a MAC and number of points");
		
		MenuItem mntmForProvancyCalculation = new MenuItem(menu, SWT.NONE);
		mntmForProvancyCalculation.setText("for provancy calculation enter MAC address and signals as followed");
		
		MenuItem mntmMacSignalMac = new MenuItem(menu, SWT.NONE);
		mntmMacSignalMac.setText("MAC1 Signal1 MAC2 Signal2 MAC3 Signal3");
		
		Button btnInfo_1 = new Button(container, SWT.NONE);
		btnInfo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				text_1.setText("");
				if (FP != null)
				{
					text_1.setText("lines in all files: " + FP.getNumOfLines());
				}
				if (FL != null)
				{
					text_1.setText(text_1.getText() + " num of matches: " + FL.getNumOfLines());
				}
				if (CL != null)
				{
					text_1.setText(text_1.getText() + "num of APS: " + parseDB.getNumOfAPS());
				}
			}
		});
		btnInfo_1.setText("INFO");
		
		Button btnVreateComboFile = new Button(container, SWT.NONE);
		btnVreateComboFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				try {
					parseDB = new ParseDB(txtC.getText());
					//parseDB.printMat();
					parseDB.createCSV(txtC_1.getText() + "\\\\");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVreateComboFile.setText("Create Combo File");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Button btnCalcLocation = new Button(container, SWT.NONE);
		btnCalcLocation.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				try {
					String args[] = text.getText().split("\\s+");
					
					//System.out.println(Arrays.toString(args));
					
					FP = new filesParser(txtC_1.getText());
					FL = new FindLocations(FP.getString());
					if (args.length == 1)
					{
						CL = new CalcLocation(FL.findLocationsOfMAC(args[0]));
						text_2.setText(Arrays.toString(CL.calcLocation()));
					}
					else if (args.length == 2)
					{
						CL = new CalcLocation(FL.findLocationsOfMAC(args[0]));
						text_2.setText(Arrays.toString(CL.calcLocation(Integer.parseInt(args[1]))));
					}
					else if (args.length == 6)
					{
						String MACS[][] = new String[(args.length)/2][2];
						int counter = 0;
						for (int j = 0; j < args.length; j = j + 2)
						{
							MACS[counter][0] = args[j];
							MACS[counter][1] = args[j+1];
							counter++;
						}
						CL = new CalcLocation(FL.findLocationsOfMAC(MACS));
						text_2.setText(Arrays.toString(CL.calcLocation(MACS)));
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCalcLocation.setText("calc location!");
		new Label(container, SWT.NONE);
		
		Label lblArguments = new Label(container, SWT.NONE);
		lblArguments.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblArguments.setText("Arguments:");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblResults = new Label(container, SWT.NONE);
		lblResults.setText("Results:");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		text_2 = new Text(container, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(container, SWT.NONE);

		return container;
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			GUI window = new GUI();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Location Calculator");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 483);
	}
}
