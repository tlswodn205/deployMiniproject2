package site.metacoding.miniproject.dto.response.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SubmitResumeRespDto {
    private Integer submitResumeId;
    private Integer resumeId;
    private Integer noticeId;
}
