package cn.minsin.core.web.table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author minton.zhang
 * @since 2019/12/2 18:02
 */
@Getter
@Setter
public class ActiveTableData extends BaseActiveTable {
    /**
     * 值
     */
    private Object value;

    public ActiveTableData(String columnKey, Object value) {
        this.value = value;
        super.setColumnKey(columnKey);
    }
}
