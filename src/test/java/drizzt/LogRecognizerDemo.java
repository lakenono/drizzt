package drizzt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import drizzt.recognize.AccountRecognizer;
import drizzt.recognize.AppRecognizer;
import drizzt.recognize.TerminalRecognizer;
import drizzt.recognize.URLFeatureRecognizer;
import drizzt.recognize.domain.MaienLog;
import drizzt.rule.account.AccountBean;
import drizzt.rule.app.AppBean;
import drizzt.rule.terminal.TerminalBean;
import drizzt.rule.url.URLBean;

@Slf4j
public class LogRecognizerDemo {
	private File logFile;

	private AppRecognizer appRecognizer;
	private TerminalRecognizer terminalRecognizer;
	private AccountRecognizer accountRecognizer;
	private URLFeatureRecognizer goodsRecognizer;

	private int totalCount;
	private int appCount;
	private int terminalCount;
	private int accountCount;
	private int goodsCount;

	public LogRecognizerDemo(String path) throws Exception {
		logFile = new File(path);

		if (!logFile.exists()) {
			log.error("Can not find file : {}", logFile.getAbsolutePath());
			return;
		}

		log.info("Get file : {} ", logFile.getAbsolutePath());

		appRecognizer = new AppRecognizer();
		terminalRecognizer = new TerminalRecognizer();
		accountRecognizer = new AccountRecognizer();
		goodsRecognizer = new URLFeatureRecognizer();
	}

	public void run() throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, SQLException {
		LineIterator lineIterator = FileUtils.lineIterator(logFile);

		while (lineIterator.hasNext()) {
			String line = lineIterator.nextLine();

			MaienLog maienLog = MaienLog.convertLine(line);
			if (maienLog == null) {
				continue;
			}

			// 应用识别
			AppBean app = appRecognizer.recognize(maienLog);
			if (app != null) {
				app.setAdid(maienLog.getUid());
				boolean persist = app.persistOnNotExist();
				if (persist) {
					appCount++;
				}
			}

			// 终端识别
			List<TerminalBean> terminals = terminalRecognizer.recognize(maienLog);
			if (terminals != null && !terminals.isEmpty()) {
				for (TerminalBean t : terminals) {
					t.setAdid(maienLog.getUid());
					boolean persist = t.persistOnNotExist();
					if (persist) {
						terminalCount++;
					}
				}
			}

			// 用户识别
			List<AccountBean> accounts = accountRecognizer.recognize(maienLog);
			if (accounts != null && !accounts.isEmpty()) {
				for (AccountBean a : accounts) {
					a.setAdid(maienLog.getUid());
					boolean persist = a.persistOnNotExist();
					if (persist) {
						accountCount++;
					}
				}
			}

			// 商品识别
			URLBean goods = goodsRecognizer.recognize(maienLog);
			if (goods != null) {
				goods.setAdid(maienLog.getUid());
				boolean persist = goods.persistOnNotExist();
				if (persist) {
					goodsCount++;
				}
			}

			totalCount++;
		}

		log.info("total count : {}", totalCount);
		log.info("app recognized records count : {}", appCount);
		log.info("terminal recognized records count : {}", terminalCount);
		log.info("account recognized records count : {}", accountCount);
		log.info("goods recognized records count : {}", goodsCount);

		log.info("success ! ");
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			log.info("Usage : drizzt.LogRecognizerDemo filePath");
		}

		new LogRecognizerDemo(args[0]).run();
	}

}
