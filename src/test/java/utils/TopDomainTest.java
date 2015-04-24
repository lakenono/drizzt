package utils;

import drizzt.domain.BroadbandLog;

public class TopDomainTest
{
	public static void main(String[] args)
	{

		System.out.println(BroadbandLog.getDomain("www.baidu.com"));
		System.out.println(BroadbandLog.getDomain("search.baidu.tv"));
		System.out.println(BroadbandLog.getDomain("search.baidu.com"));
		System.out.println(BroadbandLog.getDomain("cm.pos.baidu.com"));

	}
}
