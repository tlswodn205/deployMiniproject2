package site.metacoding.miniproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.miniproject.domain.user.User;
import site.metacoding.miniproject.domain.user.UserDao;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.user.LoginReqDto;
import site.metacoding.miniproject.dto.response.person.UserIdDeleteRespDto;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserDao userDao;

  @Transactional
  public SessionUserDto 로그인(LoginReqDto loginDto) {
    User userPS = userDao.findByUsername(loginDto.getUsername());
    if (userPS == null) {
      return null;
    }

    if (userPS.getPassword().equals(loginDto.getPassword())) {
      return new SessionUserDto(userPS);
    }
    return null;
  }

  @Transactional
  public User 유저네임으로유저찾기(String Username) {
    User userPS = userDao.findByUsername(Username);
    return userPS;
  }

  @Transactional
  public UserIdDeleteRespDto 유저삭제하기(Integer userId) {
    UserIdDeleteRespDto userIdDeleteRespDto = userDao.userIdDeleteResult(
        userId);
    return userIdDeleteRespDto;
  }
}
