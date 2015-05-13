package httpclient;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import drizzt.domain.AdxUser;

@Slf4j
public class MappingDemo
{
	private CookieMappingDemo mapping = new CookieMappingDemo();

	@Test
	public void tanx() throws ClientProtocolException, IOException
	{
		AdxUser user = new AdxUser();
		user.setAdid("a502045502519705");
		user.setAdxtype("cms.tanx.com");
		//user.setAdxcookie("cna=Rqq2DXOOchACATwP+TgVy67S");
		user.setAdxcookie("cna=jH+ODdGp3ngCAT3oC8X7qQf2; cnaui=140126313");
		

		AdxUser result = mapping.mapping(user);

		log.info(result.getDspurl());
	}
	
	@Test
	public void baidu() throws ClientProtocolException, IOException
	{
		AdxUser user = new AdxUser();
		user.setAdid("d9038220702");
		user.setAdxtype("cm.pos.baidu.com");
		//user.setAdxcookie("BAIDUID=B7FE2E14605D20B96D6F0A3C5D9F4689:FG=1; UDID=5ED3C76F8B9EE5000B5BB25627A12984:FG=1; LDID=5ED3C76F8B9EE5000B5BB25627A12984:FG=1; FP=AEB2C9C59C24C8B81DD7B16B1E5A9C45:FG=1; FP2=AEB2C9C59C24C8B81DD7B16B1E5A9C45:FG=1; CPROID=35E4DB75B89E5ACAF6CB9188D1649CF6:FG=1; ETID=8745F532257CFF1201442008E5611D44:FG=1; ISBID=B7FE2E14605D20B96D6F0A3C5D9F4689:FG=1; ISUS=35E4DB75B89E5ACAF6CB9188D1649CF6:FG=1");
		user.setAdxcookie("BAIDUID=7CAA27E947F68A8333DDC67C4CA8F5D9:FG=1; CPROID=8F68211297BC229B79161B94AAC47478:FG=1; BIDUPSID=7CAA27E947F68A8333DDC67C4CA8F5D9");

		AdxUser result = mapping.mapping(user);

		log.info(result.getDspurl());
	}
}
