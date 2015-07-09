package drizzt.rule.account;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import drizzt.rule.account.AccountRuleBean.AccountRuleMatchSource;

/**
 * url 用户提取
 * 
 * @author shilei
 *
 */
@Slf4j
public class AccountRule {
	// key matchSource，二级key host： matchSource：url，cookie， value 规则列表
	private Map<String, Map<String, List<AccountRuleBean>>> rules = new HashMap<String, Map<String, List<AccountRuleBean>>>();

	public AccountRule() throws Exception {
		List<AccountRuleBean> dbRules = BaseBean.getAll(AccountRuleBean.class);

		for (AccountRuleBean r : dbRules) {
			Map<String, List<AccountRuleBean>> matchSourceRules = rules.get(r.getMatchSource());
			if (matchSourceRules == null) {
				matchSourceRules = new HashMap<String, List<AccountRuleBean>>();
				rules.put(r.getMatchSource(), matchSourceRules);
			}

			List<AccountRuleBean> hostRules = matchSourceRules.get(r.getHost());
			if (hostRules == null) {
				hostRules = new LinkedList<AccountRuleBean>();
				matchSourceRules.put(r.getHost(), hostRules);
			}

			hostRules.add(r);
		}

		log.info("account init : rule count {} ", dbRules.size());
	}

	public List<AccountBean> matchUrl(String host, String url) {
		return match(host, url, AccountRuleMatchSource.URL);
	}

	public List<AccountBean> matchCookie(String host, String cookie) {
		return match(host, cookie, AccountRuleMatchSource.COOKIE);
	}

	/**
	 * 
	 * 提取账户
	 * 
	 * @param host
	 *            url 中的host
	 * @param url
	 * @return
	 */
	public List<AccountBean> match(String host, String matchStr, String matchSource) {
		if (StringUtils.isBlank(host) || StringUtils.isBlank(matchStr) || StringUtils.isBlank(matchSource)) {
			return null;
		}

		Map<String, List<AccountRuleBean>> matchSourceRules = rules.get(matchSource);
		if (matchSourceRules == null) {
			log.warn("{} not exist!", matchSource);
			return null;
		}

		List<AccountRuleBean> hostRules = matchSourceRules.get(host);
		if (hostRules == null) {
			return null;
		}

		List<AccountBean> accounts = new LinkedList<AccountBean>();
		for (AccountRuleBean r : hostRules) {
			Matcher matcher = r.getPattern().matcher(matchStr);

			if (matcher.find()) {
				String account = matcher.group(1);
				if (StringUtils.isBlank(account)) {
					continue;
				}
				AccountBean accountBean = new AccountBean();
				accountBean.setAccount(account);
				accountBean.setAccountType(r.getAccountType());
				accountBean.setSite(r.getSite() != null ? r.getSite() : r.getHost());

				accounts.add(accountBean);
			}
		}
		return accounts;
	}

}
