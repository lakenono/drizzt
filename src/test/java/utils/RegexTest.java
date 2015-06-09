package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class RegexTest {

	@Test
	public void testPattern() throws Exception {
		String url = "http://item.yhd.com/item/2800662";
		String pattern = "/(\\d{1,})";

		test(url, pattern, "2800662");

		url = "http://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.KfAF7z&id=44839077275&skuId=4611686063266465179&areaId=110100&is_b=1&user_id=1089381230&cat_id=55070022&q=%B3%C8%D7%D3&rn=354bbf93a528331a227619ae1c048087";
		pattern = "id=([^=]+)(\\&|$)";

		test(url, pattern, "44839077275");
	}


	private void test(String url, String pattern, String expectResult) {
		Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(url);

		if (matcher.find()) {
			String actualResult = matcher.group(1);
			System.out.println(actualResult + " | " + url);
			Assert.assertEquals("Match fail ! ", expectResult, actualResult);
		}
	}
}
