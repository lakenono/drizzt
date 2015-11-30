package drizzt.rule.industry.ec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lakenono.db.DBBean;
import lakenono.db.annotation.DBField;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;

@Data
@DBTable(name = "drizzt_rule_ec")
@EqualsAndHashCode(callSuper = false)
public class ECRuleBean extends DBBean {
	private String id;
	private String name;
	private String site;
	private String idType;
	private String action;
	private String source;

	private String host;
	private String matchRule;
	private String extractRule;

	@DBField(serialization = false)
	private Pattern matchPattern;
	@DBField(serialization = false)
	private Pattern extractPattern;

	public void init() {
		if (StringUtils.isBlank(matchRule)) {
			throw new IllegalArgumentException("RegexRule matchRuleStr can not blank.");
		}
		matchPattern = Pattern.compile(matchRule, Pattern.CASE_INSENSITIVE);

		// 初始化提取规则
		if (!StringUtils.isBlank(extractRule)) {
			extractPattern = Pattern.compile(extractRule, Pattern.CASE_INSENSITIVE);
		}
	}

	/**
	 * 匹配
	 * 
	 * @param matchStr
	 * @return
	 */
	protected boolean match(String matchStr) {
		if (StringUtils.isBlank(matchStr)) {
			return false;
		}
		// 检测是否满足匹配规则
		Matcher matcher = matchPattern.matcher(matchStr);
		return matcher.find();
	}

	/**
	 * 提取
	 * 
	 * @param extractStr
	 * @param result
	 * @return
	 */
	protected String extract(String extractStr) {
		// 检测提取串是否能提取
		if (StringUtils.isBlank(extractStr)) {
			return null;
		}

		Pattern pattern = extractPattern;
		// 检测是否有提取规则
		if (extractPattern == null) {
			pattern = matchPattern;
		}

		// 匹配提取
		Matcher matcher = pattern.matcher(extractStr);
		if (!matcher.find()) {
			return null;
		}

		if (matcher.groupCount() > 0) {
			return matcher.group(1);
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		DBBean.createTable(ECRuleBean.class);
	}
}
