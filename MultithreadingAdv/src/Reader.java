import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    public static void fileReader() throws IOException {

        try {
            File fileFolder = new File("D:\\JavaHomework\\MultithreadingAdv\\src\\resources");
            File[] files = fileFolder.listFiles();

            assert files != null;
            for (File file : files) {
                BufferedReader bufferd = new BufferedReader(new FileReader(file));
                String line;

                while ((line = bufferd.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
