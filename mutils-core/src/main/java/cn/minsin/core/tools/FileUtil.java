package cn.minsin.core.tools;

import cn.minsin.core.exception.MutilsErrorException;

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
        return file.mkdirs();
    }

    public static boolean createFile(File file) throws IOException {
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
                createFile(file);
            }
        }
    }

    /**
     * 复制文件夹
     *
     * @param source 源目录
     * @param target 目标目录
     * @param flag   是否覆盖原文件夹 true代表把a文件夹整个复制过去，false只复制子文件夹及文件。
     * @throws MutilsErrorException
     */
    public static boolean copy(File source, File target, boolean flag) {
        try {
            // 判断是否存在
            if (source.exists()) {
                // 判断是否是目录
                if (source.isDirectory()) {
                    if (flag) {
                        // 制定路径，以便原样输出
                        target = new File(target + "/" + source.getName());
                        // 判断文件夹是否存在，不存在就创建
                        if (!target.exists()) {
                            target.mkdirs();
                        }
                    }
                    flag = true;
                    // 获取文件夹下所有的文件及子文件夹
                    File[] l = source.listFiles();
                    // 判断是否为null
                    if (null != l) {
                        for (File ll : l) {
                            // 循环递归调用
                            copy(ll, target, flag);
                        }
                    }
                } else {
                    // 获取输入流
                    FileInputStream fis = new FileInputStream(source);
                    // 获取输出流
                    FileOutputStream fos = new FileOutputStream(target + "/" + source.getName());
                    int i;
                    byte[] b = new byte[1024];
                    // 读取文件
                    while ((i = fis.read(b)) != -1) {
                        // 写入文件，复制
                        fos.write(b, 0, i);
                    }
                    IOUtil.close(fos, fis);
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
            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
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

    public static void main(String[] args) {
        File file = new File("G://test11111111");

        File aaaa = new File(file, "aaaa.txt");
        boolean directory = aaaa.isDirectory();
        boolean file1 = aaaa.isFile();

        System.out.println();
    }
}
