package log.reader.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LogReaderUI extends JFrame {
	
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private JButton logFileLoadBtn;
	private JButton saveCsvFile;	
	
	private JFileChooser logFileChooser;
	
	private JTextField loadLogFileFullPath;
	private JTextField findLogName;
	private JTextField findLogName2;
	private JTextField findSeparator1;
	private JTextField findSeparator2;
	private JTextField findLogDateForm;
	
	private JTextArea loadLogData;
		
	private JLabel findLogNameLabel;
	private JLabel findLogNameLabel2;
	private JLabel findSeparator1Label;
	private JLabel findSeparator2Label;
	private JLabel findLogDateFormLabel;
	
	public LogReaderUI() {
		LogReaderUiProcess lrup = new LogReaderUiProcess(this);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		// ------------------------------------------
		// 1. 파일 불러오기 버튼 UI 세팅
		// ------------------------------------------
		logFileLoadBtn = new JButton("파일 불러오기");
		logFileLoadBtn.setSize(100, 100);
		logFileLoadBtn.addActionListener(lrup);

		// ------------------------------------------
		// 2. CSV 저장 버튼 UI 세팅
		// ------------------------------------------
		saveCsvFile = new JButton("CSV 저장");
		saveCsvFile.setSize(100, 100);
		saveCsvFile.addActionListener(lrup);			
		
		// ------------------------------------------
		// 3. 불러온 파일 경로 정보 기재할 UI 세팅
		// ------------------------------------------
		loadLogFileFullPath = new JTextField(25);
		loadLogFileFullPath.setEditable(false);
		
		// ------------------------------------------
		// 4. 찾을 로그정보 기입하는 UI 세팅
		// ------------------------------------------
		findLogName = new JTextField(30);		// 로그 내용 (시작점)		
		//findLogName.setEditable(false);		
		findLogName2 = new JTextField(30);		// 로그 내용 (끝점)
		
		findSeparator1 = new JTextField(30);	// date 구분자1		
		//findSeparator1.setEditable(false);
		
		findSeparator2 = new JTextField(30);	// date 구분자2		
		//findSeparator2.setEditable(false);
		
		findLogDateForm = new JTextField(30);	// log date 형식		
		//findLogDateForm.setEditable(false);
				
		// ------------------------------------------
		// 5. 찾은 로그 발생 간격 기재할 UI 정보 세팅
		// ------------------------------------------
		loadLogData = new JTextArea("", 30,45);
		loadLogData.setEditable(false);
		loadLogData.setText("*** 예시 : \n[2019-12-21T16:46:24,357][INFO ][o.e.p.PluginsService] [DESKTOP-42G58I0] loaded module [aggs-matrix-stats]\n"
				+ "1) 로그내용 : loaded module [aggs-matrix-stats]\n"
				+ "2) 로그일자 구분자1 : [\n"
				+ "3) 로그일자 구분자2 : ]\n\n"
				+ "※  찾고자 하는 로그 일자 구분자가 일자 뒤 문자 하나로 되어있을 경우 구분자1, 구분자2 에 해당 문자를 넣어준다.\n"
				+ "*** 예시 : 2019-12-21 16:46:24.357/[INFO ][o.e.p.PluginsService     ] [DESKTOP-42G58I0] 받은 패킷 : ##1400001\n"
				+ "1) 로그내용 : 받은 패킷 : ##1400001\n"
				+ "2) 로그일자 구분자1 : /\n"
				+ "3) 로그일자 구분자2 : /\n\n");
		JScrollPane scroll = new JScrollPane(loadLogData);
		scroll.setBounds(534, 89, 334, 200);
		
		findLogNameLabel = new JLabel();
		findLogNameLabel.setText("* 로그 내용(시작점) : ");
		findLogNameLabel.setSize(100, 100);
		
		findLogNameLabel2 = new JLabel();
		findLogNameLabel2.setText("* 로그 내용(끝점) : ");
		findLogNameLabel2.setSize(100, 100);
		
		findSeparator1Label  = new JLabel();
		findSeparator1Label.setText("*로그일자 구분자1 : ");
		findSeparator1Label.setSize(100, 100);
		
		findSeparator2Label  = new JLabel();
		findSeparator2Label.setText("*로그일자 구분자2 : ");
		findSeparator2Label.setSize(100, 100);
		
		//findLogDateFormLabel = new JLabel();
		//findLogDateFormLabel.setText("*로그 날짜 출력 형식 : ");
		//findLogDateFormLabel.setSize(100, 100);
		
		// ------------------------------------------
		// 6. Frame 구성 (꾸썽 UI 붙히기)
		// ------------------------------------------
		topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setSize(600, 300);
		//topPanel.setBackground(Color.MAGENTA);		
		topPanel.add(loadLogFileFullPath);
		topPanel.add(logFileLoadBtn);
		topPanel.add(saveCsvFile);
		
		topPanel.add(findLogNameLabel);
		topPanel.add(findLogName);
		
		topPanel.add(findLogNameLabel2);
		topPanel.add(findLogName2);
		
		topPanel.add(findSeparator1Label);
		topPanel.add(findSeparator1);
		topPanel.add(findSeparator2Label);
		topPanel.add(findSeparator2);
		//topPanel.add(findLogDateFormLabel);
		//topPanel.add(findLogDateForm);
		
		centerPanel = new JPanel();
		centerPanel.setSize(500, 200);
		//centerPanel.setLayout(new FlowLayout());
		centerPanel.add(scroll);
		
		bottomPanel = new JPanel();
		bottomPanel.setSize(400, 200);
		//bottomPanel.setBackground(Color.YELLOW);
		//bottomPanel.setLayout(new BorderLayout());
		
		this.setSize(530, 780);
		this.add(topPanel, BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.SOUTH);
		//this.add(bottomPanel, BorderLayout.SOUTH);
		this.setTitle("log Finder");
		this.setResizable(false);
		this.setLocation((screenSize.width-this.getSize().width)/2, (screenSize.height - this.getSize().height)/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	
	public JPanel getTopPanel() {
		return topPanel;
	}

	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}

	public JPanel getBottomPanel() {
		return bottomPanel;
	}

	public void setBottomPanel(JPanel bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	public JButton getLogFileLoadBtn() {
		return logFileLoadBtn;
	}

	public void setLogFileLoadBtn(JButton logFileLoadBtn) {
		this.logFileLoadBtn = logFileLoadBtn;
	}

	public JButton getSaveCsvFile() {
		return saveCsvFile;
	}

	public void setSaveCsvFile(JButton saveCsvFile) {
		this.saveCsvFile = saveCsvFile;
	}

	public JFileChooser getLogFileChooser() {
		return logFileChooser;
	}

	public void setLogFileChooser(JFileChooser logFileChooser) {
		this.logFileChooser = logFileChooser;
	}

	public JTextField getLoadLogFileFullPath() {
		return loadLogFileFullPath;
	}

	public void setLoadLogFileFullPath(JTextField loadLogFileFullPath) {
		this.loadLogFileFullPath = loadLogFileFullPath;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public JTextArea getLoadLogData() {
		return loadLogData;
	}

	public void setLoadLogData(JTextArea loadLogData) {
		this.loadLogData = loadLogData;
	}


	public JTextField getFindLogName() {
		return findLogName;
	}


	public void setFindLogName(JTextField findLogName) {
		this.findLogName = findLogName;
	}


	public JTextField getFindSeparator1() {
		return findSeparator1;
	}


	public void setFindSeparator1(JTextField findSeparator1) {
		this.findSeparator1 = findSeparator1;
	}


	public JTextField getFindSeparator2() {
		return findSeparator2;
	}


	public void setFindSeparator2(JTextField findSeparator2) {
		this.findSeparator2 = findSeparator2;
	}


	public JTextField getFindLogDateForm() {
		return findLogDateForm;
	}


	public void setFindLogDateForm(JTextField findLogDateForm) {
		this.findLogDateForm = findLogDateForm;
	}


	public JTextField getFindLogName2() {
		return findLogName2;
	}


	public void setFindLogName2(JTextField findLogName2) {
		this.findLogName2 = findLogName2;
	}


	public JLabel getFindLogNameLabel() {
		return findLogNameLabel;
	}


	public void setFindLogNameLabel(JLabel findLogNameLabel) {
		this.findLogNameLabel = findLogNameLabel;
	}


	public JLabel getFindLogNameLabel2() {
		return findLogNameLabel2;
	}


	public void setFindLogNameLabel2(JLabel findLogNameLabel2) {
		this.findLogNameLabel2 = findLogNameLabel2;
	}


	public JLabel getFindSeparator1Label() {
		return findSeparator1Label;
	}


	public void setFindSeparator1Label(JLabel findSeparator1Label) {
		this.findSeparator1Label = findSeparator1Label;
	}


	public JLabel getFindSeparator2Label() {
		return findSeparator2Label;
	}


	public void setFindSeparator2Label(JLabel findSeparator2Label) {
		this.findSeparator2Label = findSeparator2Label;
	}


	public JLabel getFindLogDateFormLabel() {
		return findLogDateFormLabel;
	}


	public void setFindLogDateFormLabel(JLabel findLogDateFormLabel) {
		this.findLogDateFormLabel = findLogDateFormLabel;
	}
	
	
}
