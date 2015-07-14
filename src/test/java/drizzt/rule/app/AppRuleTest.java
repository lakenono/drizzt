package drizzt.rule.app;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppRuleTest {

	private static AppRule appRule;

	@BeforeClass
	public static void init() throws Exception {
		appRule = new AppRule();
	}

	@Test
	public void task() throws Exception {
		String host = "112.90.138.173";
		String url = "http://112.90.138.173:8080/2/0/index.html";

		AppBean app = appRule.match(host, url);
		if (app == null) {
			Assert.fail("match fail!");
		}
		Assert.assertEquals("match fail!", "3900", app.getAppId());
		System.out.println("task() : "+app);
	}

	@Test
	public void taskNoRegex() throws Exception {
		String host = "gwwcloudimg-02.m.alicdn.com";
		String url = "http://gwwcloudimg-02.m.alicdn.com/2/0/index.html";

		AppBean app = appRule.match(host, url);
		if (app == null) {
			Assert.fail("match fail!");
		}
		Assert.assertEquals("match fail!", "3914", app.getAppId());
		System.out.println("taskNoRegex() : "+app);
	}

}
