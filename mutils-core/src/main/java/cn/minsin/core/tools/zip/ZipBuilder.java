package cn.minsin.core.tools.zip;

import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author: minton.zhang
 * @since: 2020/5/29 11:27
 */
@Getter
@Setter
public class ZipBuilder {

    public static ZipBuilder of(@NonNull File... files) {
        ZipBuilder zipBuilder = new ZipBuilder();
        for (File file : files) {
            zipBuilder.addFiles(file);
        }
        return zipBuilder;
    }

    public static ZipBuilder of(@NonNull List<File> files) {
        ZipBuilder zipBuilder = new ZipBuilder();
        for (File file : files) {
            zipBuilder.addFiles(file);
        }
        return zipBuilder;
    }

    private List<File> files;

    /**
     * 缓冲区大小 单位byte
     */
    private int bufferSize = 1024;


    /**
     * 添加文件
     *
     * @param file
     * @return
     */
    public ZipBuilder addFiles(File file) {
        if (this.files == null) {
            this.files = Lists.newArrayList(file);
        } else {
            this.files.add(file);
        }
        return this;
    }

    /**
     * 压缩文件
     *
     * @param outputStream 输出文件流
     * @throws IOException
     */
    public void zip(OutputStream outputStream) throws IOException {
        @Cleanup OutputStream copyOutputStream = outputStream;
        @Cleanup ZipOutputStream zipOutputStream = new ZipOutputStream(copyOutputStream);
        for (File file : files) {
            ZipUtil.zipDictionary(file, null, zipOutputStream, this.bufferSize);
        }
    }

    /**
     * 压缩文件
     *
     * @param saveFile 输出文件夹
     * @throws IOException
     */
    public void zip(File saveFile) throws IOException {
        @Cleanup
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        @Cleanup
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        for (File file : files) {
            ZipUtil.zipDictionary(file, null, zipOutputStream, this.bufferSize);
        }
    }

    /**
     * 解压文件
     *
     * @param saveDictionary
     * @param ifErrorContinue
     * @throws IOException
     */
    public void unZip(File saveDictionary, boolean ifErrorContinue) throws IOException {
        for (File file : files) {
            ZipUtil.unZip(file, saveDictionary, ifErrorContinue, this.bufferSize);
        }
    }
}
