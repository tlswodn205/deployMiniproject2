package site.metacoding.miniproject.dto.request.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class PersonMyPageUpdateReqDto {
	private Integer userId;
	private String password;
	private String personName;
	private String personPhone;
	private Boolean isGender;
	private String address;
	private String degree;
	private Integer career;
	private String personEmail;
}
