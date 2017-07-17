package yandexDisk.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesUtils {

    public static File folder;

    public List<File> createFiles(int fileQuantity) throws IOException {
        List<File> fileList = new ArrayList<File>();
        folder = new File(FileUtils.getTempDirectory() + "/" + RandomUtils.getFileDirectory());
        folder.mkdir();
        for (int x = 0; x < fileQuantity; x++) {
            File file = new File(folder, RandomUtils.getFileName() + ".txt");
            FileUtils.writeStringToFile(file, RandomUtils.getFileContent(), "ISO-8859-1");
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

    public void deleteTempFolder(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory() && file.listFiles() != null) {
            for (File files : file.listFiles()) {
                deleteTempFolder(files);
            }
        }
        file.delete();
    }
}