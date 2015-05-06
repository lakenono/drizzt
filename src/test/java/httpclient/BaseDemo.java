package httpclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BaseDemo
{
	public static void main(String[] args) throws ClientProtocolException, IOException
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
}
