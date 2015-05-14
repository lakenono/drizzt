package httpclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

// 原始未封装的mapping
public class BaseMappingDemo
{

	CloseableHttpClient httpclient = HttpClients.createDefault();

	//标准302跳转
	@Test
	public void taxn() throws ClientProtocolException, IOException
	{
		HttpGet get = new HttpGet("http://cms.tanx.com/t.gif?id=34462052");
		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		get.setConfig(config);

		get.setHeader("Host", "cms.tanx.com");
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Cookie", "cna=jH+ODdGp3ngCAT3oC8X7qQf2; cnaui=140126313");

		CloseableHttpResponse response = httpclient.execute(get);

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			System.out.println(header.toString());
		}

		Header[] headers = response.getAllHeaders();
		for (Header header : headers)
		{
			System.out.println(header.toString());
		}
	}

	@Test
	public void bes1() throws ClientProtocolException, IOException
	{

		HttpGet get = new HttpGet("http://cm.pos.baidu.com/pixel?dspid=7826902");
		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		get.setConfig(config);

		get.setHeader("Host", "cm.pos.baidu.com");
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Cookie", "BAIDUID=7CAA27E947F68A8333DDC67C4CA8F5D9:FG=1; CPROID=8F68211297BC229B79161B94AAC47478:FG=1; BIDUPSID=7CAA27E947F68A8333DDC67C4CA8F5D9");

		CloseableHttpResponse response = httpclient.execute(get);

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			System.out.println(header.toString());
		}

		Header[] headers = response.getAllHeaders();
		for (Header header : headers)
		{
			System.out.println(header.toString());
		}
	}

	@Test
	public void BES() throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		//HttpPost post = new HttpPost("http://cm.pos.baidu.com/pixel?dspid=6770394"); //定象
		HttpPost post = new HttpPost("http://cm.pos.baidu.com/pixel?dspid=7826902"); //京东

		post.setHeader("Host", "cm.pos.baidu.com");
		post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:29.0) Gecko/20100101 Firefox/29.0");
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Cookie", "BAIDUID=7CAA27E947F68A8333DDC67C4CA8F5D9:FG=1; CPROID=8F68211297BC229B79161B94AAC47478:FG=1; BIDUPSID=7CAA27E947F68A8333DDC67C4CA8F5D9");
		post.setHeader("Connection", "keep-alive");

		CloseableHttpResponse response = httpclient.execute(post);

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			System.out.println(header.toString());
		}

		Header[] headers = response.getAllHeaders();
		for (Header header : headers)
		{
			System.out.println(header.toString());
		}
	}

	//标准302跳转
	@Test
	public void sax() throws ClientProtocolException, IOException
	{
		HttpGet get = new HttpGet("http://sax.sina.com.cn/cm?sina_nid=19");
		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		get.setConfig(config);

		get.setHeader("Host", "sax.sina.com.cn");
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Cookie", "SINAGLOBAL=61.232.11.197_1428464596.804916; sso_info=v02m6alo5qztayYlq2liZSUtYmTpLGJlImDiaeVqZmDtLOMg5C2jKOEuI6TiLU=; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5_PHKJfY-qMKfXWK_WG3XI; ALF=1462583671");

		CloseableHttpResponse response = httpclient.execute(get);

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			System.out.println(header.toString());
		}

		Header[] headers = response.getAllHeaders();
		for (Header header : headers)
		{
			System.out.println(header.toString());
		}
	}

	//标准302跳转
	@Test
	public void allyes() throws ClientProtocolException, IOException
	{
		HttpGet get = new HttpGet("http://cm.allyes.com/pixel?allyes_dspid=174&allyes_cm&ext_data=");
		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
		get.setConfig(config);

		get.setHeader("Host", "cm.allyes.com");
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Cookie", "ALLYESID4=LygHZ33e42A5yGX7SRZ");

		CloseableHttpResponse response = httpclient.execute(get);

		Header[] headers2 = response.getHeaders("Location");
		for (Header header : headers2)
		{
			System.out.println(header.toString());
		}

		Header[] headers = response.getAllHeaders();
		for (Header header : headers)
		{
			System.out.println(header.toString());
		}
	}

}
