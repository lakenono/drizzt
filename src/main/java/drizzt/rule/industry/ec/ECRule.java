package drizzt.rule.industry.ec;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lakenono.db.BaseBean;
import lakenono.db.DBBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ECRule extends BaseBean {

	// key host ; value rule
	private Map<String, List<ECRuleBean>> rules = new HashMap<String, List<ECRuleBean>>();

	public ECRule() throws SQLException {
		List<ECRuleBean> dbRules = DBBean.getAll(ECRuleBean.class);

		for (ECRuleBean r : dbRules) {
			try {
				r.init();
			} catch (Exception e) {
				log.warn("ecRule init error : {}", r, e);
			}

			List<ECRuleBean> ecRules = rules.get(r.getHost());

			if (ecRules == null) {
				ecRules = new ArrayList<ECRuleBean>();
				rules.put(r.getHost(), ecRules);
			}

			ecRules.add(r);
		}
		log.info("url rule init : rule count {} 。 ", dbRules.size());
	}

	/**
	 * 对url进行规则解析，返回特征值
	 * 
	 * @return
	 */
	public ECBean match(String host, String url) {
		if (StringUtils.isBlank(host) || StringUtils.isBlank(url)) {
			return null;
		}
		// 根据host获取规则
		List<ECRuleBean> ecRules = rules.get(host);
		if (ecRules == null) {
			return null;
		}

		ECBean ecBean = null;

		Iterator<ECRuleBean> iter = ecRules.iterator();
		while (iter.hasNext()) {
			ECRuleBean r = iter.next();

			try {
				if (r.match(url)) {
					ecBean = new ECBean();
					ecBean.setAction(r.getAction());
					ecBean.setSource(r.getSource());
					ecBean.setSite(r.getSite());

					String extractStr = r.extract(url);
					if (extractStr != null) {
						ecBean.setEcId(extractStr);
						ecBean.setIdType(r.getIdType());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("regex error : {} ", r);
				iter.remove();
			}
		}
		return ecBean;
	}

}
