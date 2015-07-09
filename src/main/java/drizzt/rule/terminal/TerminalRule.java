package drizzt.rule.terminal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import drizzt.recognize.terminal.TerminalBean.TerminalFlag;

/**
 * 终端识别
 * 
 * @author shilei
 *
 */
@Slf4j
public class TerminalRule {

	// 一级索引 host ， 二级 索引 terminal_flag
	protected Map<String, Map<String, List<TerminalRuleBean>>> rules = new HashMap<String, Map<String, List<TerminalRuleBean>>>();

	protected List<String> terminalFlags = new LinkedList<String>();

	public TerminalRule() throws Exception {
		// 初始化规则
		List<TerminalRuleBean> dbRules = BaseBean.getAll(TerminalRuleBean.class);

		for (TerminalRuleBean r : dbRules) {
			Map<String, List<TerminalRuleBean>> hostRules = rules.get(r.getHost());

			if (hostRules == null) {
				hostRules = new HashMap<String, List<TerminalRuleBean>>();
				rules.put(r.getHost(), hostRules);
			}

			List<TerminalRuleBean> flagRules = hostRules.get(r.getTerminalFlag());
			if (flagRules == null) {
				flagRules = new LinkedList<TerminalRuleBean>();
				hostRules.put(r.getTerminalFlag(), flagRules);
			}

			flagRules.add(r);
		}

		log.info("terminal rule init : rule count {} 。", dbRules.size());

		// 初始化要提取的类型那个
		terminalFlags.add(TerminalFlag.IDFA);
		terminalFlags.add(TerminalFlag.IMEI);
		terminalFlags.add(TerminalFlag.IMSI);
		terminalFlags.add(TerminalFlag.MAC);
		terminalFlags.add(TerminalFlag.PHONE_NO);
	}

	/**
	 * 根据从log重识别终端信息
	 * 
	 * @param bean
	 * @return
	 */
	public List<TerminalBean> match(String host, String url) {
		if (StringUtils.isBlank(host) || StringUtils.isBlank(url)) {
			return null;
		}

		List<TerminalBean> terminals = new LinkedList<TerminalBean>();

		for (String terminalFlag : terminalFlags) {
			String terminalInfo = match(host, terminalFlag, url);
			if (StringUtils.isBlank(terminalInfo)) {
				continue;
			}
			terminals.add(new TerminalBean(terminalFlag, terminalInfo));
		}

		return terminals;
	}

	// 根据域名匹配
	private String match(String host, String terminalFlag, String url) {
		Map<String, List<TerminalRuleBean>> hostRules = rules.get(host);

		if (hostRules == null) {
			return null;
		}

		List<TerminalRuleBean> flagRules = hostRules.get(terminalFlag);
		if (flagRules == null || flagRules.isEmpty()) {
			return null;
		}

		for (TerminalRuleBean r : flagRules) {
			Matcher matcher = r.getPattern().matcher(url);

			if (matcher.find()) {
				return matcher.group(1);
			}
		}
		return null;
	}

}
