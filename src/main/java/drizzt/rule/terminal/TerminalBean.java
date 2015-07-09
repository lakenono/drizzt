package drizzt.rule.terminal;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBConstraintPK;
import lakenono.db.annotation.DBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_data_terminal")
public class TerminalBean extends BaseBean {
	@DBConstraintPK
	private String adid;
	// 终端类型
	@DBConstraintPK
	private String terminalFlag;
	// 终端信息
	@DBConstraintPK
	private String terminalData;

	public TerminalBean(String terminalFlag, String terminalData) {
		this.terminalFlag = terminalFlag;
		this.terminalData = terminalData;
	}

	public static class TerminalFlag {
		public static final String IDFA = "idfs";
		public static final String IMEI = "imei";
		public static final String IMSI = "imsi";
		public static final String MAC = "mac";
		public static final String PHONE_NO = "phone_no";
	}
	
	public static void main(String[] args) throws Exception{
		new TerminalBean().buildTable();
	}
}
