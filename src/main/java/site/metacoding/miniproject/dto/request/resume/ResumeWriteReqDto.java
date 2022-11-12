package site.metacoding.miniproject.dto.request.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.domain.resume.Resume;

@NoArgsConstructor
@Getter
@Setter
public class ResumeWriteReqDto {
	private Integer personId;
	private String resumeTitle;
	private String photo;
	private String introduction;
	private String address;
	private String myCloud;

	public Resume toEntity() {
		return Resume.builder().personId(this.personId).resumeTitle(this.resumeTitle).photo(this.photo)
				.introduction(this.introduction).myCloud(this.myCloud).build();
	}
}
