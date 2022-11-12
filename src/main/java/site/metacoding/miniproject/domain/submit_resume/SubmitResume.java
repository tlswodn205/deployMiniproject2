package site.metacoding.miniproject.domain.submit_resume;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SubmitResume {
    private Integer submitResumeId;
    private Integer resumeId;
    private Integer noticeId;
    private Timestamp createdAt;

    @Builder
	public SubmitResume(Integer submitResumeId, Integer resumeId, Integer noticeId, Timestamp createdAt) {
		super();
		this.submitResumeId = submitResumeId;
		this.resumeId = resumeId;
		this.noticeId = noticeId;
		this.createdAt = createdAt;
	}
}

