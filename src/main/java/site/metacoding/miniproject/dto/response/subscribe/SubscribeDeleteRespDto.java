package site.metacoding.miniproject.dto.response.subscribe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscribeDeleteRespDto {

  private Integer subscribeId;
  private Integer userId;
  private Integer subjectId;
}
