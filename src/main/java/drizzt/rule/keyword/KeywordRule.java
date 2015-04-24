package drizzt.rule.keyword;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

@Slf4j
public class KeywordRule
{
	private Map<String, List<KeywordRuleBean>> rules = new HashMap<String, List<KeywordRuleBean>>();

	public KeywordRule() throws SQLException
	{
		List<KeywordRuleBean> beans = BaseBean.getAll(KeywordRuleBean.class);

		log.info("keyword rules {}", beans.size());

		for (KeywordRuleBean bean : beans)
		{
			// 如果该host不存在
			if (!this.rules.containsKey(bean.getHost()))
			{
				this.rules.put(bean.getHost(), new LinkedList<KeywordRuleBean>());
			}

			this.rules.get(bean.getHost()).add(bean);
		}
	}

	public String match(String url, String host) throws UnsupportedEncodingException, ParseException
	{
		String result = null;

		if (!this.rules.containsKey(host))
		{
			return result;
		}

		for (KeywordRuleBean rule : this.rules.get(host))
		{

			String urltmp = url;

			if (!StringUtils.isEmpty(rule.getDecode()))
			{
				urltmp = URLDecoder.decode(url, rule.getDecode()); // 解码
			}

			Matcher matcher = rule.getPattern().matcher(urltmp);

			if (matcher.find())
			{
				result = matcher.group(1);
			}
		}

		return result;
	}
}
