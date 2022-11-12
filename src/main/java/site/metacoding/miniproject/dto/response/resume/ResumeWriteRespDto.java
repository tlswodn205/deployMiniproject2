package site.metacoding.miniproject.dto.response.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResumeWriteRespDto {
    private Integer resumeId;
    private Integer personId;
    private String resumeTitle;
    private String photo;
    private String introduction;
    private String myCloud;
}
