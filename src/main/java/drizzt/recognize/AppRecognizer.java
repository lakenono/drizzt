package drizzt.recognize;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.MaienLog;
import drizzt.rule.app.AppBean;
import drizzt.rule.app.AppRule;

public class AppRecognizer {
	private AppRule rules;

	public AppRecognizer() throws Exception {
		this.rules = new AppRule();
	}

	public AppBean recognize(MaienLog bean) {
		// url识别
		if (StringUtils.isBlank(bean.getUrl())) {
			return null;
		}

		return rules.match(bean.getHost(), bean.getUrl());
	}
}
