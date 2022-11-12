package site.metacoding.miniproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CMRespDto<T> {
	private Integer code; // 1정상 -1실패
	private String msg; // 실패의 이유
	private T data; // 응답할 데이터
}
