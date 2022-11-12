package site.metacoding.miniproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import site.metacoding.miniproject.domain.company.Company;
import site.metacoding.miniproject.domain.company.CompanyDao;
import site.metacoding.miniproject.domain.user.User;
import site.metacoding.miniproject.domain.user.UserDao;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.company.CompanyJoinReqDto;
import site.metacoding.miniproject.dto.request.company.CompanyMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.request.notice.NoticeInsertReqDto;
import site.metacoding.miniproject.dto.request.user.LoginReqDto;
import site.metacoding.miniproject.utill.JWTToken.CreateJWTToken;

@ActiveProfiles("JTest") // 테스트 어플리케이션 실행
@Transactional
@AutoConfigureMockMvc // MockMvc Ioc 컨테이너에 등록 실제가 아닌 가짜
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // MOCK은 가짜 환경임
@WebAppConfiguration
public class CompanyApiControllerTest {

    private static final String APPLICATION_JSON = "application/json; charset=utf-8";

    @Autowired
    private MockMvc mvc; // 이걸로 통신을 한다

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    private MockHttpSession session;

    private MockCookie cookie;

    // @BeforeEach
    // public void sessionInit() {
    // session = new MockHttpSession();// 직접 new를 했다 MockHttpSession해야 Mock가 된다
    // User user =
    // User.builder().userId(11).username("empc").role("company").build();
    // session.setAttribute("principal", new SessionUserDto(user));
    // }

    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)

    public void sessionToInitCompany() {

        session = new MockHttpSession();
        SessionUserDto sessionUserDto = new SessionUserDto(7, "ire", "company");

        session.setAttribute("principal", sessionUserDto);

        String JwtToken = CreateJWTToken.createToken(sessionUserDto); // Authorization
        cookie = new MockCookie("Authorization", JwtToken);

    }

    public void sessionToInitPerson() {

        session = new MockHttpSession();
        SessionUserDto sessionUserDto = new SessionUserDto(1, "ppc", "person");

        session.setAttribute("principal", sessionUserDto);

        String JwtToken = CreateJWTToken.createToken(sessionUserDto); // Authorization
        cookie = new MockCookie("Authorization", JwtToken);

    }

    // 기업회원가입
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void joinCompany_test() throws Exception {
        // given

        CompanyJoinReqDto companyJoinReqDto = new CompanyJoinReqDto();
        companyJoinReqDto.setUsername("apttftf");
        companyJoinReqDto.setPassword("1234");
        companyJoinReqDto.setRole("company");
        companyJoinReqDto.setAddress("제주특별자치도");

        String body = om.writeValueAsString(companyJoinReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/joinCompany").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("apttftf"));
    }

    // 기업회원가입 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void companyJoinForm_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/companyJoinForm")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 기업추천 리스트 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void recommendListFrom_test() throws Exception {

        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/recommendListFrom").session(session).cookie(cookie)
                        .accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    // 기업 마이페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void companyMyPageForm_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/companyMypageForm").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 기업 마이페이지 수정하기
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void update_test() throws Exception {
        // given
        sessionToInitCompany();
        CompanyMyPageUpdateReqDto companyMyPageUpdateReqDto = new CompanyMyPageUpdateReqDto();
        companyMyPageUpdateReqDto.setCeoName("나사장");

        String body = om.writeValueAsString(companyMyPageUpdateReqDto);
        // json 변경

        // when
        ResultActions resultActions = mvc
                .perform(put("/s/companyMypageUpdate").session(session).cookie(cookie).content(body)
                        // post안에 , 해서 넣을수있다 -> 쿼리스트림
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON) // 둘 중 하나라도 안적으면 안나옴 -> json타입인줄 몰라서
                        .session(session)); // 가짜세션!!

        // then/ charset=utf-8안넣으면바로한글이깨진다
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(jsonPath("$.code").value(1L));
        resultActions.andExpect(jsonPath("$.data.ceoName").value("나사장"));
    }

    // 기업소개등록 페이지 불러오기
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void companyInsertForm_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/companyInsertWriteForm").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 기업추천리스트보기
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void skillCompanyMatching_test() throws Exception {
        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(get("/matchingListFrom").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void skillCompanyMatchingList_test() throws Exception {

        // given
        List<String> skillList = new ArrayList<>();
        skillList.add("java");
        skillList.add("javascript");

        String body = om.writeValueAsString(skillList);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/skillCompanyMatchingList/needSkill")
                        .content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
    }

    // 구독 관리 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void subscribeManage_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/subscribeManageForm").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 구독 취소
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void deleteSubscribe_test() throws Exception {
        // given
        sessionToInitPerson();
        Integer subscribeId = 7;
        // when
        ResultActions resultActions = mvc
                .perform(delete("/s/deleteSubscribe/" + subscribeId)
                        .accept(APPLICATION_JSON)
                        .session(session).cookie(cookie));

        // then/ charset=utf-8안넣으면바로한글이깨진다

        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(jsonPath("$.code").value(1L));
    }

    // 기업 상세보기 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void companyDetail_test() throws Exception {
        // given
        Integer companyId = 1;
        sessionToInitPerson();

        // when
        ResultActions resultActions = mvc
                .perform(get("/companyDetailForm/" + companyId).session(session).cookie(cookie)
                        .accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 기업 구독
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void companySubscribe_test() throws Exception {

        // given
        Integer subjectId = 1;
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/subscribe/" + subjectId).session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 기업 추천
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void companyRecommend_test() throws Exception {

        // given
        Integer subjectId = 1;
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/recommend/" + subjectId).session(session).cookie(cookie)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 등록 공고 보기 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void noticeLoad_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/noticeLoadForm").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 공고 등록하기 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void noticeWrite_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/noticeWriteForm").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 공고 등록
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void noticeInsert_test() throws Exception {

        // given

        NoticeInsertReqDto noticeInsertDto = new NoticeInsertReqDto();
        noticeInsertDto.setCompanyId(1);
        noticeInsertDto.setNoticeTitle("들어오asdfasdf세요");
        noticeInsertDto.setDegree("3년차");

        List<String> needSkill = new ArrayList<>();
        needSkill.add("java");
        needSkill.add("javascript");

        noticeInsertDto.setNeedSkill(needSkill);
        String body = om.writeValueAsString(noticeInsertDto);
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(
                        MockMvcRequestBuilders.post("/s/noticeInsert").session(session).cookie(cookie).content(body)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON));
        System.out.println("디버그 : " + resultActions.andReturn().getResponse().getContentAsString());

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // 공고 상세보기 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void noticeDetail_test() throws Exception {
        // given
        Integer noticeId = 1;

        // when
        ResultActions resultActions = mvc
                .perform(get("/s/noticeDetailForm/" + noticeId).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // 공고 상세보기 페이지
    @Sql(scripts = "classpath:create.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void myCompanyDetail_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/companyDetail").session(session).cookie(cookie).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }
}