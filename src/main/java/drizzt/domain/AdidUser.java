package drizzt.domain;

import java.sql.SQLException;

import lakenono.db.DBBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import drizzt.conf.Configuration;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_data_adiduser")
public class AdidUser extends DBBean {
	public AdidUser() {
		area = Configuration.getString("datasource");
	}

	@DBConstraintPK
	private String adid;

	@DBConstraintPK
	private String campaignId;

	private String type; // 筛选来源 host kw url

	private String area; // 数据来自哪个省

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(adid).append(',');
		result.append(campaignId).append(',');
		result.append(type).append(',');
		result.append(area);
		return result.toString();
	}

	public static void main(String[] args) throws SQLException {
		DBBean.createTable(AdidUser.class);
	}
}
