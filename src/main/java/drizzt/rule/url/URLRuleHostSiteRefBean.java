package drizzt.rule.url;

import java.util.List;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@DBTable(name = "drizzt_rule_host_site_ref")
@EqualsAndHashCode(callSuper = false)
public class URLRuleHostSiteRefBean extends BaseBean{
	private String host;
	private String site;
	
	public static void main(String[] args) throws Exception{
//		new URLRuleHostSiteRefBean().buildTable();
		
		List<URLRuleHostSiteRefBean> hostSiteRefs = BaseBean.getAll(URLRuleHostSiteRefBean.class);
		for(URLRuleHostSiteRefBean b : hostSiteRefs){
			System.out.println(b);
		}
	}
}
