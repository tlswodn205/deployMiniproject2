package site.metacoding.miniproject.dto.response.resume;

import lombok.Getter;

@Getter
public class ResumeDeleteRespDto {
    private Integer personId;
	private String resumeTitle;
	private String photo;
	private String introduction;
	private String myCloud;
}
