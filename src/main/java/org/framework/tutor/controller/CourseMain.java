package org.framework.tutor.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程表控制类
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/coursemain_con")
public class CourseMain {

    @Autowired
    private CourseMService courseMService;

    @Autowired
    private UserMService userMService;

    @Autowired
    private CourseCMService courseCMService;

    @Autowired
    private CourseOService courseOService;

    @Autowired
    private CourseChService courseChService;

    @Autowired
    private CourseSummaryService courseSummaryService;

    /**
     * 加载课程数据
     *
     * @param stype
     * @param ctype
     * @param sort
     * @param startpos
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcourselist")
    public void getCourseList(Integer stype, String ctype, String sort, Integer startpos, Integer status, String keyword,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;
        List<org.framework.tutor.domain.CourseMain> courseMains = null;
        //默认：不限进行排序
        if (ctype.equals("all")) {
            //时间最新排序
            if (sort.equals("new")) {
                //未指定主类别
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListNew(startpos);
                    } else {
                        courseMains = courseMService.getCourseListNewKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListNew(stype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListNewKW(stype, keyword, startpos);
                    }
                }
            }
            //搜索最热排序
            else if (sort.equals("hot")) {
                //未指定主类别
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListHot(startpos);
                    } else {
                        courseMains = courseMService.getCourseListHotKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListHot(stype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListHotSKW(stype, keyword, startpos);
                    }
                }
            }
            //评论最多排序
            else {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListMore(startpos);
                    } else {
                        courseMains = courseMService.getCourseListMoreKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListMore(stype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListMoreSKW(stype, keyword, startpos);
                    }
                }
            }
        }
        //选择指定子类别的课程
        else {
            //时间最新排序
            if (sort.equals("new")) {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListNewAC(ctype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListNewACK(ctype, keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListNew(stype, ctype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListNewKW(stype, ctype, keyword, startpos);
                    }
                }
            }
            //搜索最热排序
            else if (sort.equals("hot")) {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListHotAC(ctype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListHotACK(ctype, keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListHot(stype, ctype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListHotKW(stype, ctype, keyword, startpos);
                    }
                }
            }
            //评论最多排序
            else {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListMoreAC(ctype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListMoreACK(ctype, keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListMore(stype, ctype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListMoreKW(stype, ctype, keyword, startpos);
                    }
                }
            }
        }
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                res += "\"" + i + "\": ";
                String temp = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                        "\"id\": \"" + courseMain.getId() + "\", " +
                        "\"name\": \"" + courseMain.getName() + "\", " +
                        "\"jcount\": \"" + courseMain.getJcount() + "\", " +
                        "\"nickname\": \"" + userMain.getNickname() + "\", " +
                        "\"price\": \"" + courseMain.getPrice() + "\", " +
                        "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                        "\"descript\": \"" + courseMain.getDescript() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }
        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * @param [response]
     * @return void
     * @Description 获取所有科目类别
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/getallcoursetype")
    public void getAllCourseType(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.getAllCourseType();
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 0;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                String temp = "\"ctype" + i + "\": \"" + courseMain.getCtype() + "\", ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定主类别的所有科目类别
     *
     * @param stype
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursetype")
    public void getCourseType(String stype, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.getCourseType(stype);
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 0;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                String temp = "\"ctype" + i + "\": \"" + courseMain.getCtype() + "\", ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 关键字获取所有对应的课程数据
     *
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/coursesearch")
    public void courseSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.courseSearch(keyword);
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                res += "\"" + i + "\": ";
                String temp = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                        "\"id\": \"" + courseMain.getId() + "\", " +
                        "\"name\": \"" + courseMain.getName() + "\", " +
                        "\"jcount\": \"" + courseMain.getJcount() + "\", " +
                        "\"nickname\": \"" + userMain.getNickname() + "\", " +
                        "\"price\": \"" + courseMain.getPrice() + "\", " +
                        "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                        "\"descript\": \"" + courseMain.getDescript() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定的课程的数据
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursebyid")
    public void getCourseById(Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        org.framework.tutor.domain.CourseMain courseMain = courseMService.getCourseById(id);
        if (courseMain == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            UserMain userMain = userMService.getByUser(courseMain.getUsername());

            res = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                    "\"id\": \"" + courseMain.getId() + "\", " +
                    "\"stype\": \"" + courseMain.getStype() + "\", " +
                    "\"ctype\": \"" + courseMain.getCtype() + "\", " +
                    "\"name\": \"" + courseMain.getName() + "\", " +
                    "\"jcount\": \"" + courseMain.getJcount() + "\", " +
                    "\"nickname\": \"" + userMain.getNickname() + "\", " +
                    "\"info\": \"" + userMain.getInfo() + "\", " +
                    "\"price\": \"" + courseMain.getPrice() + "\", " +
                    "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                    "\"total\": \"" + courseMain.getTotal() + "\", " +
                    "\"descript\": \"" + courseMain.getDescript() + "\"}";
        }
        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取所搜索的课程数量，便于实现分页
     *
     * @param stype
     * @param ctype
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursecount")
    public void getCourseCount(Integer stype, String ctype, Integer status, String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        Integer total = 0;
        //默认，不限课程类别进行排序
        if (ctype.equals("all")) {
            //未指定主类别
            if (stype == -1) {
                //不是通过用户搜素
                if (status == null || status == 0) {
                    total = courseMService.getCourseCount();
                } else {
                    total = courseMService.getCourseCountK(keyword);
                }
            } else {
                if (status == null || status == 0) {
                    total = courseMService.getCourseCountS(stype);
                } else {
                    total = courseMService.getCourseCountSK(stype, keyword);
                }
            }
        } else {
            if (stype == -1) {
                if (status == null || status == 0) {
                    total = courseMService.getCourseCountC(ctype);
                } else {
                    total = courseMService.getCourseCountCK(ctype, keyword);
                }
            } else {
                if (status == null || status == 0) {
                    total = courseMService.getCourseCountCS(ctype, stype);
                } else {
                    total = courseMService.getCourseCountCSK(ctype, stype, keyword);
                }
            }
        }

        res = "{\"total\": \"" + total + "\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前家教的所有发布数据
     * @author yinjimin
     * @date 2018/4/14
     */
    @PostMapping("/getmypublish")
    public void getMyPublish(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.getMyPublish(username);
        if (courseMains.size() == 0) {
            res = "{\"status\": \"0\"}";
        } else {
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                Integer score = courseCMService.getMyPublishAvg(courseMain.getId());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                Integer buycount = courseOService.getMyCourseOrderCount(courseMain.getId()).size();
                res += "\"" + i + "\": ";
                String temp = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                        "\"id\": \"" + courseMain.getId() + "\", " +
                        "\"name\": \"" + courseMain.getName() + "\", " +
                        "\"viscount\": \"" + courseMain.getHcount() + "\", " +
                        "\"ptime\": \"" + simpleDateFormat2.format(courseMain.getPtime()) + "\", " +
                        "\"regtime\": \"" + simpleDateFormat.format(courseMain.getPtime()) + "\", " +
                        "\"comcount\": \"" + courseMain.getCcount() + "\", " +
                        "\"buycount\": \"" + courseMain.getCcount() + "\", " +
                        "\"score\": \"" + score + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }


        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * @param [name, stype, ctype, imgsrc, total, jcount, price, sumTitle1, sumTitle2, sumTitle3,
     *               sumDescript1, sumDescript2, sumDescript3, chapTitle, chaDescript, request, response]
     * @return void
     * @Description 发布课程
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/publishnewcourse")
    @Transactional
    public void publishNewCourse(@RequestParam String name, @RequestParam Integer stype, @RequestParam String ctype, @RequestParam String descript,
                                 MultipartFile imgsrc, @RequestParam Integer total, @RequestParam Integer jcount,
                                 @RequestParam Double price, @RequestParam String sumTitle1, @RequestParam String sumTitle2,
                                 @RequestParam String sumTitle3, @RequestParam String sumDescript1, @RequestParam String sumDescript2,
                                 @RequestParam String sumDescript3, @RequestParam String chapTitle, @RequestParam String chapDescript,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Integer identity = (Integer) session.getAttribute("identity");
        //判断当前用户是否为家教身份
        if (identity != 1) {
            res = "{\"status\": \"invalid\"}";
        } else {
            //判断课程名称是否已存在
            org.framework.tutor.domain.CourseMain nameCourseMain = courseMService.checkIsexistName(name);
            if (nameCourseMain != null) {
                res = "{\"status\": \"courseexist\"}";
            } else {
                //上传图片
                String webPath = session.getServletContext().getRealPath("/");
                System.out.println(webPath);
                String srcPath = webPath.substring(0, webPath.lastIndexOf("\\"));
                srcPath = srcPath.substring(0, srcPath.lastIndexOf("\\"));
                System.out.println(srcPath);
                File file = new File(srcPath + File.separator + "/resources/static/images/user/course/" + imgsrc.getOriginalFilename());
                if (file.exists()) {
                    res = "{\"status\": \"filexist\"}";
                    writer.print(new JsonParser().parse(res).getAsJsonObject());
                    writer.flush();
                    writer.close();
                    throw new RuntimeException();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(imgsrc.getBytes());
                fos.flush();
                fos.close();
                res = "{\"status\": \"valid\"}";
                //保存课程基本信息
                courseMService.publishCourse(username, name, "/images/user/course/" + imgsrc.getOriginalFilename(), stype, ctype, jcount, descript, price, total);
                org.framework.tutor.domain.CourseMain courseMain = courseMService.getByName(username, name, stype, ctype);
                //保存课程概述信息
                courseSummaryService.addCourseSummary(username, courseMain.getId(), sumTitle1, sumDescript1);
                courseSummaryService.addCourseSummary(username, courseMain.getId(), sumTitle2, sumDescript2);
                courseSummaryService.addCourseSummary(username, courseMain.getId(), sumTitle3, sumDescript3);
                //保存目录信息
                String eq = "c03c1650fa940cd2f5de959bfbd6d8a6";
                String[] titleArr = chapTitle.split(eq);
                String[] descriptArr = chapDescript.split(eq);
                int i = 1;
                for (String title : titleArr) {
                    courseChService.addChapter(courseMain.getId(), i, title, descriptArr[--i]);
                    i += 2;
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    /**
     *
     * @Description 获取课程概述
     * @param
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/25
     */
    @PostMapping("/getcoursesummary")
    public @ResponseBody String getCourseSummary(@RequestParam Integer cid) {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(3);

        List<CourseSummary> courseSummarys = courseSummaryService.getCourseSummary(cid);

        if (courseSummarys.size() == 0) {
            resultMap.put("status", "none");
        } else {

            UserMain userMain = userMService.getByUser(courseSummarys.get(0).getUsername());
            resultMap.put("nickname", userMain.getNickname());
            resultMap.put("imgsrc", userMain.getImgsrc());
            List<Object> rowList = new ArrayList<>(3);
            for (CourseSummary courseSummary : courseSummarys) {
                Map<String, Object> rowMap = new HashMap<>(2);
                rowMap.put("title", courseSummary.getTitle());
                rowMap.put("descript", courseSummary.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("rows", rowList);
        }

        return gson.toJson(resultMap);
    }
}
