package drizzt.rule.url;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_data_goods")
public class URLBean extends BaseBean {

	@DBConstraintPK
	private String adid;
	// 特征
	@DBConstraintPK
	private String urlFeture;
	// 站点
	@DBConstraintPK
	private String site;
	// 动作
	private String action;
	// 类型
	private String type;

	public static void main(String[] args) throws Exception {
		new URLBean().buildTable();
	}
}
