package drizzt.rule.account;

import java.util.regex.Pattern;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBField;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_rule_account")
public class AccountRuleBean extends BaseBean {
	private String id;
	private String host;
	private String regex;

	// 站点标志-账户是什么网站的
	private String site;

	// 账户类型—email，cookieid，username等
	private String accountType;

	// 提取来源：url，cookies
	private String matchSource;

	@DBField(serialization = false)
	private Pattern pattern = null;

	public Pattern getPattern() {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}

	public static class AccountRuleMatchSource {
		public static final String URL = "url";
		public static final String COOKIE = "cookie";
	}

	public static void main(String[] args) throws Exception {
		new AccountRuleBean().buildTable();
	}
}
