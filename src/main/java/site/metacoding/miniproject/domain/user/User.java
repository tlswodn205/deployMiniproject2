package site.metacoding.miniproject.domain.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class User {
	private Integer userId;
	private String username;
	private String password;
	private String role;
	private Timestamp createdAt;
	
	@Builder
	public User(Integer userId, String username, String password, String role, Timestamp createdAt) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
	}
}
