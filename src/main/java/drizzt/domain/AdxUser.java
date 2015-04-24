package drizzt.domain;

import java.sql.SQLException;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBField;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@DBTable(name = "drizzt_data_adxuser")
@EqualsAndHashCode(callSuper = false)
public class AdxUser extends BaseBean
{
	@DBConstraintPK
	private String adid;
	@DBConstraintPK
	private String adxtype;

	@DBField(type = "varchar(500)")
	private String adxcookie;
	private String dspurl; // 回调

	public static void main(String[] args) throws SQLException
	{
		new AdxUser().buildTable();
	}
}
