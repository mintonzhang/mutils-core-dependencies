package cn.minsin.core.tools;

import com.alibaba.fastjson2.util.IOUtils;
import lombok.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 * 文件流相关工具类  可参考{@link IOUtils}
 *
 * @author mintonzhang
 * @date 2019年1月15日
 */
public class IOUtil extends IOUtils {

    /**
     * 关闭流
     */
    public static void close(Closeable... ios) {
        if (ios != null) {
            for (Closeable x : ios) {
                IOUtils.close(x);
            }
        }
    }

    /**
     * 关闭流
     */
    public static void close(AutoCloseable... ios) {
        if (ios != null) {
            for (AutoCloseable x : ios) {
                if (x != null) {
                    try {
                        x.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    public static void close(final URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    /**
     * 将文件流转成字节缓存在内存中，可以让流多次使用。使用{@link ByteArrayInputStream} 创建新的输入流
     */
    public static byte[] readAllBytes(InputStream in) throws IOException {
        try (InputStream inputStream = in) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        }
    }

    /**
     * 将文件流转成字节缓存在内存中，可以让流多次使用。使用{@link ByteArrayInputStream} 创建新的输入流
     */
    public static String readAllBytes2String(InputStream in) throws IOException {
        byte[] bytes = IOUtil.readAllBytes(in);

        try {
            return new String(bytes, StandardCharsets.UTF_8);
        } finally {
            close(in);
        }

    }

    /**
     * 使用NIO将 inputStream转换成为OutputStream
     */
    public static OutputStream inputStream2OutputStreamWithNIO(final InputStream src) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        final ReadableByteChannel inputChannel = Channels.newChannel(src);
        final WritableByteChannel outputChannel = Channels.newChannel(byteOutputStream);
        NIOCopy(inputChannel, outputChannel);
        return byteOutputStream;
    }


    public static void inputStream2OutputStreamWithNIO(final InputStream src, final OutputStream dest) throws IOException {
        final ReadableByteChannel inputChannel = Channels.newChannel(src);
        final WritableByteChannel outputChannel = Channels.newChannel(dest);
        NIOCopy(inputChannel, outputChannel);
        inputChannel.close();
        outputChannel.close();
    }

    public static void NIOCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }


    /**
     * 使用NIO将 inputStream转换成为OutputStream
     */
    public static OutputStream inputStream2OutputStreamWithIO(final InputStream src) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        inputStream2OutputStreamWithIO(src, byteArrayOutputStream, false);
        return byteArrayOutputStream;
    }


    /**
     * inputStream 写入outputStream
     */
    public static void inputStream2OutputStreamWithIO(@NonNull final InputStream src, @NonNull final OutputStream dest, boolean closeOutputStream) throws IOException {
        try {
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 开始读取
            while ((len = src.read(bs)) != -1) {
                dest.write(bs, 0, len);
            }
        } finally {
            close(src);
            dest.flush();
            if (closeOutputStream) {
                close(dest);
            }
        }
    }
}
