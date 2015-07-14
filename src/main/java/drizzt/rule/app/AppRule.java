package drizzt.rule.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@Slf4j
public class AppRule {
	// key : host ; value 该host下的app规则列表
	private Map<String, List<AppRuleBean>> rules = new HashMap<String, List<AppRuleBean>>();

	public AppRule() throws Exception {
		List<AppRuleBean> dbRules = BaseBean.getAll(AppRuleBean.class);

		int effectiveRuleCount = 0;
		for (AppRuleBean r : dbRules) {

			if (StringUtils.isBlank(r.getAppId())) {
				continue;
			}

			List<AppRuleBean> appRules = rules.get(r.getHost());
			if (appRules == null) {
				appRules = new LinkedList<AppRuleBean>();
				rules.put(r.getHost(), appRules);
			}

			appRules.add(r);
			effectiveRuleCount++;
		}

		log.info("AppRule init : total rules {}, effective rules {} 。", dbRules.size(), effectiveRuleCount);
	}

	public AppBean match(String host, String url) {
		if (StringUtils.isBlank(host) || StringUtils.isBlank(url)) {
			// 输入不合法
			return null;
		}

		List<AppRuleBean> appRules = rules.get(host);
		if (appRules == null || appRules.isEmpty()) {
			// host没有提取规则
			return null;
		}

		AppBean app = null;

		Iterator<AppRuleBean> iter = appRules.iterator();
		while (iter.hasNext()) {
			AppRuleBean r = iter.next();

			try {
				if (r.getPattern() == null) {
					app = new AppBean(r.getAppId(), r.getAction());
					continue;
				}

				// 正则匹配识别
				Matcher matcher = r.getPattern().matcher(url);
				if (matcher.find()) {
					app = new AppBean(r.getAppId(), r.getAction());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("regex error : {} ", r);
				iter.remove();
			}
		}

		return app;
	}
}
