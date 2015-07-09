package drizzt.rule.account;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountRuleTest {
	private static AccountRule rules;

	@BeforeClass
	public static void init() throws Exception {
		rules = new AccountRule();
	}

	@Test
	public void testMatchURL() {
		String host = "wm18.com";
		String url = "http://wm18.com/a/b?username=asdf@163.com&b=2";

		List<AccountBean> accounts = rules.matchUrl(host, url);
		if (accounts == null || accounts.isEmpty()) {
			Assert.fail("match fail!");
		}

		for (AccountBean a : accounts) {
			System.out.println(a);
			Assert.assertEquals("matach fail ! ", "asdf@163.com", a.getAccount());
		}

	}

	@Test
	public void testMatchCookie() {
		String host = "baidu.com";
		String cookies = "BAIDUID=0B7FA219F8D1022B88425D385BB21B80:FG=1; BIDUPSID=0B7FA219F8D1022B88425D385BB21B80; PSTM=1436412165; BD_HOME=0; H_PS_PSSID=16059_15976_1431_14732_16148_14803_12826_10813_14430_12867_16167_14669_14871_16210_16036_15053_11467_13932_10633; BD_UPN=133252";

		List<AccountBean> accounts = rules.matchCookie(host, cookies);
		if (accounts == null || accounts.isEmpty()) {
			Assert.fail("match fail!");
		}

		for (AccountBean a : accounts) {
			System.out.println(a);
			Assert.assertEquals("matach fail ! ", "0B7FA219F8D1022B88425D385BB21B80", a.getAccount());
		}
	}

}
