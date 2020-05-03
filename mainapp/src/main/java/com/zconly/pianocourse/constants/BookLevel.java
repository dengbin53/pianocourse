package com.zconly.pianocourse.constants;

import java.io.Serializable;

/**
 * @Description: 书的难度级别（非钢琴曲目level）
 * @Author: dengbin
 * @CreateDate: 2020/5/3 15:10
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/3 15:10
 * @UpdateRemark: 更新说明
 */
public enum BookLevel implements Serializable {
    LEVEL_0, // 初级
    LEVEL_1, // 中级
    LEVEL_2, // 高级
    LEVEL_3;  // 准专业级

    public String getLevelStr() {
        switch (this) {
            case LEVEL_0:
                return "初级";
            case LEVEL_1:
                return "中级";
            case LEVEL_2:
                return "高级";
            case LEVEL_3:
                return "准专业级";
        }
        return "";
    }

}
