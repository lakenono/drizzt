package drizzt.recognize.domain;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

/**
 * 日志：12个字段，意义如下： 字段1： 字段2：用户标识 字段3： 字段4： 字段5： 字段6：host+port 字段7：path+parameter
 * 字段8：refer 字段9：ua 字段10： 字段11： 字段12：cookie
 * 
 * @author shilei
 *
 */
@Data
public class MaienLog {
	private String uid;
	private String host;
	private String domain;

	private String url;
	private String refer;
	private String ua;
	private String cookie;

	public MaienLog(String log) {
		String[] records = StringUtils.splitByWholeSeparatorPreserveAllTokens(log, "\t");
		if (records.length != 12) {
		}

		uid = records[1];
		host = handleHost(records[5]);
		url = handleUrl(host, records[6]);
		refer = filterNull(records[7]);
		ua = filterNull(records[8]);
		cookie = filterNull(records[11]);
	}

	private String handleHost(String host) {
		if (StringUtils.endsWith(host, ":80")) {
			host = StringUtils.substringBefore(host, ":");
		}
		return host;
	}

	private String handleUrl(String host, String url) {
		return "http://" + host + url;
	}

	private String filterNull(String content) {
		if (StringUtils.equals(content, "(NULL)")) {
			return "";
		}
		return content;
	}
}
