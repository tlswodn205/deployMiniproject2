package site.metacoding.miniproject.domain.resume;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Resume {
	private Integer resumeId;
	private Integer personId;
	private String resumeTitle;
	private String photo;
	private String introduction;
	private String myCloud;
	private Timestamp createdAt;


	@Builder
	public Resume(Integer resumeId, Integer personId, String resumeTitle, String photo, String introduction,
			String myCloud, Timestamp createdAt) {
		super();
		this.resumeId = resumeId;
		this.personId = personId;
		this.resumeTitle = resumeTitle;
		this.photo = photo;
		this.introduction = introduction;
		this.myCloud = myCloud;
		this.createdAt = createdAt;
	}
}
