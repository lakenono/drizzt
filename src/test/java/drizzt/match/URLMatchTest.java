package drizzt.match;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
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
		String host = "item.yhd.com";
		String url = "http://item.yhd.com/item/29237767";
		String adid = "lurenjia";

		log.setAdid(adid);
		log.setHost(host);
		log.setUrl(url);

		List<AdidUser> adidUsers = urlMatch.match(log);

		if (adidUsers == null) {
			Assert.fail("match fail!");
		}

		for (AdidUser u : adidUsers) {
			System.out.println(u.getCampaignId() + " | " + u.getAdid() + " | " + u.getType());
		}
	}

	@Test
	public void testLog() throws Exception {
		String fileName = "/Users/shilei/Root/Develop/DevelopSpace/Test/2015091721.txt";
		LineIterator lineIterator = FileUtils.lineIterator(new File(fileName));

		while (lineIterator.hasNext()) {
			String line = lineIterator.nextLine();

			BroadbandLog bean = BroadbandLog.convertLine(line);

			
			if (bean == null) {
				continue;
			}
			
//			System.out.println(bean.getUrl());
//			System.out.println(bean.getRef());
//			System.out.println("-----------------");
			
//			String url = "detail.tmall.com";
//			if(StringUtils.contains(bean.getUrl(), url)||StringUtils.contains(bean.getRef(), url)){
//				System.out.println(bean.getUrl()+"|" + bean.getRef());
//			}

			List<AdidUser> users = urlMatch.match(bean);

			if (users != null && !users.isEmpty()) {

				for (AdidUser u : users) {
					System.out.println(u.getCampaignId() + " | " + u.getAdid() + " | " + u.getType());
				}

				System.out.println("===============");
			}
		}
	}

	@Test
	public void testPattern() throws Exception {
		String url = "http://item.jd.com/1217501.html";
		String pattern = "item\\.jd\\.com/(\\d{1,})\\.html";

		Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(url);

		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}

}
