package main.java.org.framework.tutor.domain;

import java.io.Serializable;

/**
 * 用户密保实体类
 * @author chengxi
 */
public class UserSecret implements Serializable {

    private final static long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String question;

    private String answer;

    private String msg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    @Override
    public String toString() {
        return "UserSecret{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}