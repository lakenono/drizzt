package drizzt.rule.industry.ec;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ECRuleTest {

	private static ECRule rules;

	@BeforeClass
	public static void init() throws Exception {
		rules = new ECRule();
	}

	@Test
	public void test() throws Exception {
		String host = "cart.jd.com";
		String url = "http://cart.jd.com/addToCart.html?rcd=1&pid=1579700&rid=1440731282075&em=";

		ECBean result = rules.match(host, url);

		if (result == null) {
			Assert.fail("match fial ! ");
		}

		System.out.println(result);

		Assert.assertEquals("match fail ! ", "1579700", result.getEcId());
	}
	
	public void testRules(){
		
	}

}
