package drizzt.domain;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

/**
 * 宽带运营商日志
 * @author Lakenono
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class BroadbandLog
{
	private String adid;
	private String url;
	private String ref;
	private String ua;
	private String ts;
	private String cookie;
	private String srcip;
	private String dstip;

	private String host;
	private String domain;

	@Override
	public String toString()
	{
		return "BroadbandLog [adid=" + adid + ", url=" + url + ", ref=" + ref + ", ua=" + ua + ", ts=" + ts + ", cookie=" + StringUtils.substring(cookie, 0, 10) + ", srcip=" + srcip + ", dstip=" + dstip + "]";
	}

	public static BroadbandLog convertLine(String line) throws MalformedURLException
	{
		try
		{
			String[] s = StringUtils.split(line, "\t");

			// 判断字段是否8
			if (s.length != 8)
			{
				return null;
			}

			BroadbandLog bean = new BroadbandLog();
			bean.setTs(s[0]);
			bean.setSrcip(s[1]);
			bean.setAdid(s[2]);
			bean.setUrl(s[3]);
			bean.setRef(s[4]);
			bean.setUa(s[5]);
			bean.setDstip(s[6]);
			bean.setCookie(s[7]);

			// adid为空的数据不可用
			if (StringUtils.equals(bean.getAdid(), "--"))
			{
				return null;
			}

			bean.setHost(new URL(bean.getUrl()).getHost());
			bean.setDomain(getDomain(bean.getHost()));

			//log.debug(bean.toString());

			return bean;
		}
		catch (Exception e)
		{
			log.error("error: {}", line);
			throw new RuntimeException(e);
		}
	}

	public static String getDomain(String host)
	{
		if (host.startsWith("www."))
		{
			return StringUtils.remove(host, "www.");
		}

		String[] split = StringUtils.split(host, ".");

		String suffix = split[split.length - 1];
		String domain = split[split.length - 2];

		return domain + "." + suffix;
	}
}
