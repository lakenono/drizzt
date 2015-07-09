package drizzt.rule.terminal;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import drizzt.recognize.terminal.TerminalBean.TerminalFlag;

public class TerminalRuleTest {

	private static TerminalRule rule;

	@BeforeClass
	public static void init() throws Exception {
		rule = new TerminalRule();
	}

	@Test
	public void test() {
		String url = "http://store.tv.sohu.com/?mobile=13812309980&";
		String host = "store.tv.sohu.com";

		List<TerminalBean> terminals = rule.match(host, url);
		if (terminals == null || terminals.isEmpty()) {
			Assert.fail("match fail!");
		}

		for (TerminalBean t : terminals) {
			System.out.println(t);

			if (t.getTerminalFlag().equals(TerminalFlag.PHONE_NO)) {
				Assert.assertEquals("match fail!", "13812309980", t.getTerminalData());
			}
		}
	}

}
