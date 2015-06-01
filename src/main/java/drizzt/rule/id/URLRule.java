package drizzt.rule.id;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import lakenono.db.BaseBean;

public class URLRule extends BaseBean {
	private Map<String,List<URLRuleBean>> rules = new HashMap<>();
	
	public URLRule() throws SQLException{
		List<URLRuleBean> ruleBeans = BaseBean.getAll(URLRuleBean.class);
		for(URLRuleBean r : ruleBeans){
			List<URLRuleBean> urlRules = rules.get(r.getHost());
			if(urlRules==null){
				urlRules = new ArrayList<>();
				rules.put(r.getHost(), urlRules);
			}
			
			urlRules.add(r);
		}
	}
	
	/**
	 * 对url进行规则解析，返回特征值
	 * 
	 * @param url
	 * @param host
	 * @return
	 */
	public String match(String url,String host){
		//根据host获取规则
		List<URLRuleBean> idRules = rules.get(host);
		if(idRules==null){
			return null;
		}
		
		for(URLRuleBean r : idRules){
			Matcher matcher = r.getPattern().matcher(url);

			if (matcher.find())
			{
				return matcher.group(1);
			}
		}
		return null;
	}
	
}
