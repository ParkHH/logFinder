package log.reader.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import log.reader.ui.LogReaderUiProcess;

public class CreateCSVFile {
	public void createCsvFile(List<Long> diffTimeList, String csvFilePath, String csvFileName) {
		try {
			File file = new File(csvFilePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath+"/"+csvFileName+".csv", false));
			bw.write("\uFEFF"); // FileWriter csv 파일 한글 깨짐시 추가 BOM 문자 (Encoding 형식 지정) - UTF-8
			//bw.write("순번,로그발생간격");
			bw.newLine();
			List<String> readLogLineList = LogReaderUiProcess.readLogLineData;
			
			for(int i=0; i<diffTimeList.size(); i++) {
				String readOddLine = readLogLineList.get((((i+1)*2)-2));
				if(readOddLine != null) {
					if(!readOddLine.equals("")) {
						readOddLine = readOddLine.substring(readOddLine.indexOf('#')+1, readOddLine.indexOf('#')+4);
					}
				}
				bw.write((i+1)+","+readOddLine+","+diffTimeList.get(i)+", ms");
				bw.newLine();
			}
			bw.flush();
			bw.close();
			JOptionPane.showMessageDialog(null, "CSV 파일 저장을 완료했습니다.\n*저장경로 : "+csvFilePath+"\n*파일명 : "+csvFileName);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(">>> CSV File 구성중 문제가 발생했습니다.");
			JOptionPane.showMessageDialog(null, "CSV 파일 구성중 문제가 발생했습니다.");
		}
	}
}
