package jar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class RunJar {
    public static void main(String[] args) {
        System.out.println("首次运行");
        try {
            FileUtils.touch(new File("D:/a.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
