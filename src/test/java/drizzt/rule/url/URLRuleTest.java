package drizzt.rule.url;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lakenono.db.BaseBean;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class URLRuleTest {
	
	private List<String> hosts ;
	private URLRule urlRule ;
	
	@Before
	public void before() throws Exception{
		hosts = new ArrayList<>();
		urlRule = new URLRule();
		
		List<URLRuleBean> beans = BaseBean.getAll(URLRuleBean.class);
		for(URLRuleBean b : beans){
			System.out.println("init host : " + b.getHost());
			hosts.add(b.getHost());
		}
	}
	
	@Test
	public void test() throws Exception{
		String fileName = "drizzt/rule/url/testurl.txt";
		LineIterator lineIterator = FileUtils.lineIterator(new File(URLRuleTest.class.getResource("/").getFile(),fileName));

		while (lineIterator.hasNext()) {
			String url = lineIterator.nextLine();

			
			if (StringUtils.isBlank(url)) {
				continue;
			}
			
			match(url);
		}
	}
		
	private void match(String url) throws Exception{
		if(StringUtils.equals(url, "--")){
			return ;
		}
		
		String host = new URL(url).getHost();
		
		if(hosts.contains(host)){
			System.out.println("Find url : " +  url);
		}
		
		String id = urlRule.match(url, host);
		if(id!=null){
			System.out.println(id + " | " + url);
		}
		
	}

}
