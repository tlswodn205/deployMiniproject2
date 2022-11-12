package site.metacoding.miniproject.dto.response.person;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class PersonMyPageRespDto {
	private Integer userId;
	private String username;
	private Integer personId;
	private String personName;
	private String personPhone;
	private Boolean isGender;
	private String address;
	private String degree;
	private String career;
	private String personEmail;
	private List<String> skill;
}