package utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlDemo
{
	public static void main(String[] args) throws MalformedURLException
	{

		String url = "http://ct.wonnder.com/tanx_dsp/cm.php?tid=v1Zg4tVTHT4%3D&ver=1";

		URL u = new URL(url);
		
		System.out.println(u.getHost());
		System.out.println(u.getQuery());

	}
}
