package drizzt.recognize;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import drizzt.recognize.domain.LogRecord;
import drizzt.rule.account.AccountBean;
import drizzt.rule.account.AccountRule;

public class AccountRecognizer {
	private AccountRule rules = null;

	public AccountRecognizer() throws Exception {
		rules = new AccountRule();
	}

	/**
	 * 使用url，refer ， cookie 识别账户数据
	 * 
	 * @param log
	 * @return
	 */
	public List<AccountBean> recognize(LogRecord bean) {
		String url = bean.getUrl();
		String cookie = bean.getCookie();
		String refer = bean.getRefer();

		List<AccountBean> accounts = new LinkedList<AccountBean>();

		// url识别
		if (StringUtils.isNotBlank(url)) {
			List<AccountBean> urlAccounts = rules.matchUrl(bean.getHost(), url);
			if (urlAccounts != null && !urlAccounts.isEmpty()) {
				accounts.addAll(urlAccounts);
			}
		}

		// refer识别
		if (StringUtils.isNotBlank(refer) && StringUtils.startsWithIgnoreCase(refer, "http")) {
			try {
				List<AccountBean> referAccounts = rules.matchUrl(new URL(refer).getHost(), refer);
				if (referAccounts != null && !referAccounts.isEmpty()) {
					accounts.addAll(referAccounts);
				}
			} catch (MalformedURLException e) {
			}

		}

		// cookie识别
		if (StringUtils.isNotBlank(cookie)) {
			List<AccountBean> cookieAccounts = rules.matchCookie(bean.getHost(), cookie);
			if (cookieAccounts != null && !cookieAccounts.isEmpty()) {
				accounts.addAll(cookieAccounts);
			}
		}

		return accounts;
	}
}
