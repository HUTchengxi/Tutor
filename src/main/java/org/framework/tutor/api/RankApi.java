package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RankApi {

    /**
     * 获取rank榜数据
     * @param type
     * @param mark
     * @param startpos
     * @param request
     * @param response
     * @throws IOException
     */
    public void rankSelect(String type, String mark, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
