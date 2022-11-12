package site.metacoding.miniproject.dto.response.notice;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeApplyRespDto {
	private Integer noticeId;
	private Integer companyId;
	private String noticeTitle;
	private String resumeTitle;
	private String address;
	private String companyName;
	private List<String> needSkillList;
}
