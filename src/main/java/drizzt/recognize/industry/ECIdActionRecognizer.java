package drizzt.recognize.industry;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.LogRecord;
import drizzt.rule.industry.ec.ECBean;
import drizzt.rule.industry.ec.ECRule;

public class ECIdActionRecognizer {
	private ECRule rules;

	public ECIdActionRecognizer() throws Exception {
		this.rules = new ECRule();
	}

	public ECBean recognize(LogRecord bean) {
		// url识别
		if (StringUtils.isBlank(bean.getUrl())) {
			return null;
		}
		return rules.match(bean.getHost(), bean.getUrl());
	}
}
