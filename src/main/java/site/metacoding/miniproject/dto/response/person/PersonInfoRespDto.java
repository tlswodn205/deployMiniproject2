package site.metacoding.miniproject.dto.response.person;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.dto.response.recommend.RecommendDetailRespDto;

@NoArgsConstructor
@Getter
@Setter
public class PersonInfoRespDto {
	private Integer personId;
	private Integer userId;
	private String personName;
	private String personEmail;
	private String personPhone;
	private Boolean isGender;
	private String address;
	private String degree;
	private Integer career;
	private Integer personSkillId;
	private List<String> skill;
	private Integer recommendCount;
	private Integer recommendId;
	private Timestamp createdAt;

	public void insertRecommend(RecommendDetailRespDto recommendDetailRespDto) {
		this.recommendCount = recommendDetailRespDto.getRecommendCount();
		this.recommendId = recommendDetailRespDto.getRecommendId();
	}
}