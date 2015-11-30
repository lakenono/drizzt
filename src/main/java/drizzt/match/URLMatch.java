package drizzt.match;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.domain.URL;
import drizzt.rule.industry.ec.ECBean;
import drizzt.rule.industry.ec.ECRule;

/**
 * url 匹配
 * 
 * @author shilei
 *
 */
@Slf4j
public class URLMatch {
	// url提取规则
	private ECRule ecRule;

	// url 特征与待提取的comapignid对应关系
	private Map<String, List<String>> urls; // key：site + feature , value :

	public URLMatch() throws SQLException {
		ecRule = new ECRule();

		urls = new HashMap<String, List<String>>();

		List<URL> urlList = BaseBean.getAll(URL.class);
		log.debug("url campaigns :{} ", urlList.size());

		for (URL u : urlList) {

			String key = u.getSite() + u.getUrlFeature();
			List<String> compaignIds = urls.get(key);
			if (compaignIds == null) {
				compaignIds = new ArrayList<>();
				urls.put(key, compaignIds);
			}

			compaignIds.add(u.getCampaignId());
		}
	}

	public List<AdidUser> matchUrl(String url, String host, String adid) {
		// 空url
		if (StringUtils.equals(url, "--") || StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (StringUtils.isBlank(host)) {
				return null;
			}

			// 根据host match 获得标志
			ECBean ecBean = ecRule.match(host, url);
			
			if (ecBean == null) {
				return null;
			}

			log.debug("ecbean : {}",ecBean);
			// 根据site 找到对应的站点
			String key = ecBean.getSite() + ecBean.getEcId();
			if (urls.containsKey(key)) {
				List<AdidUser> users = new ArrayList<>();
				List<String> campaignIds = urls.get(key);

				for (String campaignId : campaignIds) {
					AdidUser user = new AdidUser();
					user.setAdid(adid);
					user.setCampaignId(campaignId);
					user.setType("url");

					users.add(user);
				}

				return users;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<AdidUser> match(BroadbandLog bean) {
		List<AdidUser> fromUrl = matchUrl(bean.getUrl(), bean.getHost(), bean.getAdid());
		List<AdidUser> fromRefer = matchUrl(bean.getRef(), null, bean.getAdid());

		if (fromUrl != null) {
			if (fromRefer != null) {
				fromUrl.addAll(fromRefer);
			}
			return fromUrl;
		} else {
			return fromRefer;
		}

	}

}
