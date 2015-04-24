package drizzt.match;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.domain.Domain;

@Slf4j
public class DomainMatch
{
	private Map<String, List<String>> domains = new HashMap<String, List<String>>();

	public DomainMatch() throws SQLException
	{
		for (Domain bean : Domain.loadAll())
		{
			// 如果不存在 
			if (!domains.containsKey(bean.getDomain()))
			{
				List<String> campaignIds = new ArrayList<String>();
				campaignIds.add(bean.getCampaignId());

				this.domains.put(bean.getDomain(), campaignIds);
			}
			else
			{
				// 如果存在. 继续追加活动id
				this.domains.get(bean.getDomain()).add(bean.getCampaignId());
			}
		}
	}

	public List<AdidUser> match(BroadbandLog bean)
	{
		if (!this.domains.containsKey(bean.getDomain()))
		{
			return null;
		}
		else
		{
			List<String> campaignIds = this.domains.get(bean.getDomain());

			List<AdidUser> result = new ArrayList<AdidUser>();

			for (String campaignId : campaignIds)
			{
				AdidUser user = new AdidUser();
				user.setAdid(bean.getAdid());
				user.setCampaignId(campaignId);
				user.setType("domain");

				result.add(user);

				//log.debug("匹配到domain[{}]人群 日志: {}", bean.getDomain(), bean.toString());
			}

			return result;
		}
	}
}
