package SemiProject;

import java.io.Serializable;
import java.util.Date;

public class BoardDTO implements Serializable {
    private String title;
    private String writer;
    private String content;
    private String RegDate;

    public BoardDTO(String title, String writer, String content, String regDate) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.RegDate = regDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }
}
