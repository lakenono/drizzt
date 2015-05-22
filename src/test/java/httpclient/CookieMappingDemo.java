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
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import drizzt.domain.AdxUser;

// mapping封装
@Slf4j
public class CookieMappingDemo
{
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException
	{
		while (true)
		{
			try
			{
				new CookieMappingDemo().run();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private Map<String, String> mappingUrls = new HashMap<String, String>();

	public CookieMappingDemo()
	{
		// 定象优动
		this.mappingUrls.put("cms.tanx.com", "http://cms.tanx.com/t.gif?id=34462052");
		this.mappingUrls.put("cm.pos.baidu.com", "http://cm.pos.baidu.com/pixel?dspid=6770394");
		this.mappingUrls.put("sax.sina.com.cn", "http://sax.sina.com.cn/cm?sina_nid=19");
		this.mappingUrls.put("cm.allyes.com", "http://cm.allyes.com/pixel?allyes_dspid=174&allyes_cm&ext_data=");
	}

	public void run() throws SQLException, InterruptedException
	{
		List<AdxUser> adxUsers = GlobalComponents.db.getRunner().query("select * from " + BaseBean.getTableName(AdxUser.class) + " where dspurl is null limit 100", new BeanListHandler<AdxUser>(AdxUser.class));

		for (AdxUser adxUser : adxUsers)
		{
			AdxUser result = null;

			try
			{
				result = this.mapping(adxUser);
			}
			catch (ClientProtocolException e)
			{
				log.error("{}", e);
			}
			catch (IOException e)
			{
				log.error("{}", e);
			}

			if (result != null)
			{
				log.info(result.getDspurl());
				GlobalComponents.db.getRunner().update("update drizzt_data_adxuser set dspurl=? where adid=?", result.getDspurl(), result.getAdid());
			}

			Thread.sleep(2000);
		}
	}

	public AdxUser mapping(AdxUser adxUser) throws ClientProtocolException, IOException
	{
		String url = this.mappingUrls.get(adxUser.getAdxtype());
		String cookie = adxUser.getAdxcookie();
		String host = adxUser.getAdxtype();

		log.info("begin {}", url);
		log.debug(adxUser.toString());

		HttpGet get = new HttpGet("http://cms.tanx.com/t.gif?id=34462052");

		Builder configBuilder = RequestConfig.custom().setRedirectsEnabled(false);
		if (true) //是否设置超时
		{
			int timeout = 10000;
			configBuilder.setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout);
		}
		RequestConfig config = configBuilder.build();
		get.setConfig(config);

		get.setHeader("Host", host);
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Cookie", cookie);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(get);
		httpclient.close();

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			adxUser.setDspurl(header.getValue());
		}

		return adxUser;
	}
}
