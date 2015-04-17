package drizzt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang.StringUtils;

@Data
@EqualsAndHashCode(callSuper = false)
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

	@Override
	public String toString()
	{
		return "BroadbandLog [adid=" + adid + ", url=" + url + ", ref=" + ref + ", ua=" + ua + ", ts=" + ts + ", cookie=" + StringUtils.substring(cookie, 0, 10) + ", srcip=" + srcip + ", dstip=" + dstip + "]";
	}

}
