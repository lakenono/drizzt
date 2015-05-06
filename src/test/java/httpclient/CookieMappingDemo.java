package httpclient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lakenono.core.GlobalComponents;
import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import drizzt.domain.AdxUser;

@Slf4j
public class CookieMappingDemo
{
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException
	{
		new CookieMappingDemo().run();
	}

	private Map<String, String> mappingUrls = new HashMap<String, String>();
	private CloseableHttpClient httpclient = HttpClients.createDefault();

	public CookieMappingDemo()
	{
		// 定象优动
		this.mappingUrls.put("cms.tanx.com", "http://cms.tanx.com/t.gif?id=34462052");
		this.mappingUrls.put("cm.pos.baidu.com", "http://cm.pos.baidu.com/pixel?dspid=6770394");
		this.mappingUrls.put("sax.sina.com.cn", "http://sax.sina.com.cn/cm?sina_nid=19");
		this.mappingUrls.put("cm.allyes.com", "http://cm.allyes.com/pixel?allyes_dspid=174&allyes_cm&ext_data=");
	}

	public void run() throws SQLException, ClientProtocolException, IOException
	{
		List<AdxUser> adxUsers = GlobalComponents.db.getRunner().query("select * from " + BaseBean.getTableName(AdxUser.class) + " where dspurl is null", new BeanListHandler<AdxUser>(AdxUser.class));

		for (AdxUser adxUser : adxUsers)
		{
			AdxUser result = this.mapping(adxUser);
			log.info(result.getDspurl());
		}
	}

	public AdxUser mapping(AdxUser adxUser) throws ClientProtocolException, IOException
	{
		String url = this.mappingUrls.get(adxUser.getAdxtype());
		String cookie = adxUser.getAdxcookie();
		String host = adxUser.getAdxtype();

		log.info("begin {}", url);
		log.debug(adxUser.toString());

		HttpPost post = new HttpPost(url);
		post.setHeader("Host", host);
		post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:29.0) Gecko/20100101 Firefox/29.0");
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Cookie", cookie);
		post.setHeader("Connection", "keep-alive");

		CloseableHttpResponse response = httpclient.execute(post);

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			adxUser.setDspurl(header.getValue());
		}

		return adxUser;
	}
}
