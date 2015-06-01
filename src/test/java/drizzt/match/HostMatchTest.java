package drizzt.match;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;


public class HostMatchTest {

	private static HostMatch hostMatch;

	@BeforeClass
	public static void before() throws SQLException {
		hostMatch = new HostMatch();
	}

	@Test
	public void test() {
		BroadbandLog log = new BroadbandLog();
		
		String host = "shouji.jd.com";
		String adid="lurenjia"	;
		
		log.setHost(host);
		log.setAdid(adid);
		
		List<AdidUser> adidUsers = hostMatch.match(log);
		
		if(adidUsers==null){
			Assert.fail("match fail!");
		}
		
		for(AdidUser u : adidUsers){
			System.out.println(u.getAdid()+"|"+u.getCampaignId()+"|" + u.getType());
		}
	}

}
