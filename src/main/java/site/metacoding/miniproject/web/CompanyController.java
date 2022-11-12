package site.metacoding.miniproject.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.miniproject.domain.company.Company;
import site.metacoding.miniproject.domain.need_skill.NeedSkillDao;
import site.metacoding.miniproject.domain.notice.NoticeDao;
import site.metacoding.miniproject.domain.user.User;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.company.CompanyInsertReqDto;
import site.metacoding.miniproject.dto.request.company.CompanyJoinReqDto;
import site.metacoding.miniproject.dto.request.company.CompanyMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.request.notice.NoticeInsertReqDto;
import site.metacoding.miniproject.dto.response.CMRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyDetailRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyInsertRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyIntroductionRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyJoinRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyMyPageRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyMyPageUpdateRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyRecommendRespDto;
import site.metacoding.miniproject.dto.response.notice.NoticeDetailRespDto;
import site.metacoding.miniproject.dto.response.notice.NoticeInsertRespDto;
import site.metacoding.miniproject.dto.response.notice.NoticeRespDto;
import site.metacoding.miniproject.dto.response.recommend.RecommendDetailRespDto;
import site.metacoding.miniproject.dto.response.subscribe.SubscribeDeleteRespDto;
import site.metacoding.miniproject.dto.response.subscribe.SubscribeRespDto;
import site.metacoding.miniproject.service.CompanyService;
import site.metacoding.miniproject.service.PersonService;
import site.metacoding.miniproject.service.UserService;

@RequiredArgsConstructor
@RestController
public class CompanyController {

  private final HttpSession session;
  private final CompanyService companyService;
  private final UserService userService;
  private final PersonService personService;
  private final NoticeDao noticeDao;
  private final NeedSkillDao needSkillDao;

  // 기업회원가입
  @PostMapping("/joinCompany")
  public CMRespDto<?> joinCompany(
      @RequestBody CompanyJoinReqDto companyJoinDto) {
    User userPS = userService.유저네임으로유저찾기(
        companyJoinDto.getUsername());
    if (userPS != null) {
      return new CMRespDto<>(-1, "회원가입 실패", null);
    }
    CompanyJoinRespDto companyJoinRespDto = companyService.기업회원가입(
        companyJoinDto);
    return new CMRespDto<>(1, "회원가입 성공", companyJoinRespDto);
  }

  // 기업 회원가입 페이지
  @GetMapping("/companyJoinForm")
  public CMRespDto<?> companyJoinForm(Model model) {
    return new CMRespDto<>(1, "기업 회원가입 페이지 불러오기 성공", null);
  }

  // 기업추천 리스트 페이지
  @GetMapping("/recommendListFrom")
  public CMRespDto<?> recommendListFrom(Model model) {
    List<CompanyRecommendRespDto> companyRecommendDto = companyService.기업추천리스트보기();
    return new CMRespDto<>(1, "기업추천 리스트", companyRecommendDto);
  }

  // 기업 마이페이지
  @GetMapping("/s/companyMypageForm")
  public CMRespDto<?> companyMyPageForm(Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    CompanyMyPageRespDto companyMyPageDto = companyService.기업마이페이지정보보기(
        userPS.getUserId());
    return new CMRespDto<>(
        1,
        "기업마이 페이지 불러오기 성공",
        companyMyPageDto);
  }

  // 기업 마이페이지 수정하기
  @PutMapping("/s/companyMypageUpdate")
  public CMRespDto<?> updateToCompany(
      @RequestBody CompanyMyPageUpdateReqDto companyMyPageUpdateReqDto) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    companyMyPageUpdateReqDto.setUserId(userPS.getUserId());
    CompanyMyPageUpdateRespDto companyMyPageUpdateRespDto = companyService.기업회원정보수정(
        companyMyPageUpdateReqDto);
    return new CMRespDto<>(
        1,
        "기업회원정보수정 성공",
        companyMyPageUpdateRespDto);
  }

  @GetMapping("/s/companyInsertWriteForm")
  public CMRespDto<?> companyInsertForm(Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    CompanyIntroductionRespDto companyPS2 = companyService.기업이력가져오기(
        userPS.getUserId());
    return new CMRespDto<>(1, "기업소개등록 페이지 불러오기", companyPS2);
  }

  @PostMapping(value = "/company/companyInsert/{companyId}")
  public CMRespDto<?> create(
      @RequestPart("file") MultipartFile file,
      @PathVariable Integer companyId,
      @RequestPart("companyInsertReqDto") CompanyInsertReqDto companyInsertReqDto) throws Exception {
    int pos = file.getOriginalFilename().lastIndexOf(".");
    String extension = file.getOriginalFilename().substring(pos + 1);
    String filePath = "C:\\temp\\img\\";
    String imgSaveName = UUID.randomUUID().toString();
    String imgName = imgSaveName + "." + extension;

    File makeFileFolder = new File(filePath);
    if (!makeFileFolder.exists()) {
      if (!makeFileFolder.mkdir()) {
        throw new Exception("File.mkdir():Fail.");
      }
    }

    File dest = new File(filePath, imgName);
    try {
      Files.copy(file.getInputStream(), dest.toPath());
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("사진저장");
    }
    companyInsertReqDto.setPhoto(imgName);

    CompanyInsertRespDto companyInsertRespDto = companyService.기업이력등록하기(companyId, companyInsertReqDto);
    return new CMRespDto<>(1, "업로드 성공", companyInsertRespDto);
  }

  @GetMapping("/matchingListFrom")
  public CMRespDto<?> skillCompanyMatching(Model model) {
    List<CompanyRecommendRespDto> companyRecommendDto = companyService.기업추천리스트보기();
    return new CMRespDto<>(
        1,
        "기술별 기업 매칭 페이지 불러오기 성공",
        companyRecommendDto);
  }

  @PostMapping("/skillCompanyMatchingList/needSkill")
  public CMRespDto<List<CompanyRecommendRespDto>> skillCompanyMatchingList(
      @RequestBody List<String> skillList,
      Model model) {
    List<CompanyRecommendRespDto> CompanyRecommendDtoList = companyService.NoticeId로공고불러오기(
        skillList);
    return new CMRespDto<List<CompanyRecommendRespDto>>(
        1,
        "기술별 기업불러오기 성공",
        CompanyRecommendDtoList);
  }

  @GetMapping("/s/subscribeManageForm")
  public CMRespDto<?> subscribeManage(Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    List<SubscribeRespDto> subscribeDtoList = companyService.구독목록불러오기(
        userPS.getUserId());
    return new CMRespDto<>(
        1,
        "구독 관리 페이지 불러오기 성공",
        subscribeDtoList);
  }

  @DeleteMapping("/s/deleteSubscribe/{subjectId}")
  public CMRespDto<?> deleteSubscribe(@PathVariable Integer subjectId) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    SubscribeDeleteRespDto subscribeDeleteRespDto = companyService.구독취소(userPS.getUserId(),
        subjectId);
    return new CMRespDto<>(1, "구독 취소", subscribeDeleteRespDto);
  }

  @GetMapping("/companyDetailForm/{companyId}")
  public CMRespDto<?> companyDetail(@PathVariable Integer companyId, Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    CompanyDetailRespDto companyDetailRespDto = companyService.기업상세보기불러오기(companyId, userPS);
    return new CMRespDto<>(1, "기업 상세보기 페이지 불러오기 완료", companyDetailRespDto);
  }

  @GetMapping("/s/subscribe/{subjectId}")
  public CMRespDto<?> companySubscribe(@PathVariable Integer subjectId, Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    Integer subscribeId = companyService.구독Id불러오기(userPS.getUserId(), subjectId);
    if (subscribeId == null) {
      companyService.구독하기(userPS.getUserId(), subjectId);
      subscribeId = companyService.구독Id불러오기(userPS.getUserId(), subjectId);
      return new CMRespDto<Integer>(1, "구독 완료", subscribeId);
    }
    SubscribeDeleteRespDto subscribeDeleteRespDto = companyService.구독취소(userPS.getUserId(), subjectId);
    return new CMRespDto<>(1, "구독 취소 완료", subscribeDeleteRespDto);
  }

  @GetMapping("/s/recommend/{subjectId}")
  public CMRespDto<?> companyRecommend(@PathVariable Integer subjectId) {
    SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
    RecommendDetailRespDto recommendDetail = companyService.기업추천불러오기(principal.getUserId(), subjectId);
    if (recommendDetail.getRecommendId() == null) {
      companyService.기업추천하기(principal.getUserId(), subjectId);
      recommendDetail = companyService.기업추천불러오기(principal.getUserId(), subjectId);
      return new CMRespDto<>(1, "추천 완료", recommendDetail);
    }
    companyService.기업추천취소(recommendDetail.getRecommendId());
    recommendDetail = companyService.기업추천불러오기(principal.getUserId(), subjectId);
    return new CMRespDto<>(1, "추천 취소 완료", recommendDetail);
  }

  @GetMapping("/s/noticeLoadForm")
  public CMRespDto<?> noticeLoad(Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    List<NoticeRespDto> noticeRespDtoList = companyService.유저아이디로공고불러오기(userPS.getUserId());
    return new CMRespDto<>(1, "등록 공고 보기 페이지 불러오기 완료", noticeRespDtoList);
  }

  @GetMapping("/s/noticeWriteForm")
  public CMRespDto<?> noticeWrite(Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    Company company = companyService.유저아이디로찾기(userPS.getUserId());
    return new CMRespDto<>(1, "공고 등록하기 페이지 불러오기 완료", company);
  }

  @PostMapping("/s/noticeInsert")
  public CMRespDto<?> noticeInsert(@RequestBody NoticeInsertReqDto noticeInsertDto) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    NoticeInsertRespDto noticeInsertRespDto = companyService.공고등록하기(noticeInsertDto, userPS);
    return new CMRespDto<>(1, "공고 등록 완료", noticeInsertRespDto);
  }

  @GetMapping("/s/noticeDetailForm/{noticeId}")
  public CMRespDto<?> noticeDetail(@PathVariable Integer noticeId, Model model) {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
    NoticeDetailRespDto noticeDetailRespDto = personService.공고하나불러오기(noticeId, userPS);
    return new CMRespDto<>(1, "공고 상세보기 페이지 불러오기 완료", noticeDetailRespDto);
  }

  @GetMapping("/s/companyDetail")
  public String myCompanyDetail(Model model) {
    SessionUserDto user = (SessionUserDto) session.getAttribute("principal");
    Company company = companyService.유저아이디로찾기(user.getUserId());
    return "redirect:/company/companyDetail/" + company.getCompanyId();
  }
}
