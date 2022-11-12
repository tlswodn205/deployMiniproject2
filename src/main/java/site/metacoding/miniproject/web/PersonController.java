package site.metacoding.miniproject.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.miniproject.domain.resume.Resume;
import site.metacoding.miniproject.domain.user.User;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.person.PersonJoinReqDto;
import site.metacoding.miniproject.dto.request.person.PersonMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.request.resume.ResumeWriteReqDto;
import site.metacoding.miniproject.dto.request.resume.SubmitResumeReqDto;
import site.metacoding.miniproject.dto.response.CMRespDto;
import site.metacoding.miniproject.dto.response.notice.CloseNoticeRespDto;
import site.metacoding.miniproject.dto.response.notice.FindNoticePerApplierRespDto;
import site.metacoding.miniproject.dto.response.notice.NoticeApplyRespDto;
import site.metacoding.miniproject.dto.response.person.InterestPersonRespDto;
import site.metacoding.miniproject.dto.response.person.PersonInfoRespDto;
import site.metacoding.miniproject.dto.response.person.PersonJoinRespDto;
import site.metacoding.miniproject.dto.response.person.PersonMyPageRespDto;
import site.metacoding.miniproject.dto.response.person.PersonMyPageUpdateRespDto;
import site.metacoding.miniproject.dto.response.person.PersonRecommendListRespDto;
import site.metacoding.miniproject.dto.response.recommend.RecommendDetailRespDto;
import site.metacoding.miniproject.dto.response.resume.SubmitResumeRespDto;
import site.metacoding.miniproject.dto.response.resume.ResumeDeleteRespDto;
import site.metacoding.miniproject.dto.response.resume.ResumeWriteRespDto;
import site.metacoding.miniproject.service.CompanyService;
import site.metacoding.miniproject.service.PersonService;
import site.metacoding.miniproject.service.UserService;

@RequiredArgsConstructor
@RestController
public class PersonController {
	private final HttpSession session;
	private final PersonService personService;
	private final UserService userService;
	private final CompanyService companyService;

	// 회원가입 응답
	@PostMapping("/joinPerson")
	public @ResponseBody CMRespDto<?> joinPerson(@RequestBody PersonJoinReqDto personJoinReqDto) {
		User userPS = userService.유저네임으로유저찾기(personJoinReqDto.getUsername());
		if (userPS != null) {
			return new CMRespDto<>(-1, "회원가입 실패", null);
		}
		PersonJoinRespDto personJoinRespDto = personService.회원가입(personJoinReqDto);
		return new CMRespDto<>(1, "회원가입 성공", personJoinRespDto);

	}

	// 개인 회원가입 페이지
	@GetMapping("/personJoinForm")
	public CMRespDto<?> perseonJoinForm(Model model) {
		return new CMRespDto<>(1, "개인 회원 가입 페이지 불러오기 성공", null);

	}

	// 이력서 응답
	@PostMapping("/s/resumeSave")
	public CMRespDto<?> resumeWrite(@RequestBody ResumeWriteReqDto resumeWriteDto) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		ResumeWriteRespDto resumeWriteRespDto = personService.이력서등록(resumeWriteDto, userPS.getUserId());
		return new CMRespDto<>(1, "이력서 등록 성공", resumeWriteRespDto);
	}

	@GetMapping("/s/personRecommend/{subjectId}")
	public @ResponseBody CMRespDto<RecommendDetailRespDto> personRecommend(@PathVariable Integer subjectId) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		RecommendDetailRespDto recommendDetailDto = personService.구직자추천불러오기(userPS.getUserId(), subjectId);
		if (recommendDetailDto.getRecommendId() == null) {
			personService.구직자추천하기(userPS.getUserId(), subjectId);
			recommendDetailDto = personService.구직자추천불러오기(userPS.getUserId(), subjectId);
			return new CMRespDto<RecommendDetailRespDto>(1, "추천 완료", recommendDetailDto);
		}
		personService.구직자추천취소(recommendDetailDto.getRecommendId());
		recommendDetailDto = personService.구직자추천불러오기(userPS.getUserId(), subjectId);
		return new CMRespDto<RecommendDetailRespDto>(1, "추천 취소 완료", recommendDetailDto);
	}

	// 구직자 상세보기 페이지
	@GetMapping("/s/personDetailForm/{personId}")
	public CMRespDto<?> personDetailForm(@PathVariable Integer personId, Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		PersonInfoRespDto personInfoRespDto = personService.개인정보보기(personId, userPS);
		return new CMRespDto<>(1, "구직자 상세보기 페이지 불러오기 성공", personInfoRespDto);
	}

	@GetMapping("/personRecommendList")
	public CMRespDto<?> personRecommendList(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<PersonRecommendListRespDto> personRecommendListDto = personService.구직자추천리스트보기(userPS);
		return new CMRespDto<>(1, "구직자 추천 리스트 리셋 불러오기 성공", personRecommendListDto);
	}

	// 구직자 추천 페이지
	@GetMapping("/personRecommendListForm")
	public CMRespDto<?> PersonRecommendListFrom(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<PersonRecommendListRespDto> personRecommendListDto = personService.구직자추천리스트보기(userPS);
		return new CMRespDto<>(1, "구직자 추천 리스트 페이지 불러오기 성공", personRecommendListDto);
	}

	@PostMapping("/personSkillPersonMatching/personSkill")
	public CMRespDto<List<InterestPersonRespDto>> interestPersonSkillList(@RequestBody List<String> skillList,
			Model model) {
		List<InterestPersonRespDto> interestPersonDto = personService.관심구직자리스트(skillList);
		return new CMRespDto<>(1, "기술별 관심 구직자 불러오기 완료", interestPersonDto);
	}

	// 구직자 마이페이지
	@GetMapping("/s/personMypageForm")
	public CMRespDto<?> PersonMypageForm(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		PersonMyPageRespDto personMyPageRespDto = personService.구직자마이페이지정보보기(userPS.getUserId());
		return new CMRespDto<>(1, "구직자 마이 페이지 불러오기 성공", personMyPageRespDto);
	}

	// 구직자 마이페이지 수정하기
	@PutMapping("/s/personMypageUpdate")
	public CMRespDto<?> updateToPerson(@RequestBody PersonMyPageUpdateReqDto personMyPageUpdateDto) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		personMyPageUpdateDto.setUserId(userPS.getUserId());
		PersonMyPageUpdateRespDto personMyPageUpdateRespDto = personService.구직자회원정보수정(personMyPageUpdateDto);
		return new CMRespDto<>(1, "구직자회원정보수정 성공", personMyPageUpdateRespDto);
	}

	@DeleteMapping("/s/deleteResume/{resumeId}")
	public CMRespDto<?> resumeDelete(@PathVariable Integer resumeId) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		ResumeDeleteRespDto resumeDeleteRespDto = personService.이력서삭제하기(resumeId, userPS.getUserId());
		return new CMRespDto<>(1, "이력서 삭제 성공", resumeDeleteRespDto);
	}

	@GetMapping("/s/resumeManageForm")
	public CMRespDto<?> personResumeManage(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<Resume> resumeList = personService.이력서목록가져오기(userPS.getUserId());
		return new CMRespDto<>(1, "이력서 관리 페이지 불러오기 성공", resumeList);
	}

	@GetMapping("/noticePerApplierForm/{noticeId}")
	public CMRespDto<?> findNoticePerApplier(@PathVariable Integer noticeId, Model model) {
		FindNoticePerApplierRespDto findNoticePerApplierRespDto = personService.공고별구직자리스트(noticeId);
		return new CMRespDto<>(1, "공고별 지원자 리스트 페이지 불러오기 성공", findNoticePerApplierRespDto);
	}

	@GetMapping("/s/noticeClose/{noticeId}")
	public CMRespDto<?> closeNotice(@PathVariable Integer noticeId) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		CloseNoticeRespDto closeNoticeRespDto = personService.공고마감하기(noticeId, userPS.getUserId());
		return new CMRespDto<>(1, "마감 완료", closeNoticeRespDto);
	}

	@GetMapping("/s/personApplyForm")
	public CMRespDto<?> personApply(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<NoticeApplyRespDto> noticeApplyDtoList = personService.지원공고목록(userPS.getUserId());
		return new CMRespDto<>(1, "공고 지원 리스트 불러오기 성공", noticeApplyDtoList);
	}

	@PostMapping("/s/submitResume")
	public CMRespDto<?> submitResume(@RequestBody SubmitResumeReqDto submitResumeReqDto) {
		SubmitResumeRespDto submitResumeRespDto = companyService.이력서제출하기(submitResumeReqDto);
		return new CMRespDto<>(1, "이력서 제출 완료", submitResumeRespDto);
	}
}