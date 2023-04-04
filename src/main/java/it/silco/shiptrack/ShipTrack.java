package it.silco.shiptrack;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.silco.shiptrack.data.InputData;
import it.silco.shiptrack.data.TrackingResultData;
import it.silco.shiptrack.data.csv.InputDataCsv;
import it.silco.shiptrack.data.csv.TrackingResultCsv;
import it.silco.shiptrack.logic.TrackingExecutor;
import it.silco.shiptrack.model.InputDataTableModel;
import it.silco.shiptrack.model.TrackingResultTableModel;
import it.silco.shiptrack.utils.MiscUtils;
import it.silco.shiptrack.utils.SettingsKeys;
import it.silco.shiptrack.utils.SettingsUtils;

public class ShipTrack extends JPanel {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(ShipTrack.class);

	private SettingsUtils settingsUtils;

	private static Properties localizedLabels;

	private JTabbedPane tabbedPane;

	private final JTable inputDataTable;
	private final JTable trackingResultTable;

	private InputDataTableModel inputDataTableModel;
	private TrackingResultTableModel trackingResultTableModel;

	private JButton addRowBtn;
	private JButton deleteRowsBtn;
	private JButton saveBtn;
	private JButton trackBtn;
	private JButton exportBtn;

	private JLabel loaderLabel;

	private List<InputData> data;

	public ShipTrack() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		localizedLabels = MiscUtils.readProperties("ita.properties");

		settingsUtils = SettingsUtils.getInstance();
		MiscUtils.install(settingsUtils);

		inputDataTable = MiscUtils.buildTable();
		trackingResultTable = MiscUtils.buildTable();
		trackingResultTable.setAutoCreateRowSorter(true);

		// main layout
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(50, 50, 200, 200);
		tabbedPane.setFocusable(false);
		tabbedPane.setFont(MiscUtils.FONT_BOLD);

		JScrollPane inputTableScrollPane = new JScrollPane(inputDataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputTableScrollPane.setFont(MiscUtils.FONT_BOLD);
		tabbedPane.add(inputTableScrollPane, localizedLabels.getProperty("tracking_list"), 0);

		JScrollPane trackingResultScrollPane = new JScrollPane(trackingResultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		trackingResultScrollPane.setFont(MiscUtils.FONT_BOLD);
		tabbedPane.add(trackingResultScrollPane, localizedLabels.getProperty("tracking_result"), 1);

		add(tabbedPane);

		// loader
		JPanel loaderPanel = new JPanel();
		loaderLabel = new JLabel(localizedLabels.getProperty("label_loading"), JLabel.CENTER);
		loaderLabel.setVisible(false);
		loaderLabel.setFont(MiscUtils.FONT_BOLD);
		loaderPanel.add(loaderLabel);
		loaderPanel.setPreferredSize(new Dimension(400, 30));
		loaderPanel.setMaximumSize(loaderPanel.getPreferredSize());
		add(loaderPanel);

		// add new row button
		ActionListener addRowBtnAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = MiscUtils.nextValidId(InputDataCsv.getIds());
				InputData newRow = new InputData(nextId);
				InputDataCsv.addId(nextId);
				inputDataTableModel.addRow(newRow);

				List<int[]> dossierRegChanges = inputDataTableModel.getChanges();
				saveCsvFile(dossierRegChanges, null);
			}
		};
		addRowBtn = MiscUtils.buildButton(localizedLabels.getProperty("btn_add_row"), addRowBtnAL);

		// save button
		ActionListener saveBtnAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<int[]> dossierRegChanges = inputDataTableModel.getChanges();
				saveCsvFile(dossierRegChanges, null);

				logger.info("File saved!");
			}
		};
		saveBtn = MiscUtils.buildButton(localizedLabels.getProperty("btn_save"), saveBtnAL);

		// delete rows button
		ActionListener deleteRowsBtnAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Integer> rows = inputDataTableModel.getSelectedRows();
				InputDataCsv.removeIds(rows);
				inputDataTableModel.removeRows(rows);

				List<int[]> dossierRegChanges = inputDataTableModel.getChanges();
				saveCsvFile(dossierRegChanges, rows);
			}
		};
		deleteRowsBtn = MiscUtils.buildButton(localizedLabels.getProperty("btn_delete_rows"), deleteRowsBtnAL);

		// track button
		ActionListener trackBtnAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loaderLabel.setVisible(true);

				new SwingWorker<Void, String>() {
					@Override
					protected Void doInBackground() throws Exception {
						List<TrackingResultData> result = TrackingExecutor.track(data);
						loadTrackingResult(result);
						tabbedPane.setSelectedIndex(1);
						return null;
					}

					@Override
					protected void done() {
						loaderLabel.setVisible(false);
					}
				}.execute();
			}
		};
		trackBtn = MiscUtils.buildButton(localizedLabels.getProperty("btn_track"), trackBtnAL);

		// export button
		ActionListener exportBtnAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showSaveDialog(null);
				String selectedPath = fileChooser.getSelectedFile().getAbsolutePath() + File.separator;
				logger.info("Selected path: " + selectedPath);

				File selectedDir = new File(selectedPath);

				TrackingResultCsv trc = TrackingResultCsv.getInstance();
				trc.writeCSVfile(trackingResultTableModel, selectedDir);
			}
		};
		exportBtn = MiscUtils.buildButton(localizedLabels.getProperty("btn_export"), exportBtnAL);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(addRowBtn);
		buttonsPanel.add(deleteRowsBtn);
		buttonsPanel.add(saveBtn);
		buttonsPanel.add(trackBtn);
		buttonsPanel.add(exportBtn);
		buttonsPanel.setLayout(new GridLayout());
		buttonsPanel.setVisible(true);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		buttonsPanel.setMaximumSize(buttonsPanel.getPreferredSize());
		add(buttonsPanel);

		setBorder(new EmptyBorder(5, 5, 5, 5));

		InputDataCsv idc = InputDataCsv.getInstance();
		File file = new File(settingsUtils.getSetting(SettingsKeys.WORK_DIRECTORY) + File.separator + MiscUtils.SOURCE_FILENAME);
		data = idc.readCSVfile(file);
		loadInputDataCsv();
	}

	/**
	 * Saves csv file.
	 * 
	 * @param changes
	 */
	private void saveCsvFile(List<int[]> changes, List<Integer> deletedRows) {
		InputDataCsv inputDataCsv = InputDataCsv.getInstance();
		inputDataCsv.writeCSVfile(inputDataTableModel, changes, deletedRows);
	}

	private void loadInputDataCsv() {
		inputDataTableModel = new InputDataTableModel(data, localizedLabels);
		this.inputDataTable.setModel(inputDataTableModel);

		MiscUtils.resizeColumnWidth(inputDataTable, 170);

		logger.info("Input data rows: " + inputDataTableModel.getRowCount());
		logger.info("Input data columns: " + inputDataTableModel.getColumnCount());
	}

	private void loadTrackingResult(List<TrackingResultData> data) {
		trackingResultTableModel = new TrackingResultTableModel(data, localizedLabels);
		this.trackingResultTable.setModel(trackingResultTableModel);

		MiscUtils.resizeColumnWidth(trackingResultTable, 170);

		logger.info("Tracking result rows: " + trackingResultTableModel.getRowCount());
		logger.info("Tracking result columns: " + trackingResultTableModel.getColumnCount());
	}

	private static void showGui() {
		final JFrame mainFrame = new JFrame(ShipTrack.class.getSimpleName());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ShipTrack ordersManager = new ShipTrack();
		mainFrame.setContentPane(ordersManager);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(mainFrame, localizedLabels.getProperty("exit_conf_msg"), localizedLabels.getProperty("exit_conf_title"),
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else if (result == JOptionPane.NO_OPTION) {
					mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				showGui();
			}
		});
	}
}
