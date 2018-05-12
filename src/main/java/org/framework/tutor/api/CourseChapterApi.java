package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseChapterApi {

    /**
     * 获取指定课程的章节目录数据
     * @param cid 课程id
     */
    public String getCourseChapter(Integer cid) throws IOException;

    /**
     * @Description 删除指定目录
     * @param id: 待删除的目录id
     */
    public String deleteChapter(Integer id) throws IOException;

    /**
     * @Description 更新目录
     * @param id：待更新目录id
     * @param cid：课程id
     * @param title：目录标题
     * @param descript：目录描述
     */
    public String modChapter(Integer id, Integer cid, String title, String descript) throws IOException;
}
