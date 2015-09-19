package drizzt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import lakenono.db.DBBean;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import drizzt.recognize.AccountRecognizer;
import drizzt.recognize.AppRecognizer;
import drizzt.recognize.TerminalRecognizer;
import drizzt.recognize.URLFeatureRecognizer;
import drizzt.recognize.domain.LogRecord;
import drizzt.recognize.domain.builder.MaienLogBuilder;
import drizzt.rule.account.AccountBean;
import drizzt.rule.app.AppBean;
import drizzt.rule.terminal.TerminalBean;
import drizzt.rule.url.URLBean;

@Slf4j
public class MaienLogRecognizerDemo {
	private static String tableKey = "maien";

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

	private void createTable() throws Exception {
		DBBean.createTable(AppBean.class, tableKey);
		DBBean.createTable(TerminalBean.class, tableKey);
		DBBean.createTable(AccountBean.class, tableKey);
		DBBean.createTable(AppBean.class, tableKey);
	}

	public MaienLogRecognizerDemo(String path) throws Exception {
		// 创建测试数据库表
		createTable();

		// 开始单进程处理
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

			LogRecord maienLog = MaienLogBuilder.convertLine(line);
			if (maienLog == null) {
				continue;
			}

			// 应用识别
			AppBean app = appRecognizer.recognize(maienLog);
			if (app != null) {
				app.setAdid(maienLog.getAdid());
				boolean persist = app.saveOnNotExist();
				if (persist) {
					appCount++;
				}
			}

			// 终端识别
			List<TerminalBean> terminals = terminalRecognizer.recognize(maienLog);
			if (terminals != null && !terminals.isEmpty()) {
				for (TerminalBean t : terminals) {
					boolean persist = t.saveOnNotExist();
					if (persist) {
						terminalCount++;
					}
				}
			}

			// 用户识别
			List<AccountBean> accounts = accountRecognizer.recognize(maienLog);
			if (accounts != null && !accounts.isEmpty()) {
				for (AccountBean a : accounts) {
					boolean persist = a.saveOnNotExist();
					if (persist) {
						accountCount++;
					}
				}
			}

			// 商品识别
			URLBean goods = goodsRecognizer.recognize(maienLog);
			if (goods != null) {
				boolean persist = goods.saveOnNotExist();
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
			return;
		}

		new MaienLogRecognizerDemo(args[0]).run();
	}

}
