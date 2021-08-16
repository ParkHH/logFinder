package log.reader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import log.reader.core.CreateCSVFile;
import log.reader.core.FileReader;
import log.reader.core.FileReaderImpl;

public class LogReaderUiProcess implements ActionListener {
	String filePath = "";					// 읽을 로그파일 경로
	String fileName = "";					// 읽을 로그파일명
	String logText = "";					// 조회할 로그 문구
	String separator1 = "";					// 로그 일시를 감싸는 구분자 (시작)
	String separator2 = "";					// 로그 일시를 감싸는 구분자 (끝)
	
	String csvFilePath = "C:/csvTest";		// csv 저장시 사용할 저장경로
	String csvFileName = "testCsv";			// 저장할 csv 파일명
	
	LogReaderUI logReaderUi;				// 응용프로그램 UI Object
	
	List<Long> diffTimeList = null;			// 검색 대상 로그 일시를 저장하는 리스트
	public static List<String> readLogLineData = new ArrayList<String>();	// 검색 대상 로그 문구를 포함하는 라인의 로그 텍스트를 저장하는 리스트
	
	/**
	 * 본 Class 생성자 UI Class 를 받아와 보관
	 * @param logReaderUi
	 */
	public LogReaderUiProcess(LogReaderUI logReaderUi) {
		this.logReaderUi = logReaderUi;
	}

	
	/**
	 * 버튼 클릭시 버튼의 종류가 어떤것인지 구분 한 후에 버튼 동작 로직 동작
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object bt_value = (JButton) e.getSource();
		button_Action(bt_value);
	}
	
	/**
	 * 버튼 클릭시 동작하는 EventHandler Method
	 * 버튼 객체의 값을 먼저 확인 후 각 버튼에 맞는 동작을 수행
	 * @param bt_value
	 */
	public void button_Action(Object bt_value) {		
		if(bt_value == logReaderUi.getLogFileLoadBtn()) {               		// ******************* 파일 불러오기 버튼을 눌렀을 경우 동작
			//System.out.println(">>>> 로그파일 불러오기 버튼!");
			// File Chooser Open 하고 선택 파일 경로 받아와서 Log 읽기 동작 진행
			// 읽은 결과를 ui 내부에 미리보기로 뿌려주기
			String logText=logReaderUi.getFindLogName().getText();
			String logText2=logReaderUi.getFindLogName2().getText();
			String separator1=logReaderUi.getFindSeparator1().getText();
			String separator2=logReaderUi.getFindSeparator2().getText();
			String logDateForm=logReaderUi.getFindLogDateForm().getText();
			
			// 파일 불러오기 작업 전 필수 입력값 입력 여부 체크
			if(logText != null) {
				if(logText.equals("")) {
					JOptionPane.showMessageDialog(null, "조회 로그 내용(시작점)을 입력해주세요.");
					logReaderUi.getFindLogName().requestFocus();
					logReaderUi.setLogFileChooser(null);
					return;
				}
			}
			if(logText2 != null) {
				if(logText2.equals("")) {
					JOptionPane.showMessageDialog(null, "조회 로그 내용(끝점)을 입력해주세요.");
					logReaderUi.getFindLogName2().requestFocus();
					logReaderUi.setLogFileChooser(null);
					return;
				}
			}
			if(separator1 != null) {
				if(separator1.equals("")) {
					JOptionPane.showMessageDialog(null, "로그 발생일자를 감싸는 구분자 시작 문자를 입력해주세요.");
					logReaderUi.getFindSeparator1().requestFocus();
					logReaderUi.setLogFileChooser(null);
					return;
				}
			}
			if(separator2 != null) {
				if(separator2.equals("")) {
					JOptionPane.showMessageDialog(null, "로그 발생일자를 감싸는 구분자 끝 문자를 입력해주세요.");
					logReaderUi.getFindSeparator2().requestFocus();
					logReaderUi.setLogFileChooser(null);
					return;
				}
			}
			
			
			JFileChooser fc = logReaderUi.getLogFileChooser();
			
			if(fc == null) {
				fc = new JFileChooser();
				fc.setSize(300, 300);
				logReaderUi.setLogFileChooser(fc);
				int action = fc.showOpenDialog(null);
							
				if(action != JFileChooser.APPROVE_OPTION) {														
					logReaderUi.setLogFileChooser(null);					
					return;
				}
				
				// FileChooser 를 통해 선택한 파일의 전체경로를 가져온뒤 [1)파일경로/2)파일명] 으로 분리
				String selectFilePath = fc.getSelectedFile().getPath();	
				logReaderUi.getLoadLogFileFullPath().setText(selectFilePath);
				if(selectFilePath != null) {
					if(selectFilePath.lastIndexOf("\\") != -1){							
						String filePath = selectFilePath.substring(0, selectFilePath.lastIndexOf("\\"));
						String fileName = selectFilePath.substring(selectFilePath.lastIndexOf("\\")+1);
						
						if(filePath != null && fileName != null) {
							if(!filePath.equals("") && !fileName.equals("")) {
								FileReader fileReader = new FileReaderImpl();
								BufferedReader br = fileReader.loadFileAndRead(filePath, fileName);
																								
								System.out.println(String.format(">>>> 로그 조회 정보 : \n"
										+ "1) logText:%s\n"
										+ "2) separator1:%s\n"
										+ "3) separator2:%s\n"
										+ "4) logDateForm:%s", logText, separator1, separator2,logDateForm));
								
								List<String> logDateTimeList = fileReader.getlogDateTimeList(br, logText, logText2, separator1, separator2);								
								if(logDateTimeList != null) {
									diffTimeList = fileReader.calcDiffTime(logDateTimeList);
									if(diffTimeList != null) {
										logReaderUi.getLoadLogData().setText("");
										for(int i=0; i<diffTimeList.size(); i++) {
											long loadData = diffTimeList.get(i);
											logReaderUi.getLoadLogData().append("#"+(i+1)+" "+logText+"->"+logText2+" > : "+loadData+"ms\n");
										}
									}
								}
							}
						}
						
						System.out.println(String.format(">>> 선택한 파일 전체 경로 : [%s]", selectFilePath));
						System.out.println(String.format(">>> 선택한 파일 경로 : [%s]", filePath));
						System.out.println(String.format(">>> 선택한 파일명 : [%s]", fileName));
					}
				}
				
				logReaderUi.setLogFileChooser(null);
			}
			
		}else if(bt_value == logReaderUi.getSaveCsvFile()) {			// ***************** 파일 불러오기 버튼을 눌렀을 경우 동작
			System.out.println(">>>> CSV 저장 버튼!");
			// 로그파일 읽은 결과물에 대한 내용을 csv 로 저장하게 진행
			CreateCSVFile csvObject = new CreateCSVFile();
			System.out.println(String.format(">>> CSV FILE 저장 경로 : %s"
					+ "\nCSV FILE 명 : %s", csvFilePath, csvFileName));
			int confirmResult = JOptionPane.showConfirmDialog(null, "찾은 내용을 CSV 파일로 저장하시겠습니까?");
			if(confirmResult == JOptionPane.YES_OPTION) {
				csvObject.createCsvFile(diffTimeList, csvFilePath, csvFileName);						
			}
		}
	}
}


/* Back 단 Logic 검증 부분
FileReader fileReader = new FileReaderImpl();
BufferedReader br = fileReader.loadFileAndRead(filePath, fileName);
List<Date> logDateTimeList = fileReader.getlogDateTimeList(br, logText, separator1, separator2);
List<String> diffTimeList = fileReader.calcDiffTime(logDateTimeList);
CreateCSVFile csvObject = new CreateCSVFile();
csvObject.createCsvFile(diffTimeList, csvFilePath, csvFileName);*/
