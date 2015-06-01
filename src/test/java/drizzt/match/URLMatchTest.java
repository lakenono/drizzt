package drizzt.match;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;

public class URLMatchTest {
	private static URLMatch urlMatch;

	@BeforeClass
	public static void before() throws Exception {
		urlMatch = new URLMatch();
	}

	@Test
	public void test() {
		BroadbandLog log = new BroadbandLog();
		String host = "item.jd.com";
		String url = "http://item.jd.com/1217501.html";
		String adid = "lurenjia";

		log.setAdid(adid);
		log.setHost(host);
		log.setUrl(url);

		List<AdidUser> adidUsers = urlMatch.match(log);
		
		if(adidUsers==null){
			Assert.fail("match fail!");
		}
		
		for (AdidUser u : adidUsers) {
			System.out.println(u.getCampaignId() + " | " + u.getAdid() + " | " + u.getType());
		}
	}

	@Test
	public void testPattern() throws Exception {
		String url = "http://item.jd.com/1217501.html";
		String pattern = "http://item.jd.com/(\\d+).html";

		Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(url);

		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}

}
