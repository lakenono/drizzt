package drizzt.match.domain;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_business_host")
public class Host extends BaseBean{
	private String campaignId;
	private String host;
	
	public static void main(String[] args) throws Exception{
		new Host().buildTable();
	}
}
