package cn.minsin.core.tools.template_engine;

import com.jfinal.template.Engine;
import com.jfinal.template.Template;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/6/22
 */
public class EnjoyEngineRender {

    public static Engine engine;

    static {
        Engine engine = Engine.use();
        engine.setDevMode(true);
        engine.setToClassPathSourceFactory();
        setEngine(engine);
    }

    /**
     * 重新设置engine 会导致所有的对象都变更
     */
    public static void setEngine(Engine engine) {
        EnjoyEngineRender.engine = engine;
    }

    /**
     * templateName 是需要从classpath中拿, 写法为 test/test.txt
     */
    public static String renderToString(String templateName, Consumer<Map<String, Object>> data) {
        Template template = engine.getTemplate(templateName);
        Map<String, Object> map = new LinkedHashMap<>();
        data.accept(map);
        return template.renderToString(map);
    }

    /**
     * templateName 是需要从classpath中拿, 写法为 test/test.txt
     */
    public static void renderToOutputStream(String templateName, OutputStream outputStream, Consumer<Map<String, Object>> data) {
        Template template = engine.getTemplate(templateName);
        Map<String, Object> map = new LinkedHashMap<>();
        data.accept(map);
        template.render(map, outputStream);
    }

}
