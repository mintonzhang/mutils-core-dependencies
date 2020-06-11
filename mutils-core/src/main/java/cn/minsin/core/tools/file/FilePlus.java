package cn.minsin.core.tools.file;

import cn.minsin.core.tools.FileUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/6/11 16:57
 */
@Slf4j
public class FilePlus {

    private List<File> files = new ArrayList<>(5);

    private static void copyFile(List<File> files, File dictionary) {

        for (File file : files) {
            File child = new File(dictionary, file.getName());
        }

    }

    public FilePlus add(File file) {
        files.add(file);
        return this;
    }

    public FilePlus add(File... file) {
        files.addAll(Arrays.asList(file));
        return this;
    }

    public FilePlus add(Collection<File> file) {
        files.addAll(file);
        return this;
    }

    public FilePlus remove(Collection<File> file) {
        files.retainAll(file);
        return this;
    }

    public FilePlus remove(File file) {
        files.remove(file);
        return this;
    }

    public FilePlus create() {
        for (File file : files) {
            try {
                FileUtil.createFile(file);
            } catch (Exception e) {
                log.error("The File '{}' create failed.", file.getName());
            }
        }
        return this;
    }

    public FilePlus copyToDictionary(@NonNull File dictionary) {
        FileUtil.createDictionary(dictionary);

        for (File file : files) {
            final File file1 = new File(dictionary, file.getName());
        }
        return this;
    }
}

