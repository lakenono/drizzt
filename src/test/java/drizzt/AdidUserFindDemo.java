package drizzt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.DomainMatch;
import drizzt.match.KeywordMatch;

public class AdidUserFindDemo
{
	private File file;

	public AdidUserFindDemo(String file) throws SQLException
	{
		this.file = new File(file);
		this.domainMatch = new DomainMatch();
		this.keywordMatch = new KeywordMatch();
	}

	private DomainMatch domainMatch;
	private KeywordMatch keywordMatch;

	private void run() throws IOException, ParseException, IllegalArgumentException, IllegalAccessException, InstantiationException, SQLException
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

			// domain 人群
			List<AdidUser> domainAdidUsers = this.domainMatch.match(bean);

			// kw 人群
			List<AdidUser> keywordAdidUsers = this.keywordMatch.match(bean);

			// 根据 url kw domain的优先级持久化用户清单 TODO 升级update
			// 0. url
			// 1. kw 
			if (keywordAdidUsers != null && !keywordAdidUsers.isEmpty())
			{
				for (AdidUser adidUser : keywordAdidUsers)
				{
					adidUser.persistOnNotExist();
				}
			}

			// 2. domian
			if (domainAdidUsers != null && !domainAdidUsers.isEmpty())
			{
				for (AdidUser adidUser : domainAdidUsers)
				{
					adidUser.persistOnNotExist();
				}
			}

		}
	}

	public static void main(String[] args) throws IOException, SQLException, ParseException, IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		new AdidUserFindDemo("/Volumes/lake/9-临时数据/20150417.txt_00").run();
	}
}
