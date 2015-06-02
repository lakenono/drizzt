package drizzt.match;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lakenono.db.BaseBean;

import org.apache.commons.lang.StringUtils;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.domain.URL;
import drizzt.rule.id.URLRule;

/**
 * url 匹配
 * 
 * @author shilei
 *
 */
public class URLMatch {
	private URLRule urlRule;
	private Map<String, List<String>> urls; // key：domain + feature

	public URLMatch() throws SQLException   {
		urlRule = new URLRule();
		urls = new HashMap<>();

		for (URL u : BaseBean.getAll(URL.class)) {
			String key = u.getHost() + u.getUrlFeature();
			List<String> compaignIds = urls.get(key);
			if (compaignIds == null) {
				compaignIds = new ArrayList<>();
				urls.put(key, compaignIds);
			}

			compaignIds.add(u.getCampaignId());
		}
	}

	public List<AdidUser> match(BroadbandLog bean) {
		String feature = urlRule.match(bean.getUrl(), bean.getHost());
		if (StringUtils.isBlank(feature)) {
			return null;
		}

		String key = bean.getHost() + feature;
		if (urls.containsKey(key)) {
			List<AdidUser> users = new ArrayList<>();
			List<String> campaignIds = urls.get(key);

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
