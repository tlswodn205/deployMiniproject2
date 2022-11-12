package site.metacoding.miniproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.miniproject.domain.resume.Resume;
import site.metacoding.miniproject.domain.resume.ResumeDao;
import site.metacoding.miniproject.domain.user.User;
import site.metacoding.miniproject.domain.user.UserDao;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.company.CompanyJoinReqDto;
import site.metacoding.miniproject.utill.JWTToken.CreateJWTToken;

@ActiveProfiles("JTest") // 테스트 어플리케이션 실행
@Transactional
@AutoConfigureMockMvc // MockMvc Ioc 컨테이너에 등록 실제가 아닌 가짜
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // MOCK은 가짜 환경임
@WebAppConfiguration
public class ResumeApiControllerTest {

    private static final String APPLICATION_JSON = "application/json; charset=utf-8";

    @Autowired
    private MockMvc mvc; // 이걸로 통신을 한다

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserDao userDao;
    private ResumeDao resumeDao;

    private MockHttpSession session;

    private MockCookie cookie;

    public void sessionToInitCompany() {

        session = new MockHttpSession();
        SessionUserDto sessionUserDto = new SessionUserDto(7, "ire", "company");

        session.setAttribute("principal", sessionUserDto);

        String JwtToken = CreateJWTToken.createToken(sessionUserDto); // Authorization
        cookie = new MockCookie("Authorization", JwtToken);

    }

    public void sessionToInitPerson() {

        session = new MockHttpSession();
        SessionUserDto sessionUserDto = new SessionUserDto(1, "ssar", "person");

        session.setAttribute("principal", sessionUserDto);

        String JwtToken = CreateJWTToken.createToken(sessionUserDto); // Authorization
        cookie = new MockCookie("Authorization", JwtToken);

    }

    // @BeforeEach
    // public void sessionInit() {
    // session = new MockHttpSession();// 직접 new를 했다 MockHttpSession해야 Mock가 된다
    // User user = User.builder().userId(1).username("ssar").build();// password 는
    // 없다
    // session.setAttribute("principal", new SessionUserDto(user));// 가짜세션이 만들어진
    // 상태이다 -> 아직 주입은 안된 상태
    // }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resumeForm_Test() throws Exception {
        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/resumeWriteForm")
                        .session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resumeDetailForm_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/resumeDetailForm/" + 1)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

    }
}