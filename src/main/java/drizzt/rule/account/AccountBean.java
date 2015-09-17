package drizzt.rule.account;

import lakenono.db.DBBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_data_account")
public class AccountBean extends DBBean {
	// 用户id
	@DBConstraintPK
	private String adid;
	// 账户
	@DBConstraintPK
	private String account;
	// 账户类型
	@DBConstraintPK
	private String accountType;
	// 站点标志
	private String site;

	public static void main(String[] args) throws Exception {
		DBBean.createTable(AccountBean.class);
	}
}
