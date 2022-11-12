package site.metacoding.miniproject.dto.response.person;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdDeleteRespDto {

  private Integer userId;
  private String username;
  private String password;
  private String role;
}
