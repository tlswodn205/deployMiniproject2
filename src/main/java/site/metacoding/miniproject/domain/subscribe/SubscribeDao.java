package site.metacoding.miniproject.domain.subscribe;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import site.metacoding.miniproject.dto.response.subscribe.SubscribeDeleteRespDto;

public interface SubscribeDao {
  public List<Subscribe> findAll();

  public Subscribe findById(int subscribeId);

  public void insert(Subscribe subscribe);

  public void update(Subscribe subscribe);

  public void deleteById(@Param("userId") Integer userId,
      @Param("subjectId") Integer subjectId);

  public List<Subscribe> findByUserId(int userId);

  public Integer findByUserIdAndSubjectId(
      @Param("userId") Integer userId,
      @Param("subjectId") Integer subjectId);

  public SubscribeDeleteRespDto SubscribeDeleteResult(@Param("userId") Integer userId,
      @Param("subjectId") Integer subjectId);
}
