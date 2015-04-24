package drizzt.rule.keyword;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import lakenono.core.GlobalComponents;
import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.dbutils.handlers.BeanListHandler;

@Data
@DBTable(name = "drizzt_rule_search")
@EqualsAndHashCode(callSuper = false)
public class KeywordRuleBean extends BaseBean
{
	private String id;
	private String name;
	private String host;
	private String patternstr;
	private String action;
	private String type;
	private String classify;
	private String decode;
	private String checkdate;

	// 非持久化属性
	private Pattern pattern = null;

	public Pattern getPattern()
	{
		if (this.pattern == null)
		{
			this.pattern = Pattern.compile(this.patternstr, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}

	public static List<KeywordRuleBean> loadAll() throws SQLException
	{
		return GlobalComponents.db.getRunner().query("select * from " + BaseBean.getTableName(KeywordRuleBean.class), new BeanListHandler<KeywordRuleBean>(KeywordRuleBean.class));
	}
}
