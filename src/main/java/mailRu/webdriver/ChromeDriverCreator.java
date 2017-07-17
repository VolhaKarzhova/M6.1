package mailRu.webdriver;

import mailRu.config.GlobalOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

public class ChromeDriverCreator extends WebDriverCreator {

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities) {
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(
				new File(GlobalOptions.instance().getChromeDriverPath())).build();
		try {
			service.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = new ChromeDriver(service,capabilities);
		return driver;
	}
}