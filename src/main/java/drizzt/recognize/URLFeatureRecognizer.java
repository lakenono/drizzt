package drizzt.recognize;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.MaienLog;
import drizzt.rule.url.URLBean;
import drizzt.rule.url.URLRule;

public class URLFeatureRecognizer {
	private URLRule rules;

	public URLFeatureRecognizer() throws Exception {
		this.rules = new URLRule();
	}

	public URLBean recognize(MaienLog bean) {
		// url识别
		if (StringUtils.isBlank(bean.getUrl())) {
			return null;
		}
		return rules.match(bean.getHost(), bean.getUrl());
	}
}
