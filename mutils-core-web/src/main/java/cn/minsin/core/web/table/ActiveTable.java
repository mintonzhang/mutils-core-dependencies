package cn.minsin.core.web.table;

import cn.minsin.core.tools.ModelUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 动态表格的表格数据
 *
 * @author: minton.zhang
 * @date: 2019/10/14 19:27
 */
@NoArgsConstructor
public class ActiveTable {

    private final static String NULL = "无";
    private final transient Map<String, ActiveTableData> tempMap = new ConcurrentHashMap<>();
    /**
     * 表头数据 初始化必填
     */
    @Getter
    @Setter
    private List<ActiveTableHeader> tableHeaders;
    /**
     * 是否允许表头对应的数据是空值
     */
    private boolean allowNull;
    /**
     * 表格数据
     */
    @Getter
    @Setter
    private List<Collection<ActiveTableData>> tableData = new CopyOnWriteArrayList<>();

    public ActiveTable(ActiveTableHeader keyData, boolean allowNull) {
        this.tableHeaders = keyData.getTableHeaders();
        this.allowNull = allowNull;
    }

    public ActiveTable(ActiveTableHeader keyData) {
        this.tableHeaders = keyData.getTableHeaders();
        this.allowNull = true;
    }

    public ActiveTable add(String columnKey, Object value) {
        tempMap.put(columnKey, new ActiveTableData(columnKey, value));
        return this;
    }

    public ActiveTable add(Object model) {
        Set<Field> allFieldsAndFilter = ModelUtil.getAllFieldsAndFilter(model);
        for (Field field : allFieldsAndFilter) {
            try {
                field.setAccessible(true);
                Object o = field.get(model);
                tempMap.put(field.getName(), new ActiveTableData(field.getName(), o));
            } catch (IllegalAccessException ignored) {
                //unnecessary tips
            }
        }
        return this;
    }

    /**
     * 调用者 必须要在一个对象结束转换时调用该方法
     */
    public ActiveTable done() {
        Collection<ActiveTableData> temp = new CopyOnWriteArrayList<>();

        for (ActiveTableHeader header : tableHeaders) {
            String columnKey = header.getColumnKey();
            boolean b = tempMap.containsKey(columnKey);
            if (b) {
                temp.add(tempMap.get(columnKey));
            } else if (allowNull) {
                temp.add(new ActiveTableData(columnKey, NULL));
            }
        }
        tableData.add(temp);
        //按照header的顺序
        tempMap.clear();
        return this;
    }

    /**
     * 如果在构造函数中，填写的allowNull为true时，此方法没有任何作用
     */
    public ActiveTable trim() {
        if (!allowNull) {
            Iterator<ActiveTableHeader> iterator = tableHeaders.iterator();
            while (iterator.hasNext()) {
                ActiveTableHeader next = iterator.next();
                String columnKey = next.getColumnKey();
                boolean flag = true;
                outside:
                for (Collection<ActiveTableData> tableDatum : tableData) {
                    for (ActiveTableData activeTableData : tableDatum) {
                        String compareColumnKey = activeTableData.getColumnKey();
                        if (columnKey.equals(compareColumnKey)) {
                            flag = false;
                            break outside;
                        }
                    }
                }
                if (flag) {
                    iterator.remove();
                }
            }
        }
        return this;
    }
}
