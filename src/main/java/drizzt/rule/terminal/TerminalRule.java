package drizzt.rule.terminal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import drizzt.rule.terminal.TerminalBean.TerminalFlag;

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
			TerminalBean terminal = match(host, terminalFlag, url);
			if (terminal == null) {
				continue;
			}
			terminals.add(terminal);
		}

		return terminals;
	}

	// 根据域名匹配
	private TerminalBean match(String host, String terminalFlag, String url) {
		Map<String, List<TerminalRuleBean>> hostRules = rules.get(host);

		if (hostRules == null) {
			return null;
		}

		List<TerminalRuleBean> flagRules = hostRules.get(terminalFlag);
		if (flagRules == null || flagRules.isEmpty()) {
			return null;
		}

		Iterator<TerminalRuleBean> iter = flagRules.iterator();
		while (iter.hasNext()) {
			TerminalRuleBean r = iter.next();

			try {
				Matcher matcher = r.getPattern().matcher(url);

				if (matcher.find()) {
					String terminalInfo = matcher.group(1);
					if (StringUtils.isNoneBlank(terminalInfo)) {
						return new TerminalBean(terminalFlag, terminalInfo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("regex error : {} ", r);
				iter.remove();
			}
		}

		return null;
	}

}
