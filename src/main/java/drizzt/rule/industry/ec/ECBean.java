package drizzt.rule.industry.ec;

import lakenono.db.DBBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_data_ec")
public class ECBean extends DBBean {

	//用户id
	@DBConstraintPK
	private String adid;
	
	// 电商id
	@DBConstraintPK
	private String ecId;
	
	// 电商id类型
	private String idType;
	
	// 站点
	@DBConstraintPK
	private String site;
	
	// 动作
	@DBConstraintPK
	private String action;
	
	//来源
	private String source;

	public static void main(String[] args) throws Exception {
		DBBean.createTable(ECBean.class);
	}
}
