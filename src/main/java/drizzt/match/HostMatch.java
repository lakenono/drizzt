package drizzt.match;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;
import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.domain.Host;

@Slf4j
public class HostMatch
{
	private Map<String, List<String>> hosts = new HashMap<String, List<String>>();

	public HostMatch() throws SQLException
	{
		for (Host bean : BaseBean.getAll(Host.class))
		{
			// 如果不存在 
			if (!hosts.containsKey(bean.getHost()))
			{
				List<String> campaignIds = new ArrayList<String>();
				campaignIds.add(bean.getCampaignId());

				this.hosts.put(bean.getHost(), campaignIds);
			}
			else
			{
				// 如果存在. 继续追加活动id
				this.hosts.get(bean.getHost()).add(bean.getCampaignId());
			}
		}
	}

	public List<AdidUser> match(BroadbandLog bean)
	{
		if (!this.hosts.containsKey(bean.getHost()))
		{
			return null;
		}
		else
		{
			List<String> campaignIds = this.hosts.get(bean.getHost());

			List<AdidUser> result = new ArrayList<AdidUser>();

			for (String campaignId : campaignIds)
			{
				AdidUser user = new AdidUser();
				user.setAdid(bean.getAdid());
				user.setCampaignId(campaignId);
				user.setType("host");

				result.add(user);

				log.debug("匹配到host[{}] 人群 日志: {}", bean.getHost(), bean.toString());
			}

			return result;
		}
	}
}
