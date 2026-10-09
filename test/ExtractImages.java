package test;

import java.io.*;
import java.util.zip.*;

public class ExtractImages {
    public static void main(String[] args) throws Exception {
        ZipFile zip = new ZipFile("Report.docx");
        File outDir = new File("C:/Users/daoh9/.gemini/antigravity/brain/c51898f5-4909-4f67-a688-fa2634bd2cec/scratch/diagrams");
        outDir.mkdirs();

        for (int i = 1; i <= 7; i++) {
            String name = "word/media/image" + i + (i == 1 ? ".jpeg" : ".png");
            ZipEntry e = zip.getEntry(name);
            if (e != null) {
                InputStream is = zip.getInputStream(e);
                File outFile = new File(outDir, "image" + i + (i == 1 ? ".jpeg" : ".png"));
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buf = new byte[8192];
                int len;
                while ((len = is.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fos.close();
                is.close();
                System.out.println("Extracted: " + outFile.getAbsolutePath());
            }
        }
        zip.close();
    }
}
