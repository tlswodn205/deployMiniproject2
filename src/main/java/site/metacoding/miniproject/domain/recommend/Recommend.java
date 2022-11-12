package site.metacoding.miniproject.domain.recommend;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor 
public class Recommend {
	private Integer recommendId;
	private Integer userId;
	private Integer subjectId;
	private Timestamp createdAt;

	@Builder
	public Recommend(Integer recommendId, Integer userId, Integer subjectId, Timestamp createdAt) {
		super();
		this.recommendId = recommendId;
		this.userId = userId;
		this.subjectId = subjectId;
		this.createdAt = createdAt;
	}
}
