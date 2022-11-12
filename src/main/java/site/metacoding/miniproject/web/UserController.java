package site.metacoding.miniproject.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.user.LoginReqDto;
import site.metacoding.miniproject.dto.response.CMRespDto;
import site.metacoding.miniproject.dto.response.person.UserIdDeleteRespDto;
import site.metacoding.miniproject.service.UserService;
import site.metacoding.miniproject.utill.JWTToken.CookieForToken;
import site.metacoding.miniproject.utill.JWTToken.CreateJWTToken;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;
  private final HttpSession session;

  @PostMapping("/login")
  public CMRespDto<?> login(@RequestBody LoginReqDto loginDto, HttpServletResponse resp) {
    SessionUserDto principal = userService.로그인(loginDto);
    if (principal == null) {
      return new CMRespDto<>(-1, "로그인실패", null);
    }
    String token = CreateJWTToken.createToken(principal);

    resp.addHeader("Authorization", "Bearer " + token);
    resp.addCookie(CookieForToken.setCookie(token));
    return new CMRespDto<>(1, "로그인성공", principal);
  }

  // 로그인 페이지
  @GetMapping("/loginForm")
  public CMRespDto<?> loginForm() {
    return new CMRespDto<>(1, "로그인 페이지 불러오기 성공", null);
  }

  // 로그아웃
  @GetMapping("/logout")
  public CMRespDto<?> logout(HttpServletResponse resp) {
    Cookie cookie = new Cookie("Authorization", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    resp.addCookie(cookie);

    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");

    session.removeAttribute("principal");
    return new CMRespDto<>(1, "로그아웃 성공", userPS);
  }

  @GetMapping(value = "/")
  public String isOk() {
    return "예아";
  }

  // // role 구분하기 연습
  // @GetMapping("/rolesplit")
  // public
  // User principal = userService.유저롤로개인기업구분하기(null);

  @DeleteMapping("/deleteuser/{userid}")
  public CMRespDto<?> deleteUserId(@PathVariable Integer userid) {
    UserIdDeleteRespDto userIdDeleteRespDto = userService.유저삭제하기(userid);
    return new CMRespDto<>(userid, "유저삭제완료", userIdDeleteRespDto);
  }
}
