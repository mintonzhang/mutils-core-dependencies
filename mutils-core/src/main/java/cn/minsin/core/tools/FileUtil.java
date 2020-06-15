package cn.minsin.core.tools;

import cn.minsin.core.tools.function.FunctionalInterfaceUtil;
import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作工具类
 *
 * @author mintonzhang
 * @date 2019年2月14日
 * @since 0.1.0
 */
public class FileUtil {
    protected FileUtil() {
        // allow Subclass
    }

    /**
     * 检查文件路径 如果不存在就创建
     *
     * @param path
     */
    public static void checkPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean isFile(File file) {
        return file != null && file.isFile();
    }

    public static boolean isDictionary(File file) {
        return file != null && file.isDirectory();
    }

    public static boolean isNotFile(File file) {
        return !isFile(file);
    }

    public static boolean isNotDictionary(File file) {
        return !isDictionary(file);
    }

    public static boolean createDictionary(File file) {
        boolean exists = file.exists();
        if (!exists) {
            return file.mkdirs();
        }
        return true;
    }

    public static boolean createFile(File file, boolean isOverride) throws IOException {
        boolean exists = file.exists();
        if (exists && isOverride) {
            file.delete();
        }
        return file.createNewFile();
    }

    /**
     * 创建文件或文件夹
     *
     * @param file
     * @throws IOException
     */
    public static void createFileOrDictionary(File file) throws IOException {
        boolean exists = file.exists();
        if (!exists) {
            boolean directory = file.isDirectory();
            if (directory) {
                createDictionary(file);
            } else {
                createFile(file, false);
            }
        }
    }

    /**
     * 复制文件夹
     *
     * @param source 源目录
     * @param target 目标目录
     */
    public static boolean copy(File source, File target, boolean isOverride) {
        try {
            if (!source.exists() || !target.exists()) {
                return false;
            }
            File[] fileArray = source.listFiles();
            if (fileArray != null && fileArray.length > 0) {
                for (File src1 : fileArray) {
                    File des1 = new File(target, src1.getName());
                    if (src1.isDirectory()) {
                        des1.mkdir();
                        copy(src1, des1, isOverride);
                    } else {
                        createFile(des1, isOverride);
                        transferFile(src1, des1, false);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 删除整个文件夹 包含文件
     *
     * @param dirFile
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {
            File[] files = dirFile.listFiles();
            FunctionalInterfaceUtil.ifNotNullExecute(files, e -> {
                for (File file : e) {
                    deleteFile(file);
                }
            });
        }
        return dirFile.delete();
    }

    /**
     * 删除整个文件夹 包含文件
     *
     * @param path
     */
    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }


    /**
     * 保存文件流到临时文件
     */
    public static File saveTempFileWithNIO(InputStream inputStream, String suffix) throws IOException {
        File tempFile = File.createTempFile("temp" + System.currentTimeMillis(), suffix);
        IOUtil.inputStream2OutputStreamWithNIO(inputStream, new FileOutputStream(tempFile));
        return tempFile;
    }

    /**
     * 保存文件流到临时文件
     */
    public static File saveTempFileWithIO(InputStream inputStream, String suffix) throws IOException {
        File tempFile = File.createTempFile("temp" + System.currentTimeMillis(), suffix);
        IOUtil.inputStream2OutputStreamWithIO(inputStream, new FileOutputStream(tempFile), true);
        return tempFile;
    }

    /**
     * 保存文件到临时文件
     */
    public static File saveTempFileWithNIO(File file, String suffix) throws IOException {
        File tempFile = File.createTempFile("temp" + System.currentTimeMillis(), suffix);
        IOUtil.inputStream2OutputStreamWithNIO(new FileInputStream(file), new FileOutputStream(tempFile));
        return tempFile;
    }

    /**
     * 保存文件到临时文件
     */
    public static File saveTempFileWithIO(File file, String suffix) throws IOException {
        File tempFile = File.createTempFile("temp" + System.currentTimeMillis(), suffix);
        IOUtil.inputStream2OutputStreamWithIO(new FileInputStream(file), new FileOutputStream(tempFile), true);
        return tempFile;
    }

    /**
     * source转换到target
     *
     * @param source
     * @param target
     */
    public static void transferFile(File source, File target, boolean deleteSource) {

        if (source.isFile() && target.isFile()) {
            try {
                @Cleanup
                FileInputStream fis = new FileInputStream(source);
                @Cleanup
                FileOutputStream fos = new FileOutputStream(target);
                byte[] bys = new byte[1024];
                int len;
                while ((len = fis.read(bys)) != -1) {
                    fos.write(bys, 0, len);
                }
                if (deleteSource) {
                    source.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
