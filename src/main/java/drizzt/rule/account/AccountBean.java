package drizzt.rule.account;

import lombok.Data;

@Data
public class AccountBean {
	// 用户id
	private String adid;
	// 账户
	private String account;
	// 账户类型
	private String accountType;
	// 站点标志
	private String site;
}
