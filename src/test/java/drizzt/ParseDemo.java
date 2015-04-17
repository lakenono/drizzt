package drizzt;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;

import drizzt.domain.BroadbandLog;

public class ParseDemo
{
	private File file;

	public ParseDemo(String file)
	{
		this.file = new File(file);
	}

	private void run() throws IOException
	{
		LineIterator lineIterator = FileUtils.lineIterator(this.file);

		while (lineIterator.hasNext())
		{
			String line = lineIterator.nextLine();
			this.convertLine(line);
		}
	}

	private void convertLine(String line)
	{
		String[] s = StringUtils.split(line, "\t");

		BroadbandLog bean = new BroadbandLog();
		bean.setTs(s[0]);
		bean.setSrcip(s[1]);
		bean.setAdid(s[2]);
		bean.setUrl(s[3]);
		bean.setRef(s[4]);
		bean.setUa(s[5]);
		bean.setDstip(s[6]);
		bean.setCookie(s[7]);

		System.out.println(Arrays.toString(s));
		System.out.println(bean.toString());
	}

	public static void main(String[] args) throws IOException
	{
		new ParseDemo("/Volumes/lake/9-临时数据/20150417.txt_00").run();
	}
}
