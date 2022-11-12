package site.metacoding.miniproject.dto.response.notice;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.domain.notice.Notice;

@Getter
@Setter
@NoArgsConstructor
public class NoticeInsertRespDto {
    private Integer noticeId;
    private Integer companyId;
    private String noticeTitle;
    private Integer career;
    private String salary;
    private String degree;
    private String noticeContent;
    private List<String> needSkill;

    public NoticeInsertRespDto(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.companyId = notice.getCompanyId();
        this.noticeTitle = notice.getNoticeTitle();
        this.career = notice.getCareer();
        this.salary = notice.getSalary();
        this.degree = notice.getDegree();
        this.noticeContent = notice.getNoticeContent();
    }
}
