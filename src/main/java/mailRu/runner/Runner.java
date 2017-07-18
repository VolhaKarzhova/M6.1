package mailRu.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import mailRu.config.GlobalOptions;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        parseCLiOptions(args);
        configureSuites().run();
    }

    private static TestNG configureSuites() {
        TestNG testNG = new TestNG(false);
        XmlSuite suite = new XmlSuite();
        suite.setName("mailRu");
        List<String> files = new ArrayList<String>();
        files.add(GlobalOptions.instance().getSuitePath());
        suite.setSuiteFiles(files);
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        testNG.setXmlSuites(suites);
        return testNG;
    }

    private static void parseCLiOptions(String[] args) {
        JCommander jCommander = new JCommander(GlobalOptions.instance());
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            System.exit(1);
        }
    }
}