package site.metacoding.miniproject.dto.response.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CloseNoticeRespDto {
    private Integer noticeId;
    private Integer companyId;
    private String noticeTitle;
    private boolean isClosed;
}
