package cn.minsin.core.tools;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

/**
 * @author: minton.zhang
 * @since: 2020/11/28 下午2:17
 */
@Slf4j
public class SystemUtil {

	private static final Map<String, String> env = System.getenv();
	private static final Properties properties = System.getProperties();

	/**
	 * 获取计算机名称
	 */
	public static String getComputerName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			return "Unknown Name";
		}
	}

	/**
	 * 获得内网IP
	 */
	public static String getIntranetIp() {
		return getIp(ip -> ip.isSiteLocalAddress()
				&& !ip.isLoopbackAddress()
				&& !ip.getHostAddress().contains(":"));
	}


	/**
	 * 获取外网ip
	 */
	public static String getPublicIp() {
		return getIp(ip -> !ip.isSiteLocalAddress()
				&& !ip.isLoopbackAddress()
				&& !ip.getHostAddress().contains(":"));
	}


	public static String getIp(Predicate<InetAddress> predicate) {
		String ip = "";
		try {
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				final NetworkInterface networkInterface = networkInterfaces.nextElement();
				final List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
				for (InterfaceAddress interfaceAddress : interfaceAddresses) {
					final InetAddress address = interfaceAddress.getAddress();
					final boolean test = predicate.test(address);
					if (test) {
						ip = address.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			//
		}
		final boolean localhost = "localhost".equalsIgnoreCase(ip) || "127.0.0.1".equals(ip) || "".equals(ip);
		return localhost ? "Unknown IP" : ip;

	}


	public static String getProperties(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static String getEnvProperties(String key, String defaultValue) {
		return env.getOrDefault(key, defaultValue);
	}

	public static String getProperties(DefaultPropertiesKey key, String defaultValue) {
		return properties.getProperty(key.getKey(), defaultValue);
	}

	public static String getEnvProperties(DefaultEnvKey key, String defaultValue) {
		return env.getOrDefault(key.name(), defaultValue);
	}

	/**
	 * 默认环境变量key
	 */
	public enum DefaultEnvKey {
		/**
		 * shell类型
		 **/
		SHELL,

		/**
		 * //当前用户
		 **/
		USER,

		/**
		 * 用户目录
		 **/
		HOME,

		/**
		 * //Java安装目录
		 **/
		JAVA_HOME,


		/**
		 * //path环境变量
		 **/
		PATH,

		/**
		 * //classpath环境变量
		 **/
		CLASSPATH,

		/**
		 * //处理器体系结构
		 **/
		PROCESSOR_ARCHITECTURE,

		/**
		 * //操作系统类型
		 **/
		OS,

		/**
		 * //处理级别
		 **/
		PROCESSOR_LEVEL,


		/**
		 * //用户名
		 **/
		USERNAME,

		/**
		 * //命令行解释器可执行程序的准确路径
		 **/
		ComSpec,

		/**
		 * //应用程序数据目录
		 **/
		APPDATA,

		/**
		 * maven目录
		 */
		MAVEN_HOME


	}


	/**
	 * 默认java参数key
	 */
	@Getter
	@RequiredArgsConstructor
	public enum DefaultPropertiesKey {
		/**
		 * 运行时环境版本
		 **/
		JAVA_VERSION("java.version"),

		/**
		 * 运行时环境供应商
		 **/
		JAVA_VENDOR("java.vendor"),

		/**
		 * Java供应商的 URL
		 **/
		JAVA_VENDOR_URL("java.vendor.url"),

		/**
		 *   Java安装目录
		 **/
		JAVA_HOME("java.home"),

		/**
		 * Java虚拟机规范版本
		 **/
		JAVA_VM_SPECIFICATION_VERSION("java.vm.specification.version"),

		/**
		 * Java虚拟机规范供应商
		 **/
		JAVA_VM_SPECIFICATION_VENDOR("java.vm.specification.vendor"),

		/**
		 *   Java虚拟机规范名称
		 **/
		JAVA_VM_SPECIFICATION_NAME("java.vm.specification.name"),

		/**
		 * Java虚拟机实现版本
		 **/
		JAVA_VM_VERSION("java.vm.version"),

		/**
		 * Java虚拟机实现供应商
		 **/
		JAVA_VM_VENDOR("java.vm.vendor"),

		/**
		 *   Java虚拟机实现名称
		 **/
		JAVA_VM_NAME("java.vm.name"),

		/**
		 * Java运行时环境规范版本
		 **/
		JAVA_SPECIFICATION_VERSION("java.specification.version"),

		/**
		 * Java运行时环境规范供应商
		 **/
		JAVA_SPECIFICATION_VENDOR("java.specification.vendor"),

		/**
		 * Java运行时环境规范名称
		 **/
		JAVA_SPECIFICATION_NAME("java.specification.name"),

		/**
		 * Java类格式版本号
		 **/
		JAVA_CLASS_VERSION("java.class.version"),

		/**
		 * Java类路径
		 **/
		JAVA_CLASS_PATH("java.class.path"),

		/**
		 * 加载库时搜索的路径列表
		 **/
		JAVA_LIBRARY_PATH("java.library.path"),

		/**
		 * 默认的临时文件路径
		 **/
		JAVA_IO_TMPDIR("java.io.tmpdir"),

		/**
		 * 要使用的 JIT编译器的名称
		 **/
		JAVA_COMPILER("java.compiler"),

		/**
		 * 一个或多个扩展目录的路径
		 **/
		JAVA_EXT_DIRS("java.ext.dirs"),

		/**
		 * 操作系统的名称
		 **/
		OS_NAME("os.name"),

		/**
		 * 操作系统的架构
		 **/
		OS_ARCH("os.arch"),

		/**
		 * 操作系统的版本
		 **/
		OS_VERSION("os.version"),

		/**
		 * 文件分隔符
		 **/
		FILE_SEPARATOR("file.separator");

		private final String key;
	}

}
