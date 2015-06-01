package drizzt.match.domain;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_business_id")
public class URL extends BaseBean{
	private String campaignId;
	private String feature;
	
	public static void main(String[] args) throws Exception{
		new URL().buildTable();
	}
}
