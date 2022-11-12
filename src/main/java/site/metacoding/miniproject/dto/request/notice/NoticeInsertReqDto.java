package site.metacoding.miniproject.dto.request.notice;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.domain.need_skill.NeedSkill;
import site.metacoding.miniproject.domain.notice.Notice;

@Getter
@Setter
@NoArgsConstructor
public class NoticeInsertReqDto {
	private Integer companyId;
	private String noticeTitle;
	private Integer career;
	private String salary;
	private String degree;
	private String noticeContent;
	private List<String> needSkill;

	public Notice toNotice() {
		return Notice.builder().companyId(this.companyId).noticeTitle(this.noticeTitle).isClosed(false)
				.salary(this.salary).degree(this.degree).career(this.career).noticeContent(this.noticeContent).build();
	}

	public NeedSkill toNeedSkill(Integer noticeId, Integer i) {
		return NeedSkill.builder().noticeId(noticeId).skill(this.needSkill.get(i)).build();
	}
}
