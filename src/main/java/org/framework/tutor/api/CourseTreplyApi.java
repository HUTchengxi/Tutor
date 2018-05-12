package org.framework.tutor.api;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseTreplyApi {

    /**
     * 获取对应用户的指定课程的家教回复数据
     * @param cid 课程id
     * @param cmid 课程评论id
     */
    public String getTreply(Integer cid, Integer cmid) throws IOException;
}
