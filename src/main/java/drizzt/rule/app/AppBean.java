package drizzt.rule.app;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DBTable(name = "drizzt_data_app")
public class AppBean extends BaseBean {
	@DBConstraintPK
	private String adId;
	@DBConstraintPK
	private String appId;
	private String action;

	public AppBean(String appId, String action) {
		this.adId = appId;
		this.action = action;
	}

	public static void main(String[] args) throws Exception {
		new AppBean().buildTable();
	}
}
