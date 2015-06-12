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
import drizzt.rule.url.URLRule;
import drizzt.rule.url.URLRuleHostSiteRefBean;

/**
 * url 匹配
 * 
 * @author shilei
 *
 */
@Slf4j
public class URLMatch {
	private URLRule urlRule;
	private Map<String,String> hostSiteMapper; //站点，hosh映射，用于tmall.com，taobao.com这个样的host映射到同一个网站，相当于同义词表
	private Map<String, List<String>> urls; // key：site + feature

	public URLMatch() throws SQLException   {
		urlRule = new URLRule();
		
		hostSiteMapper = new HashMap<>();
		
		urls = new HashMap<>();
		
		for(URLRuleHostSiteRefBean m : BaseBean.getAll(URLRuleHostSiteRefBean.class)){
			hostSiteMapper.put(m.getHost(), m.getSite());
			log.debug(m.toString());
		}
		
		List<URL> urlList = BaseBean.getAll(URL.class);
		log.debug("url campaigns :{} ",urlList.size());
		
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
	
	public List<AdidUser> matchUrl(String url,String host,String adid){
		//空url
		if(StringUtils.equals(url, "--")|| StringUtils.isBlank(url)){
			return null;
		}
		try {
			if(StringUtils.isBlank(host)){
				host = new java.net.URL(url).getHost();
			}
			
			//获取host对应的站点名 site
			String site = hostSiteMapper.get(host);
			if(StringUtils.isBlank(site)){
				return null;
			}
			
			//根据host match 获得标志
			String feature = urlRule.match(url, host);
			if (StringUtils.isBlank(feature)) {
				return null;
			}
			
			//根据site 找到对应的站点
			String key = site + feature;
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
		List<AdidUser> fromUrl = matchUrl(bean.getUrl(),bean.getHost(),bean.getAdid());
		List<AdidUser> fromRefer = matchUrl(bean.getRef(),null,bean.getAdid());

		if(fromUrl!=null){
			if(fromRefer!=null){
				fromUrl.addAll(fromRefer);
			}
			return fromUrl;
		}else{
			return fromRefer;
		}
		
	}

}
