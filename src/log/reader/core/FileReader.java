package log.reader.core;

import java.io.BufferedReader;
import java.util.Date;
import java.util.List;

public interface FileReader {
	/**
	 * log File 을 읽어들입니다.
	 */
	public BufferedReader loadFileAndRead(String filePath, String fileName);
	
	/**
	 * 읽은 내용에서 특정 문자열이 포함되는지 여부를 찾아 해당 문구가 찍힌 일자를 Date 형태로 return 합니다.
	 */
	public List<String> getlogDateTimeList(BufferedReader br, String logText, String logText2, String separator1, String separator2);
	
	/**
	 * 로그파일에서 읽어온 내용의 시간 데이터의 차이를 구해 return
	 */
	public List<Long> calcDiffTime(List<String> logDateTimeList);
}
