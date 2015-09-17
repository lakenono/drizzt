package drizzt.rule.app;

import drizzt.rule.account.AccountBean;
import lakenono.db.DBBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DBTable(name = "drizzt_data_app")
public class AppBean extends DBBean {
	@DBConstraintPK
	private String adid;
	@DBConstraintPK
	private String appId;
	private String action;

	public AppBean(String appId, String action) {
		this.appId = appId;
		this.action = action;
	}

	public static void main(String[] args) throws Exception {
		AccountBean.createTable(AppBean.class);
	}
}
