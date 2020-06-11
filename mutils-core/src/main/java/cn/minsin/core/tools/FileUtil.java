package cn.minsin.core.tools;

import cn.minsin.core.exception.MutilsErrorException;
import cn.minsin.core.override.JsonString;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

    /**
     * 复制文件夹
     *
     * @param f    源目录
     * @param nf   目标目录
     * @param flag 是否覆盖原文件夹 true代表把a文件夹整个复制过去，false只复制子文件夹及文件。
     * @throws MutilsErrorException
     */
    public static void copy(File f, File nf, boolean flag) throws MutilsErrorException {
        try {
            // 判断是否存在
            if (f.exists()) {
                // 判断是否是目录
                if (f.isDirectory()) {
                    if (flag) {
                        // 制定路径，以便原样输出
                        nf = new File(nf + "/" + f.getName());
                        // 判断文件夹是否存在，不存在就创建
                        if (!nf.exists()) {
                            nf.mkdirs();
                        }
                    }
                    flag = true;
                    // 获取文件夹下所有的文件及子文件夹
                    File[] l = f.listFiles();
                    // 判断是否为null
                    if (null != l) {
                        for (File ll : l) {
                            // 循环递归调用
                            copy(ll, nf, flag);
                        }
                    }
                } else {
                    // 获取输入流
                    FileInputStream fis = new FileInputStream(f);
                    // 获取输出流
                    FileOutputStream fos = new FileOutputStream(nf + "/" + f.getName());

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
        } catch (Exception e) {
            throw new MutilsErrorException(e, "文件复制失败");
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
     * 获取图片文件流的宽高 如果没有获取到 将只会返回原文件流
     *
     * @param in 文件输入流
     * @throws MutilsErrorException
     */
    public static FileModel readHeightAndWidth(InputStream in) throws MutilsErrorException {
        try {
            byte[] copyInputStream = IOUtil.copyInputStream(in);

            FileModel fileModel = new FileModel();
            fileModel.setInputStream(new ByteArrayInputStream(copyInputStream));
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(copyInputStream));
            if (bi != null) {
                fileModel.setHeight(bi.getHeight());
                fileModel.setWidth(bi.getWidth());
                fileModel.setImage(true);
            }
            return fileModel;
        } catch (Exception e) {
            throw new MutilsErrorException(e, "Maybe,the inputstream is not an image or null.");
        }

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


    @Getter
    @Setter
    public static class FileModel implements JsonString {

        /**
         *
         */
        private static final long serialVersionUID = 719250036645822007L;

        // 	宽 如果是图片才会返回
        private int width;
        // 	高 如果是图片才会返回
        private int height;
        //	文件输入流
        private InputStream inputStream;
        //	是否为图片
        private boolean isImage = false;

    }
}
