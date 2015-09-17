package drizzt.recognize;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.LogRecord;
import drizzt.rule.terminal.TerminalBean;
import drizzt.rule.terminal.TerminalRule;

public class TerminalRecognizer {
	private TerminalRule rules;

	public TerminalRecognizer() throws Exception {
		rules = new TerminalRule();
	}

	/**
	 * url 识别终端信息
	 * 
	 * @param bean
	 * @return
	 */
	public List<TerminalBean> recognize(LogRecord bean) {
		// url识别
		if (StringUtils.isBlank(bean.getUrl())) {
			return null;
		}

		return rules.match(bean.getHost(), bean.getUrl());
	}
}
