package drizzt.rule.url;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class URLRuleTest {

	private static URLRule rules;

	@BeforeClass
	public static void init() throws Exception {
		rules = new URLRule();
	}

	@Test
	public void test() throws Exception {
		String host = "item.taobao.com";
		String url = "https://item.taobao.com/item.htm?id=520164237114";

		URLBean result = rules.match(host, url);

		if (result == null) {
			Assert.fail("match fial ! ");
		}

		System.out.println(result);

		Assert.assertEquals("match fail ! ", "520164237114", result.getUrlFeture());
	}

}
