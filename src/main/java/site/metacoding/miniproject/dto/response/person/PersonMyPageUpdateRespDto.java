package site.metacoding.miniproject.dto.response.person;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class PersonMyPageUpdateRespDto {
	private Integer userId;
	private Integer personId;
	private String personName;
	private String personPhone;
	private String isGender;
	private String address;
	private String degree;
	private Integer career;
	private String personEmail;
	private List<String> skill;
}
