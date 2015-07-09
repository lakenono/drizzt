package drizzt.rule.terminal;

import java.util.regex.Pattern;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_rule_terminal")
public class TerminalRuleBean extends BaseBean {
	private String id;
	private String terminalFlag;
	private String host;
	private String regex;

	// 非持久化属性
	private Pattern pattern = null;

	public Pattern getPattern() {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(this.regex, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}
}
