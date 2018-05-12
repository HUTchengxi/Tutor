package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RankApi {

    /**
     * 获取rank榜数据
     * @param type 榜单类型
     * @param mark 日/周/总榜
     * @param startpos 开始位置
     */
    public String rankSelect(String type, String mark, Integer startpos) throws IOException;
}
