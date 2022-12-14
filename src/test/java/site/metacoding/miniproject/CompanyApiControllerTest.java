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

@Sql(scripts = "classpath:dummy.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("jtest") // ????????? ?????????????????? ??????
@Transactional
@AutoConfigureMockMvc // MockMvc Ioc ??????????????? ?????? ????????? ?????? ??????
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // MOCK??? ?????? ?????????
@WebAppConfiguration
public class CompanyApiControllerTest {

    private static final String APPLICATION_JSON = "application/json; charset=utf-8";

    @Autowired
    private MockMvc mvc; // ????????? ????????? ??????

    @Autowired
    private ObjectMapper om;

    private MockHttpSession session;

    // @BeforeEach
    // public void sessionInit() {
    // session = new MockHttpSession();// ?????? new??? ?????? MockHttpSession?????? Mock??? ??????
    // User user =
    // User.builder().userId(11).username("empc").role("company").build();
    // session.setAttribute("principal", new SessionUserDto(user));
    // }

    public void sessionToInitCompany() {
        session = new MockHttpSession();
        SessionUserDto sessionUserDto = new SessionUserDto(7, "ire", "company");
        session.setAttribute("principal", sessionUserDto);

    }

    public void sessionToInitPerson() {
        session = new MockHttpSession();
        SessionUserDto sessionUserDto = new SessionUserDto(1, "ppc", "person");
        session.setAttribute("principal", sessionUserDto);
    }

    // ??????????????????

    @Test
    public void joinCompany_test() throws Exception {
        // given

        CompanyJoinReqDto companyJoinReqDto = new CompanyJoinReqDto();
        companyJoinReqDto.setUsername("apttftf");
        companyJoinReqDto.setPassword("1234");
        companyJoinReqDto.setRole("company");
        companyJoinReqDto.setAddress("?????????????????????");

        String body = om.writeValueAsString(companyJoinReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/joinCompany").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("????????? : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("apttftf"));
    }

    // ?????????????????? ?????????
    @Test
    public void companyJoinForm_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/companyJoinForm")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("????????? : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // ???????????? ????????? ?????????
    @Test
    public void recommendListFrom_test() throws Exception {

        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/recommendListFrom").session(session)
                        .accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    // ?????? ???????????????
    @Test
    public void companyMyPageForm_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/companyMypageForm").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ?????? ??????????????? ????????????
    @Test
    public void update_test() throws Exception {
        // given
        sessionToInitCompany();
        CompanyMyPageUpdateReqDto companyMyPageUpdateReqDto = new CompanyMyPageUpdateReqDto();
        companyMyPageUpdateReqDto.setCeoName("?????????");

        String body = om.writeValueAsString(companyMyPageUpdateReqDto);
        // json ??????

        // when
        ResultActions resultActions = mvc
                .perform(put("/s/companyMypageUpdate").session(session).content(body)
                        // post?????? , ?????? ??????????????? -> ???????????????
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON) // ??? ??? ???????????? ???????????? ????????? -> json???????????? ?????????
                        .session(session)); // ????????????!!

        // then/ charset=utf-8????????????????????????????????????
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? 1 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(jsonPath("$.code").value(1L));
        resultActions.andExpect(jsonPath("$.data.ceoName").value("?????????"));
    }

    // ?????????????????? ????????? ????????????
    @Test
    public void companyInsertForm_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/companyInsertWriteForm").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ???????????????????????????
    @Test
    public void skillCompanyMatching_test() throws Exception {
        // given
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(get("/matchingListFrom").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

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
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
    }

    // ?????? ?????? ?????????

    @Test
    public void subscribeManage_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/subscribeManageForm").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ?????? ??????

    @Test
    public void deleteSubscribe_test() throws Exception {
        // given
        sessionToInitPerson();
        Integer subscribeId = 7;
        // when
        ResultActions resultActions = mvc
                .perform(delete("/s/deleteSubscribe/" + subscribeId)
                        .accept(APPLICATION_JSON)
                        .session(session));

        // then/ charset=utf-8????????????????????????????????????

        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(jsonPath("$.code").value(1L));
    }

    // ?????? ???????????? ?????????

    @Test
    public void companyDetail_test() throws Exception {
        // given
        Integer companyId = 1;
        sessionToInitPerson();

        // when
        ResultActions resultActions = mvc
                .perform(get("/companyDetailForm/" + companyId).session(session)
                        .accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ?????? ??????

    @Test
    public void companySubscribe_test() throws Exception {

        // given
        Integer subjectId = 1;
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/subscribe/" + subjectId).session(session)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("????????? : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // ?????? ??????

    @Test
    public void companyRecommend_test() throws Exception {

        // given
        Integer subjectId = 1;
        sessionToInitPerson();
        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/s/recommend/" + subjectId).session(session)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        System.out.println("????????? : " + resultActions.andReturn().getResponse().getContentAsString());
        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // ?????? ?????? ?????? ?????????

    @Test
    public void noticeLoad_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/noticeLoadForm").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ?????? ???????????? ?????????

    @Test
    public void noticeWrite_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/noticeWriteForm").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ?????? ??????

    @Test
    public void noticeInsert_test() throws Exception {

        // given

        NoticeInsertReqDto noticeInsertDto = new NoticeInsertReqDto();
        noticeInsertDto.setCompanyId(1);
        noticeInsertDto.setNoticeTitle("?????????asdfasdf??????");
        noticeInsertDto.setDegree("3??????");

        List<String> needSkill = new ArrayList<>();
        needSkill.add("java");
        needSkill.add("javascript");

        noticeInsertDto.setNeedSkill(needSkill);
        String body = om.writeValueAsString(noticeInsertDto);
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(
                        MockMvcRequestBuilders.post("/s/noticeInsert").session(session).content(body)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON));
        System.out.println("????????? : " + resultActions.andReturn().getResponse().getContentAsString());

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }

    // ?????? ???????????? ?????????

    @Test
    public void noticeDetail_test() throws Exception {
        // given
        Integer noticeId = 1;

        // when
        ResultActions resultActions = mvc
                .perform(get("/s/noticeDetailForm/" + noticeId).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }

    // ?????? ???????????? ?????????

    @Test
    public void myCompanyDetail_test() throws Exception {
        // given
        sessionToInitCompany();
        // when
        ResultActions resultActions = mvc
                .perform(get("/s/companyDetail").session(session).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());

    }
}