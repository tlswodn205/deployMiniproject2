package site.metacoding.miniproject.domain.recommend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.miniproject.dto.response.recommend.RecommendDetailRespDto;

public interface RecommendDao {
	public List<Recommend> findAll();

	public Recommend findById();

	public void insert(Recommend recommend);

	public void update(Recommend recommend);

	public void delete(int recommendId);

	public RecommendDetailRespDto findAboutsubject(@Param("userId") Integer userId,
			@Param("subjectId") Integer subjectId);
}
