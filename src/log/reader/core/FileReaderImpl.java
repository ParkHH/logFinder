package log.reader.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import log.reader.ui.LogReaderUiProcess;

public class FileReaderImpl implements FileReader {

	/**
	 * log File 을 읽어들입니다.
	 */
	@Override
	public BufferedReader loadFileAndRead(String filePath, String fileName) {
		FileInputStream fis = null;
		InputStreamReader is = null;
		BufferedReader br = null;

		try {
			fis = new FileInputStream(filePath + "/" + fileName);
			is = new InputStreamReader(fis);
			br = new BufferedReader(is);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(">>> 파일이 경로에 없습니다, 파일 경로를 확인해주세요.");
		}
		return br;
	}

	/**
	 * 읽은 내용에서 특정 문자열이 포함되는지 여부를 찾아 해당 문구가 찍힌 일자를 String List 형태로 return 합니다.
	 */
	@Override
	public List<String> getlogDateTimeList(BufferedReader br, String logText, String logText2, String separator1, String separator2) {		
		List<String> dateTimeList = new ArrayList<String>();		
		LogReaderUiProcess.readLogLineData.clear();
		int readCount = 0;
		while (true) {
			String readData = null;
			try {
				readData = br.readLine();				
				//System.out.println(readData);
				if (readData != null) {
					if (readData.contains(logText) || readData.contains(logText2)) {
						System.out.println(">>>>> 대상 문자열을 찾았습니다.");						
						if(readCount == 0) {
							boolean validateResult = validationStartAndEnd(readData, logText, logText2);	// 시작점 끝점 유효성 검사 (끝점이 먼저 나왔을때 패스되도록 체크)						
							if(!validateResult) {
								//System.out.println(">>>> 끝점이 먼저 나와버렸네요, 패스하고 다음 줄 읽어들일게요!!");
								continue;
							}
						}
						LogReaderUiProcess.readLogLineData.add(readData);						
						int charIndex = readData.indexOf(separator1);
						int charIndex2 = readData.indexOf(separator2);
						String dateStr = "";
						if (charIndex != -1) {
							if(charIndex == charIndex2) {
								dateStr = readData.substring(0, charIndex-1);
							}else {
								dateStr = readData.substring(charIndex+1, charIndex2-1);
							}
							if(dateStr != null && dateStr.contains("T")) {
								dateStr = dateStr.replace("T", " ");
							}
							dateStr = dateStr.trim();
							//System.out.println(">>>> dateStr : ");
							//System.out.println(dateStr.trim());
												
							dateTimeList.add(dateStr);
							
							//System.out.println("!!!!! "+dateTime.getTime()+" / "+time);
						}
						
						readCount++;
					} 
				}else if (readData == null) {
					System.out.println(">>>>> 파일 내용을 모두 읽었습니다.");
					JOptionPane.showMessageDialog(null, "파일 내용을 모두 읽었습니다.");
					break;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println(">>> 파일에서 읽을 내용이 없습니다. 파일 내용을 확인해주세요.");
				JOptionPane.showMessageDialog(null, "파일에서 읽을 내용이 없습니다. 파일 내용을 확인해주세요.");
				break;
			}  catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println(">>> 문자열 데이터를 날짜 형식 변환중 에러가 발생했습니다.");
				JOptionPane.showMessageDialog(null, "문자열 데이터를 날짜 형식 변환중 에러가 발생했습니다.");
				break;
			}
		}
		return dateTimeList;
	}
		
	
	/**
	 * 로그파일에서 읽어온 내용의 시간 데이터의 차이를 구해 return
	 */
	@Override
	public List<Long> calcDiffTime(List<String> logDateTimeList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		List<Long> diffTimeList = new ArrayList<Long>();		
		for(int i=1; i<logDateTimeList.size(); i+=2) {
			String logDate1 = logDateTimeList.get(i-1);
			String logDate2 = logDateTimeList.get(i);			
			//System.out.println((i-1)+" : "+logDate1+" / "+i+" : "+logDate2);
						
			// JAVA 에서 String 형태의 "yyyy-MM-dd HH:mm:ss.SSS" 를 Date 로 변환시 MilliSecond 가 올림처리 되어버림
			// 따라서 소수점 '.' 을 기준으로 뒤 첫자리가 0 이 아닐경우 별도로 변환 보관
			// 이후 Calendar 를 통해 1초 올림된 1초를 빼주고 별도 보관한 밀리초를 더해서 원래의 로그일시를 회복
			try {
				Date logDate11 = sdf.parse(logDate1);
				Date logDate22 = sdf.parse(logDate2);
				
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(logDate11);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(logDate22);
				
				long logDate1Long = cal1.getTimeInMillis();
				long logDate2Long = cal2.getTimeInMillis();
				long duringTime = logDate2Long-logDate1Long;
				long duringTimeDouble = duringTime;
				
				//System.out.println((i-1)+" : "+cal1.getTimeInMillis()+" / "+i+" : "+cal2.getTimeInMillis());
				
				diffTimeList.add(duringTimeDouble);
							
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println(">>> 읽은 데이터를 날짜 형식 변환중 에러가 발생했습니다, 변환 대상 데이터 형식을 확인하세요.");
				JOptionPane.showMessageDialog(null, "읽은 데이터를 날짜 형식 변환중 에러가 발생했습니다, 변환 대상 데이터 형식을 확인하세요.");
				break;
			}									
		}
		return diffTimeList;
	}
	
	/*
	 * 시간 차이 표기를 HH:mm:ss 로 출력할때 사용
	long hourGap = duringTime/60/60;
	long minGap = ((long)(duringTime/60))%60;
	long secGap = duringTime%60;
	
	if(hourGap > 99) {
		hourGap = (long)99;
	}
	
	String returnTime = "";
	
	// 1.시 차이 Str 구성
	if(hourGap < 10) {
		returnTime += "0"+hourGap;
	}else {
		returnTime += hourGap;
	}
	// 2.분 차이 Str 구성
	if(minGap < 10) {
		returnTime += ":0"+minGap;
	}else {
		returnTime += ":"+minGap;
	}
	// 3.초 차이 Str 구성
	if(secGap < 10) {
		returnTime += ":0"+secGap;
	}else {
		returnTime += ":"+secGap;
	}*/
				
	
	//System.out.println(">>>> 로그 발생 시간차이 : "+returnTime);
	
	
	/**
	 * 읽어들인 문장 시작, 끝점 유효성 체크, 끝점이 먼저 나왔을 경우 체크
	 * @param readData
	 * @return
	 */
	public boolean validationStartAndEnd(String readData, String logText, String logText2) {
		boolean validateResult = false;
		if(readData.indexOf(logText) != -1) validateResult = true;		
		
		return validateResult;
	}
}
