package YandexDisk.config;

import YandexDisk.utils.RandomUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFile {

    public List<File> createFiles(int fileQuantity) throws IOException {
        List<File> fileList = new ArrayList<File>();
        File dir = new File("/" + RandomUtils.getFileDirectory());
        dir.mkdir();
        for (int x = 0; x < fileQuantity; x++) {
            File file = new File(dir, RandomUtils.getFileName() + ".txt");
            FileUtils.writeStringToFile(file, RandomUtils.getFileContent(), "ISO-8859-1");
            fileList.add(file);
        }
        return fileList;
    }
}