package drizzt.rule.url;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLRule extends BaseBean {

	// key host ; value rule
	private Map<String, List<URLRuleBean>> rules = new HashMap<String, List<URLRuleBean>>();

	public URLRule() throws SQLException {
		List<URLRuleBean> dbRules = BaseBean.getAll(URLRuleBean.class);

		for (URLRuleBean r : dbRules) {

			List<URLRuleBean> urlRules = rules.get(r.getHost());

			if (urlRules == null) {
				urlRules = new ArrayList<>();
				rules.put(r.getHost(), urlRules);
			}

			urlRules.add(r);
		}
		log.info("url rule init : rule count {} 。 ", dbRules.size());
	}

	/**
	 * 对url进行规则解析，返回特征值
	 * 
	 * @return
	 */
	public URLBean match(String host, String url) {
		if (StringUtils.isBlank(host) || StringUtils.isBlank(url)) {
			return null;
		}
		// 根据host获取规则
		List<URLRuleBean> idRules = rules.get(host);
		if (idRules == null) {
			return null;
		}

		URLBean urlBean = null;
		for (URLRuleBean r : idRules) {
			Matcher matcher = r.getPattern().matcher(url);

			if (matcher.find()) {
				try {
					String urlFeture = matcher.group(1);
					urlBean = new URLBean();
					urlBean.setUrlFeture(urlFeture);
					urlBean.setSite(r.getSite());
					urlBean.setType(r.getClassify());
					urlBean.setAction(r.getAction());
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					log.error("regex error : {} ", r);
				}
			}
		}
		return urlBean;
	}

}
