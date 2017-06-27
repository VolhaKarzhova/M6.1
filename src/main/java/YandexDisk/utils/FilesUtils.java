package YandexDisk.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesUtils {

    public File dir;

    public List<File> createFiles(int fileQuantity) throws IOException {
        List<File> fileList = new ArrayList<File>();
        dir = new File("/" + RandomUtils.getFileDirectory());
        dir.mkdir();
        for (int x = 0; x < fileQuantity; x++) {
            File file = new File(dir, RandomUtils.getFileName() + ".txt");
            org.apache.commons.io.FileUtils.writeStringToFile(file, RandomUtils.getFileContent(), "ISO-8859-1");
            fileList.add(file);
        }
        return fileList;
    }

    public String getFileName(List<File> fileList, int fileNumber) {
        return fileList.get(fileNumber).getName();
    }

    public List<File> getFileListForOperations(List<File> fileList, int filesQuantity) {
        List<File> newFileList = new ArrayList<File>();
        for (int i = 0; i < filesQuantity; i++) {
            newFileList.add(fileList.get(i));
        }
        return newFileList;
    }
}