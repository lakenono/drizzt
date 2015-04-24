package drizzt.match.domain;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import lakenono.core.GlobalComponents;
import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_business_domain")
public class Domain extends BaseBean
{
	public static void main(String[] args) throws SQLException
	{
		new Domain().buildTable();
	}

	private String campaignId;
	private String domain;

	public static List<Domain> loadAll() throws SQLException
	{
		return GlobalComponents.db.getRunner().query("select * from " + BaseBean.getTableName(Domain.class), new BeanListHandler<Domain>(Domain.class));
	}
}
