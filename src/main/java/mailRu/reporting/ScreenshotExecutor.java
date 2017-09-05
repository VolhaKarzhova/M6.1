package mailRu.reporting;


import mailRu.webdriver.WebDriverSingleton;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;

public class ScreenshotExecutor extends TestListenerAdapter {

    private static final String PNG = ".png";
    private int pngCount = 0;

    @Override
    public void onTestFailure(ITestResult testResult) {
        Logger.error(testResult.getName() + " screenshot saved: " + takeScreenshot());
    }

    private String takeScreenshot() {
        String path;
        try {
            File source = ((TakesScreenshot) WebDriverSingleton.getWebDriverInstance()).getScreenshotAs(OutputType.FILE);
            path = "./target/screenshots/" + pngCount + PNG;
            pngCount++;
            FileUtils.copyFile(source, new File(path));
        } catch (IOException e) {
            Logger.error("failed to capture screenshot");
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        return path;
    }
}