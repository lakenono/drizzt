package drizzt.recognize.domain.builder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.LogRecord;

/**
 * 日志：12个字段，意义如下： 字段1： 字段2：用户标识 字段3： 字段4： 字段5： 字段6：host+port 字段7：path+parameter
 * 字段8：refer 字段9：ua 字段10： 字段11： 字段12：cookie
 * 
 * @author shilei
 *
 */
@Data
@Slf4j
public class MaienLogBuilder {

	public static LogRecord convertLine(String line) {
		String[] records = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, "\t");
		if (records.length != 12) {
			log.warn("records fields error : {}", records.length);
			return null;
		}

		LogRecord maienLog = new LogRecord();
		maienLog.setAdid(records[1]);
		maienLog.setHost(handleHost(records[5]));
		maienLog.setUrl(handleUrl(maienLog.getHost(), records[6]));
		maienLog.setRefer(filterNull(records[7]));
		maienLog.setUa(filterNull(records[8]));
		maienLog.setCookie(filterNull(records[11]));

		return maienLog;
	}

	private static String handleHost(String host) {
		if (StringUtils.endsWith(host, ":80")) {
			host = StringUtils.substringBefore(host, ":");
		}
		return host;
	}

	private static String handleUrl(String host, String url) {
		return "http://" + host + url;
	}

	private static String filterNull(String content) {
		if (StringUtils.equals(content, "(NULL)")) {
			return "";
		}
		return content;
	}
}
