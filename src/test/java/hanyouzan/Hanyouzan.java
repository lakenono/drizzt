package hanyouzan;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;

public class Hanyouzan
{
	public static void main(String[] args) throws IOException
	{
		new Hanyouzan().run();
	}

	private void run() throws IOException
	{
		File file = new File("/temp/hanyouzan.csv");

		LineIterator lineIterator = FileUtils.lineIterator(file);

		while (lineIterator.hasNext())
		{
			String line = lineIterator.nextLine();

			String[] s = StringUtils.split(line, ",");

			Log log = new Log();
			log.setIp(s[0]);
			log.setCookie(s[1]);
			log.setTime(s[2]);
			log.setUrl(s[3]);
			log.setReferer(s[4]);

			String url = URLDecoder.decode(s[3], "UTF-8");
			System.out.println(url);

			//System.out.println(line);
		}
	}
}
