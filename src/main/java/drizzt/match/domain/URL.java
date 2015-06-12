package drizzt.match.domain;

import lakenono.db.BaseBean;
import lakenono.db.annotation.DBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@DBTable(name = "drizzt_business_url")
public class URL extends BaseBean{
	private String campaignId; 
	private String site; //站点名
	private String urlFeature; // url特征
	
	public static void main(String[] args) throws Exception{
		new URL().buildTable();
	}
}
