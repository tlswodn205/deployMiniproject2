package site.metacoding.miniproject.domain.subscribe;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Subscribe {
	private Integer subscribeId;
	private Integer userId;
	private Integer subjectId;
	private Timestamp createdAt;
	
	@Builder
	public Subscribe(Integer subscribeId, Integer userId, Integer subjectId, Timestamp createdAt) {
		super();
		this.subscribeId = subscribeId;
		this.userId = userId;
		this.subjectId = subjectId;
		this.createdAt = createdAt;
	}
}
