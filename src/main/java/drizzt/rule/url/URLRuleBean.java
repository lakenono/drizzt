package drizzt.rule.url;

import java.util.regex.Pattern;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBField;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@DBTable(name = "drizzt_rule_url")
@EqualsAndHashCode(callSuper = false)
public class URLRuleBean extends BaseBean {
	private String id;
	private String name;
	private String host;
	private String site;//站点
	private String patternstr;
	private String action;
	private String type;
	private String classify;

	// 非持久化属性
	@DBField(serialization=false)
	private Pattern pattern = null;

	public Pattern getPattern() {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(this.patternstr, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}

	public static void main(String[] args) throws Exception {
		new URLRuleBean().buildTable();
	}
}
