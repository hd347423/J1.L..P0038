package test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.*;

public class FullAudit {
    public static void main(String[] args) throws Exception {
        ZipFile zip = new ZipFile("Report.docx");
        ZipEntry entry = zip.getEntry("word/document.xml");
        InputStream is = zip.getInputStream(entry);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        zip.close();

        String xml = sb.toString();
        // Remove XML tags to get clean plain text
        String cleanText = xml.replaceAll("<w:p[^>]*>", "\n").replaceAll("<[^>]+>", " ").replaceAll("[ ]+", " ");

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("report_audit_text.txt"), StandardCharsets.UTF_8));
        writer.write(cleanText);
        writer.close();
        System.out.println("Clean text written to report_audit_text.txt");
    }
}
