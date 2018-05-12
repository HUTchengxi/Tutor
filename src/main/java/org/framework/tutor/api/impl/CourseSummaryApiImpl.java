package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.CourseSummaryApi;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.service.CourseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseSummaryApiImpl implements CourseSummaryApi {

    @Autowired
    private CourseSummaryService courseSummaryService;

    @Autowired
    private HttpServletRequest request;


    //TODO：后续考虑使用redis
    @Override
    public String getCourseSummaryInfo(Integer cid) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<CourseSummary> courseSummarys = courseSummaryService.getCourseSummaryInfo(cid);

        if(courseSummarys.size() == 0){
            resultMap.put("status", 0);
        }
        else{
            for (CourseSummary courseSummary: courseSummarys) {
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("title", courseSummary.getTitle());
                rowMap.put("id", courseSummary.getId());
                rowMap.put("descript", courseSummary.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String updateCourseSummary(Integer id, String title, String descript) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = courseSummaryService.updateCourseSummary(id, title, descript);
        if(row == 1){
            resultMap.put("status", "valid");
        }
        else{
            resultMap.put("status", "sqlerr");
        }

        return gson.toJson(resultMap);
    }
}
