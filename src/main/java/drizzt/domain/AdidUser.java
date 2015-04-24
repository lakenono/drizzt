package drizzt.domain;

import java.sql.SQLException;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@DBTable(name = "drizzt_data_adiduser")
public class AdidUser extends BaseBean
{
	@DBConstraintPK
	private String adid;

	@DBConstraintPK
	private String campaignId;
	private String type; // 筛选来源 host kw url

	public static void main(String[] args) throws SQLException
	{
		new AdidUser().buildTable();
	}
}
