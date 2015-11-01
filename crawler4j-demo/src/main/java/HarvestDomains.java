import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class HarvestDomains extends WebCrawler {

	private static CrawlConfig config = new CrawlConfig();
	private static Set<String> domains = new HashSet<String>();

	private static final Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private static final Logger logger = LogManager.getLogger(HarvestDomains.class);

	private static final String regex = "[0-9a-z-]+\\.(com\\.cn|net\\.cn|org\\.cn|gov\\.cn|edu\\.cn|com|net|org|mobi|wang|biz|top|cn|ren|club|xyz|link|life|cool|email|city|click|pub|pw|me|name|info|co|xin|cc|asia|tv|la|so|hk|site|space|la|today)\\b";

	private static Pattern pattern = Pattern.compile(regex);

	private static Set<String> target = new HashSet<String>();

	@Override
	public boolean shouldVisit(Page page,WebURL url) {
		String href = url.getURL().toLowerCase();
		if (filters.matcher(href).matches()) {
			logger.info("debug.com ....false..." + url);
			return false;
		}
		for (String one : target) {
			//取域名判断
			String[] ss = one.split("\\.");
			String domain = ss[ss.length-2]+"."+ss[ss.length-1];
			if (href.contains(domain)) {
				logger.info("debug.com ...true..." + url);
				return true;
			}
		}
		logger.info("debug.com ...false..." + url);
		return false;
	}

	@Override
	public void visit(Page page) {
		logger.debug("visit url:"+page.getWebURL().getURL());
		String html = new String(page.getContentData()).toLowerCase();
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String domain = matcher.group().toLowerCase();
			if (domain.startsWith("-")) {
				continue;
			}
			// synchronized (domains) {
			// if (!domains.contains(domain)) {
			domains.add(domain);
			logger.info(domain);
			// }
			// }
		}
	}

	public static void go(int thread) {
		String crawlStorageFolder = "./dat";
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setUserAgentString("Baiduspider+(+http://www.baidu.com/search/spider.htm)");
		//config.setResumableCrawling(true);
		// 不发送超过3每秒请求数
		config.setPolitenessDelay(3000);
		// URL深度
		config.setMaxDepthOfCrawling(5);
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		robotstxtConfig.setEnabled(false);
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		try {
			CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
			for (String one : target) {
				controller.addSeed(one);
			}
			controller.startNonBlocking(HarvestDomains.class, thread);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String s = FileUtil.file2String("./cfg/list.txt");
		String[] list = s.split("\n");
		for (String line : list) {
			target.add(line);
		}
		go(3);
	}

}
