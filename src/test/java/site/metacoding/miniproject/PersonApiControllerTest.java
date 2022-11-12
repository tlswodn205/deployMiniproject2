package site.metacoding.miniproject;

import java.util.ArrayList;
import java.util.List;

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

import site.metacoding.miniproject.domain.user.UserDao;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.resume.ResumeWriteReqDto;
import site.metacoding.miniproject.dto.request.resume.SubmitResumeReqDto;
import site.metacoding.miniproject.dto.response.person.InterestPersonRespDto;
import site.metacoding.miniproject.utill.JWTToken.CreateJWTToken;
import site.metacoding.miniproject.dto.request.person.PersonJoinReqDto;
import site.metacoding.miniproject.dto.request.person.PersonMyPageUpdateReqDto;

@ActiveProfiles("JTest") // 테스트 어플리케이션 실행
@Transactional
@AutoConfigureMockMvc // MockMvc Ioc 컨테이너에 등록 실제가 아닌 가짜
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // MOCK은 가짜 환경임
@WebAppConfiguration
public class PersonApiControllerTest {
    private static final String APPLICATION_JSON = "application/json; charset=utf-8";

    @Autowired
    private MockMvc mvc; // 이걸로 통신을 한다

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserDao userDao;

    private MockHttpSession session;

    private MockCookie cookie;
    // @BeforeEach
    // public void sessionInit() {
    // session = new MockHttpSession();// 직접 new를 했다 MockHttpSession해야 Mock가 된다
    // User user = User.builder().userId(1).username("ssar").build();// password 는
    // 없다
    // session.setAttribute("principal", new SessionUserDto(user));// 가짜세션이 만들어진
    // 상태이다 -> 아직 주입은 안된 상태
    // }

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

    // 개인회원가입
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void joinPerson_test() throws Exception {
        // given

        List<String> skill = new ArrayList<>();
        skill.add("java");
        skill.add("javascript");
        PersonJoinReqDto personJoinReqDto = new PersonJoinReqDto();
        personJoinReqDto.setUsername("apttftf");
        personJoinReqDto.setPassword("1234");
        personJoinReqDto.setRole("person");
        personJoinReqDto.setPersonSkillList(skill);

        String body = om.writeValueAsString(personJoinReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/joinPerson").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 개인 회원가입 페이지 불러오기
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void joinPersonForm_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/personJoinForm")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resumeWrite_test() throws Exception {
        // given
        ResumeWriteReqDto resumeWriteReqDto = new ResumeWriteReqDto();
        resumeWriteReqDto.setResumeTitle("안녕하세요, 주시윤입니다");
        resumeWriteReqDto.setAddress("제주광역시");
        resumeWriteReqDto.setIntroduction("자기소개");
        resumeWriteReqDto.setMyCloud("www.github.com");

        sessionToInitPerson();

        String body = om.writeValueAsString(resumeWriteReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(
                        MockMvcRequestBuilders.post("/s/resumeSave/").session(session)
                                .cookie(cookie).content(body)
                                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void personRecommend_test() throws Exception {
        // given
        sessionToInitCompany();
        Integer subjectId = 1;
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/personRecommend/" + subjectId)
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
    public void personDetailForm_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc

                .perform(MockMvcRequestBuilders.get("/s/personDetailForm/" + 2).session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void personRecommendList_test() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/personRecommendList")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void PersonRecommendListFrom_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/personRecommendListForm").session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 관심구직자리스트
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void interestPersonSkillList_test() throws Exception {
        // given
        List<String> skillList = new ArrayList<>();
        skillList.add("java");
        skillList.add("javascript");

        String body = om.writeValueAsString(skillList);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/personSkillPersonMatching/personSkill").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
    }

    // 구직자 마이페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void PersonMypageForm_Test() throws Exception {
        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/personMypageForm")
                        .session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void updateToPerson_test() throws Exception {
        // given
        PersonMyPageUpdateReqDto personMyPageUpdateReqDto = new PersonMyPageUpdateReqDto();
        personMyPageUpdateReqDto.setUserId(1);
        personMyPageUpdateReqDto.setPassword("1234");
        personMyPageUpdateReqDto.setPersonName("주시영");
        personMyPageUpdateReqDto.setPersonPhone("01000000000");
        personMyPageUpdateReqDto.setAddress("제주특별자치도");
        personMyPageUpdateReqDto.setDegree("대졸 전공");
        personMyPageUpdateReqDto.setCareer(10);
        personMyPageUpdateReqDto.setPersonEmail("ssar@naver.com");

        sessionToInitPerson();

        String body = om.writeValueAsString(personMyPageUpdateReqDto);
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.put("/s/personMypageUpdate").session(session).cookie(cookie)
                        .content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 이력서삭제하기
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resumeDelete_test() throws Exception {
        // given
        // LoginReqDto loginReqDto = new LoginReqDto();
        // loginReqDto.setUsername("ssar");
        // loginReqDto.setPassword("1234");
        // String body = om.writeValueAsString(loginReqDto);
        sessionToInitPerson();

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/s/deleteResume/1").session(session).cookie(cookie)
                        // .content(body)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println(
                "디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void personResumeManage_Test() throws Exception {
        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/resumeManageForm").session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 공고별구직자리스트
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void findNoticePerApplier_Test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/noticePerApplierForm/" + 1)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void closeNotice_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/noticeClose/" + 1).session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 지원공고목록
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void personApply_Test() throws Exception {
        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/personApplyForm")
                        .session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 이력서제출하기
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void submitResume_test() throws Exception {
        // given
        SubmitResumeReqDto submitResumeReqDto = new SubmitResumeReqDto();
        submitResumeReqDto.setResumeId(1);
        submitResumeReqDto.setNoticeId(1);

        String body = om.writeValueAsString(submitResumeReqDto);

        sessionToInitPerson();

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/s/submitResume/").session(session).cookie(cookie).content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }
}
