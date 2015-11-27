package drizzt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lakenono.db.BaseBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import drizzt.domain.AdidUser;
import drizzt.domain.AdxUser;
import drizzt.domain.BroadbandLog;

@Slf4j
public class ADXCookieFindDemo
{
	private File file;

	private Set<String> adids = new HashSet<String>();
	private Set<String> adxHosts = new HashSet<String>();

	public ADXCookieFindDemo(String filepath) throws SQLException
	{
		this.file = new File(filepath);

		// init adids
		List<AdidUser> adidUsers = BaseBean.getAll(AdidUser.class);
		for (AdidUser adidUser : adidUsers)
		{
			this.adids.add(adidUser.getAdid());
		}
		// 做关联adxcookie字段
		//GlobalComponents.db.getRunner().query("select * from "+" where ", new BeanListHandler<AdidUser>(AdidUser.class));

		// init adxhosts
		this.adxHosts.add("cms.tanx.com");
		this.adxHosts.add("cm.pos.baidu.com");
		this.adxHosts.add("sax.sina.com.cn");
		this.adxHosts.add("cm.allyes.com");
	}

	private void run() throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, SQLException
	{
		LineIterator lineIterator = FileUtils.lineIterator(this.file);

		while (lineIterator.hasNext())
		{
			String line = lineIterator.nextLine();
			BroadbandLog bean = BroadbandLog.convertLine(line);

			if (bean == null)
			{
				continue;
			}

			// 排除掉非cookiemapping地址
			if (!this.adxHosts.contains(bean.getHost()))
			{
				continue;
			}

			// 排除掉cookie不可用数据
			if (bean.getCookie() == null || bean.getCookie().equals("null") || bean.getCookie().equals("--"))
			{
				continue;
			}

			if (!this.adids.contains(bean.getAdid()))
			{
				continue;
			}

			AdxUser user = new AdxUser();
			user.setAdid(bean.getAdid());
			user.setAdxtype(bean.getHost());
			user.setAdxcookie(bean.getCookie()); //TODO 可以update cookie... 后续再说吧

			user.saveOrUpdate();

			log.info(bean.toString());
		}
	}

	public static void main(String[] args) throws IOException, SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		new ADXCookieFindDemo("/Volumes/lake/9-临时数据/20150417.txt_00").run();
	}

}
