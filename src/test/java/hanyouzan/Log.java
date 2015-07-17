package hanyouzan;

import lombok.Data;

@Data
public class Log
{
	private String ip;
	private String cookie;
	private String time;
	private String url;
	private String referer;
}
