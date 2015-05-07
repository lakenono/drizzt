package drizzt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import drizzt.domain.AdidUser;
import drizzt.domain.BroadbandLog;
import drizzt.match.DomainMatch;
import drizzt.match.KeywordMatch;

@Slf4j
public class AdidUserFindDemo
{
	private File file;

	private int count;

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

			count++;
		}

		log.info("success -- {} -- {}", count, file.getPath());
	}

	public static void main(String[] args) throws IOException, SQLException, ParseException, IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		log.info("adid user find v0.2 begin");
		new AdidUserFindDemo(args[0]).run();

	}
}
