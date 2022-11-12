package site.metacoding.miniproject.domain.user;

import java.util.List;
import site.metacoding.miniproject.dto.request.company.CompanyMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.request.person.PersonMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.response.person.UserIdDeleteRespDto;

public interface UserDao {
  public void insert(User user);

  public User findById(Integer userId);

  public List<User> findAll();

  public void update(User user); // dto생각

  public void deleteById(Integer userId);

  public User findByUsername(String username);

  public void updateToUser(CompanyMyPageUpdateReqDto companyMyPageUpdateDto);

  public void updateToUser(PersonMyPageUpdateReqDto personMyPageUpdateDto);

  public User findByUserorle(String role);

  public UserIdDeleteRespDto userIdDeleteResult(Integer userid);

  public int save(User user);
}
