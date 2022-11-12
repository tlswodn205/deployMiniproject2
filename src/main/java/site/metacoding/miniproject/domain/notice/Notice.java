package site.metacoding.miniproject.domain.notice;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Notice {
	private Integer noticeId;
	private Integer companyId;
	private String noticeTitle;
	private boolean isClosed;
	private String salary;
	private String degree;
	private Integer career;
	private String noticeContent;
	private Timestamp createdAt;

	@Builder
	public Notice(Integer noticeId, Integer companyId, String noticeTitle, boolean isClosed, String salary,
			String degree, Integer career, String noticeContent, Timestamp createdAt) {
		super();
		this.noticeId = noticeId;
		this.companyId = companyId;
		this.noticeTitle = noticeTitle;
		this.isClosed = isClosed;
		this.salary = salary;
		this.degree = degree;
		this.career = career;
		this.noticeContent = noticeContent;
		this.createdAt = createdAt;
	}

}
