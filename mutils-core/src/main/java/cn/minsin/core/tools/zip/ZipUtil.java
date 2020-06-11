package cn.minsin.core.tools.zip;

import cn.minsin.core.tools.FileUtil;
import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩Util
 *
 * @author: minton.zhang
 * @since: 2020/5/29 11:14
 */
public class ZipUtil {


    //********************************压缩文件****************************************//


    /**
     * 压缩一个或多个文件
     */
    private static void zipFiles(Collection<File> srcFiles, ZipOutputStream zipOut, int bufferSize) throws IOException {

        for (File fileToZip : srcFiles) {
            @Cleanup
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[bufferSize];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
        zipOut.close();
    }


    /**
     * 压缩文件夹
     */
    public static void zipDictionary(File fileDir, String fileName, ZipOutputStream zipOut, int bufferSize) throws IOException {
        if (fileDir.isHidden()) {
            return;
        }
        if (fileDir.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileDir.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    zipDictionary(childFile, fileName + "/" + childFile.getName(), zipOut, bufferSize);
                }
            }
            return;
        }
        @Cleanup
        FileInputStream fis = new FileInputStream(fileDir);
        ZipEntry zipEntry = new ZipEntry(fileName == null ? fileDir.getName() : fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[bufferSize];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
    }


    //********************************解压zip****************************************//


    public static void unZip(File zipFile, File saveDir, boolean ifErrorContinue, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        @Cleanup
        FileInputStream fileInputStream = new FileInputStream(zipFile);
        @Cleanup
        ZipInputStream zis = new ZipInputStream(fileInputStream);

        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            try {
                String path = saveDir.getAbsolutePath().concat(zipFile.getName().split("\\.")[0]);
                FileUtil.checkPath(path);
                @Cleanup
                FileOutputStream fos = new FileOutputStream(path.concat("/").concat(zipEntry.getName()));
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                zipEntry = zis.getNextEntry();
            } catch (Exception e) {
                e.printStackTrace();
                if (!ifErrorContinue) {
                    throw e;
                }
            }
        }
        zis.closeEntry();
    }

}
