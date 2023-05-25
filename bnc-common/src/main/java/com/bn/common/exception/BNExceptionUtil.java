package com.bn.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;

public class BNExceptionUtil {
	private final static int DEFAULT_LINE_AT_CNT = 5;
	
	public final static String getStackTrace() {
		return getStackTrace(new Exception());
	}

	public final static String getStackTrace(Throwable ex) {
		StringWriter strWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(strWriter));
		return strWriter.toString();
	}
	
	/**
	 * This tells us on what line the exception was thrown from com.bn...class
	 * @param ex
	 * @return
	 */
	public final static String getBncErrorAt(Throwable ex) {
		return getBncErrorAt(ex, DEFAULT_LINE_AT_CNT);
	}
	
	/**
	 * This gives us the stack trace of starting from com.bn... class and will
	 * provide a stack trace of n lines.
	 * @param ex exception
	 * @param line number of lines to extract
	 * @return
	 */
	public final static String getBncErrorAt(Throwable ex, int line) {
		StringWriter strWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(strWriter));
		StringBuilder sb = new StringBuilder(ex.toString());
		StringTokenizer st = new StringTokenizer(strWriter.toString(), "\n\r");
		int cnt = 0;
		int lineCount = 0;
		while (st.hasMoreTokens()) {
			lineCount ++;
			String val = st.nextToken().trim();
			if ( val.startsWith("at com.bn.") && !val.startsWith("at com.bn.exception.BNException") ) {
				if ( cnt==0 )
					sb.append(", bnc ex ").append(val);
				else
					sb.append("; ").append(val);
				cnt++;
				if ( cnt==line )
					return sb.toString();
			}
			if ( lineCount>100 ) {
				// give up after 100 lines
				break;
			}
		}
		return sb.toString();
	}
	
	public final static String getBncStackTrace() {
		StringWriter strWriter = new StringWriter();
		boolean bn = false;
		new Exception().printStackTrace(new PrintWriter(strWriter));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(strWriter.toString(), "\n\r");
		while (st.hasMoreTokens()) {
			String val = st.nextToken().trim();
			if ( val.startsWith("at com.bn.") ) {
				if ( !val.startsWith("at com.bn.exception.BNException") ) {
					sb.append(val).append("\n");
					bn = true;
				}
			} else if ( bn ){
				break;
			}
		}
		return sb.toString();
	}
}
