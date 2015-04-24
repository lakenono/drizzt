package drizzt.match;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lakenono.db.BaseBean;

import org.apache.commons.lang.StringUtils;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.domain.Keyword;
import drizzt.rule.keyword.KeywordRule;

public class KeywordMatch
{
	private KeywordRule kwRule;
	private List<Keyword> keywords = new LinkedList<Keyword>();

	public KeywordMatch() throws SQLException
	{
		this.kwRule = new KeywordRule();
		this.keywords = BaseBean.getAll(Keyword.class);
	}

	public List<AdidUser> match(BroadbandLog bean) throws UnsupportedEncodingException, ParseException
	{
		String kw = null;

		// baidu的search通过ref来进行筛选
		if (StringUtils.contains(bean.getRef(), ".baidu.com"))
		{
			kw = this.kwRule.match(bean.getRef(), bean.getHost());
		}
		else
		{
			kw = this.kwRule.match(bean.getUrl(), bean.getHost());
		}

		// 如果没有关键词就停止解析
		if (kw == null)
		{
			return null;
		}

		List<AdidUser> result = new ArrayList<AdidUser>();

		// 匹配关键词. TODO 优化匹配算法.
		for (Keyword keyword : keywords)
		{
			boolean contains = StringUtils.contains(kw, keyword.getKeyword());

			if (contains)
			{
				AdidUser user = new AdidUser();
				user.setAdid(bean.getAdid());
				user.setCampaignId(keyword.getCampaignId());
				user.setType("keyword");
				result.add(user);
			}
		}

		return result;
	}
}
