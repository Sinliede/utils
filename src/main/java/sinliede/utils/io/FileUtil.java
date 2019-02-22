package sinliede.utils.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author sinliede
 * @version 0.1.0
 * @date 18-6-4 下午8:38
 */
public class FileUtil {

    public static void writeToFile(String result, String destPath) {
       File file = new File(destPath);
        writeToFile(result, file);
    }

    /**
     * 写文件
     * @param result
     * @param file
     */
    public static void writeToFile(String result, File file) {
        BufferedWriter bw = null;
        try {
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    System.out.println("创建父文件夹失败");
                }
            }
            if (!file.exists())
                file.createNewFile();
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(result);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
