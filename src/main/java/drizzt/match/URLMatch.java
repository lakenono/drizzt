package drizzt.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.rule.id.URLRule;

public class URLMatch {
	private URLRule urlRule;
	private Map<String, List<String>> urls;

	public List<AdidUser> match(BroadbandLog bean) {
		String feature = urlRule.match(bean.getUrl(), bean.getHost());
		if (StringUtils.isBlank(feature)) {
			return null;
		}

		if (urls.containsKey(feature)) {
			List<AdidUser> users = new ArrayList<>();
			List<String> campaignIds = urls.get(feature);
			
			for (String campaignId : campaignIds) {
				AdidUser user = new AdidUser();
				user.setAdid(bean.getAdid());
				user.setCampaignId(campaignId);
				user.setType("url");

				users.add(user);
			}

			return users;
		}

		return null;
	}

}
