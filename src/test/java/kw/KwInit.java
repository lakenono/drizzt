package kw;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import drizzt.match.domain.Keyword;

@Slf4j
public class KwInit
{
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, SQLException
	{
		String campaignId = "jiuxian";

		File file = new File(KwInit.class.getResource("keyword.txt").getFile());

		String keywordFile = FileUtils.readFileToString(file);

		String[] kws = StringUtils.split(keywordFile, "、");

		for (String s : kws)
		{
			log.info(s);
			Keyword kw = new Keyword();
			kw.setKeyword(s);
			kw.setCampaignId(campaignId);
			kw.persist();
		}

	}
}
