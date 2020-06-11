package cn.minsin.core.tools.area;

import cn.minsin.core.override.JsonModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AreaModel extends JsonModel {

    /**
     *
     */
    private static final long serialVersionUID = 5712490286643607391L;

    // 唯一标识 国家指定编码,为身份证前几位
    private String adcode;
    // 经度
    private Double lat;
    // 纬度
    private Double lng;
    // 城市名
    private String name;
    // 等级
    private String level;
    // 上级
    private String parent;
    // 子类
    private List<AreaModel> children;


    public void setChild(AreaModel children) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(children);
    }
}
