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
	// key host，二级key matchSource： matchSource：url，cookie， value 规则列表
	private Map<String, Map<String, List<AccountRuleBean>>> rules = new HashMap<String, Map<String, List<AccountRuleBean>>>();

	public AccountRule() throws Exception {
		List<AccountRuleBean> dbRules = BaseBean.getAll(AccountRuleBean.class);

		for (AccountRuleBean r : dbRules) {
			if (StringUtils.endsWith(r.getRegex(), "-1")) {
				continue;
			}
			Map<String, List<AccountRuleBean>> hostRules = rules.get(r.getHost());
			if (hostRules == null) {
				hostRules = new HashMap<String, List<AccountRuleBean>>();
				rules.put(r.getHost(), hostRules);
			}

			List<AccountRuleBean> matchSourceRules = hostRules.get(r.getMatchSource());
			if (matchSourceRules == null) {
				matchSourceRules = new LinkedList<AccountRuleBean>();
				hostRules.put(r.getMatchSource(), matchSourceRules);
			}

			matchSourceRules.add(r);
		}

		log.info("account init : rule count {} ", dbRules.size());
	}

	public List<AccountBean> matchUrl(String host, String url) {
		return match(host, AccountRuleMatchSource.URL, url);
	}

	public List<AccountBean> matchCookie(String host, String cookies) {
		return match(host, AccountRuleMatchSource.COOKIE, cookies);
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
	public List<AccountBean> match(String host, String matchSource, String matchStr) {
		if (StringUtils.isBlank(host) || StringUtils.isBlank(matchStr) || StringUtils.isBlank(matchSource)) {
			return null;
		}

		Map<String, List<AccountRuleBean>> hostRules = rules.get(host);
		if (hostRules == null) {
			return null;
		}

		List<AccountRuleBean> matchSourceRules = hostRules.get(matchSource);
		if (matchSourceRules == null) {
			return null;
		}

		List<AccountBean> accounts = new LinkedList<AccountBean>();
		for (AccountRuleBean r : matchSourceRules) {
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
