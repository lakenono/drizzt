package drizzt.rule.industry.ec;

import java.util.regex.Pattern;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBField;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import drizzt.rule.url.URLRuleBean;

@Data
@DBTable(name = "drizzt_rule_ec")
@EqualsAndHashCode(callSuper = false)
public class ECRuleBean extends BaseBean {
	private String id;
	private String name;
	private String host;
	private String site;
	private String regex;
	private String idtype;
	private String action;
	private String from;

	// 非持久化属性
	@DBField(serialization = false)
	private Pattern pattern = null;

	public Pattern getPattern() {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(this.regex, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}

	public static void main(String[] args) throws Exception {
		new URLRuleBean().buildTable();
	}
}
