package drizzt.recognize.domain.builder;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.LogRecord;

@Slf4j
public class BroadbandLogBuilder {

	public static LogRecord convertLine(String line) throws MalformedURLException {
		try {
			String[] s = StringUtils.split(line, "\t");

			// 判断字段是否8
			if (s.length != 8) {
				return null;
			}

			// adid为空的数据不可用
			if (StringUtils.equals(s[2], "--") || StringUtils.isBlank(s[2])) {
				return null;
			}

			for (int i = 0; i < s.length; i++) {
				if (StringUtils.equals(s[i], "--")) {
					s[i] = "";
				}
			}

			LogRecord bean = new LogRecord();
			bean.setTs(s[0]);
			bean.setSrcip(s[1]);
			bean.setAdid(s[2]);
			bean.setUrl(s[3]);
			bean.setRefer(s[4]);
			bean.setUa(s[5]);
			bean.setDstip(s[6]);
			bean.setCookie(s[7]);

			if (StringUtils.isNotBlank(bean.getUrl())) {
				bean.setHost(new URL(bean.getUrl()).getHost());
				bean.setDomain(LogRecord.getDomain(bean.getUrl()));
			}

			// log.debug(bean.toString());
			return bean;
		} catch (Exception e) {
			log.error("error: {}", line);
			throw new RuntimeException(e);
		}
	}
}
