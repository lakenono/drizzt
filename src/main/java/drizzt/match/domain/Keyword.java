package drizzt.match.domain;

import java.sql.SQLException;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_business_keyword")
public class Keyword extends BaseBean
{
	public static void main(String[] args) throws SQLException
	{
		new Keyword().buildTable();
	}

	private String campaignId;
	private String keyword;
}
