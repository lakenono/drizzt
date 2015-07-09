package drizzt.rule.app;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_rule_app")
public class AppRuleBean extends BaseBean {
	private String id;
	private String host;
	private String regex;
	private String action;
	private String appId;

	// 非持久化属性
	private Pattern pattern = null;

	public Pattern getPattern() {
		if(StringUtils.isBlank(this.regex)){
			return null;
		}
		if (this.pattern == null) {
			this.pattern = Pattern.compile(this.regex, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}
}
