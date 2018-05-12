package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseTreplyApi;
import org.framework.tutor.service.CourseTreplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class CourseTreplyApiImpl implements CourseTreplyApi {

    @Autowired
    private CourseTreplyService courseTreplyService;


    //TODO：后续考虑使用redis
    @Override
    public String getTreply(Integer cid, Integer cmid) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        org.framework.tutor.domain.CourseTreply courseTreply = courseTreplyService.getCourseTreply(cid, cmid);

        if(courseTreply == null){
            resultMap.put("info", "null");
        }
        else {
            resultMap.put("info", courseTreply.getInfo());
        }

        return gson.toJson(resultMap);
    }
}
