package log.reader.main;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import log.reader.core.CreateCSVFile;
import log.reader.core.FileReader;
import log.reader.core.FileReaderImpl;
import log.reader.ui.LogReaderUI;

public class LogReaderMain {
	public static void main(String[] args) {
		new LogReaderUI();
		
		
		/* Back 단 Logic 검증 부분
		String filePath = "C:/elasticsearch-7.5.0/logs";
		String fileName = "elasticsearch.log";
		String logText = "loaded module [aggs-matrix-stats]";
		String separator1 = "[";
		String separator2 = "]";
		
		String csvFilePath = "C:/csvTest";
		String csvFileName = "testCsv";
		
		FileReader fileReader = new FileReaderImpl();
		BufferedReader br = fileReader.loadFileAndRead(filePath, fileName);
		List<Date> logDateTimeList = fileReader.getlogDateTimeList(br, logText, separator1, separator2);
		List<String> diffTimeList = fileReader.calcDiffTime(logDateTimeList);
		CreateCSVFile csvObject = new CreateCSVFile();
		csvObject.createCsvFile(diffTimeList, csvFilePath, csvFileName);*/
		
		
		
		/*
		 * 밀리초 단위 시간차 구하기 (String -> Date)*/
		/*String date1 = "2021-08-08 16:20:17.100/";
		String date2 = "2021-08-08 16:21:18.900/";
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		try {
			date1 = date1.substring(date1.indexOf(' '), date1.indexOf('/'));
			Date date11 = sdf.parse(date1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date11);
			//cal.add(Calendar.MILLISECOND, 1);
			Date calDate = cal.getTime();
			String val = sdf.format(calDate);
			System.out.println(cal.getTimeInMillis());
			System.out.println(">> val1 : "+val);
			
			date2 = date2.substring(date2.indexOf(' '), date2.indexOf('/'));
			Date date22 = sdf.parse(date2);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date22);
			//cal2.add(Calendar.MILLISECOND, 1);
			Date calDate2 = cal2.getTime();
			String val2 = sdf.format(calDate2);
			System.out.println(cal2.getTimeInMillis());
			System.out.println(">> val2 : "+val2);
			
			long time1 = cal.getTimeInMillis();
			long time2 = cal2.getTimeInMillis();
			System.out.println(time1 + " / "+time2);
			long diffTime = time2-time1;
			double diffTimeDouble = (double)diffTime;
			System.out.println(diffTimeDouble);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

}
