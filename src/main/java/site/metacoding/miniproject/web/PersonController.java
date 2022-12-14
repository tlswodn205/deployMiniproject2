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

	// ???????????? ??????
	@PostMapping("/joinPerson")
	public @ResponseBody CMRespDto<?> joinPerson(@RequestBody PersonJoinReqDto personJoinReqDto) {
		User userPS = userService.??????????????????????????????(personJoinReqDto.getUsername());
		if (userPS != null) {
			return new CMRespDto<>(-1, "???????????? ??????", null);
		}
		PersonJoinRespDto personJoinRespDto = personService.????????????(personJoinReqDto);
		return new CMRespDto<>(1, "???????????? ??????", personJoinRespDto);

	}

	// ?????? ???????????? ?????????
	@GetMapping("/personJoinForm")
	public CMRespDto<?> perseonJoinForm(Model model) {
		return new CMRespDto<>(1, "?????? ?????? ?????? ????????? ???????????? ??????", null);

	}

	// ????????? ??????
	@PostMapping("/s/resumeSave")
	public CMRespDto<?> resumeWrite(@RequestBody ResumeWriteReqDto resumeWriteDto) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		ResumeWriteRespDto resumeWriteRespDto = personService.???????????????(resumeWriteDto, userPS.getUserId());
		return new CMRespDto<>(1, "????????? ?????? ??????", resumeWriteRespDto);
	}

	@GetMapping("/s/personRecommend/{subjectId}")
	public @ResponseBody CMRespDto<RecommendDetailRespDto> personRecommend(@PathVariable Integer subjectId) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		RecommendDetailRespDto recommendDetailDto = personService.???????????????????????????(userPS.getUserId(), subjectId);
		if (recommendDetailDto.getRecommendId() == null) {
			personService.?????????????????????(userPS.getUserId(), subjectId);
			recommendDetailDto = personService.???????????????????????????(userPS.getUserId(), subjectId);
			return new CMRespDto<RecommendDetailRespDto>(1, "?????? ??????", recommendDetailDto);
		}
		personService.?????????????????????(recommendDetailDto.getRecommendId());
		recommendDetailDto = personService.???????????????????????????(userPS.getUserId(), subjectId);
		return new CMRespDto<RecommendDetailRespDto>(1, "?????? ?????? ??????", recommendDetailDto);
	}

	// ????????? ???????????? ?????????
	@GetMapping("/s/personDetailForm/{personId}")
	public CMRespDto<?> personDetailForm(@PathVariable Integer personId, Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		PersonInfoRespDto personInfoRespDto = personService.??????????????????(personId, userPS);
		return new CMRespDto<>(1, "????????? ???????????? ????????? ???????????? ??????", personInfoRespDto);
	}

	@GetMapping("/personRecommendList")
	public CMRespDto<?> personRecommendList(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<PersonRecommendListRespDto> personRecommendListDto = personService.??????????????????????????????(userPS);
		return new CMRespDto<>(1, "????????? ?????? ????????? ?????? ???????????? ??????", personRecommendListDto);
	}

	// ????????? ?????? ?????????
	@GetMapping("/personRecommendListForm")
	public CMRespDto<?> PersonRecommendListFrom(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<PersonRecommendListRespDto> personRecommendListDto = personService.??????????????????????????????(userPS);
		return new CMRespDto<>(1, "????????? ?????? ????????? ????????? ???????????? ??????", personRecommendListDto);
	}

	@PostMapping("/personSkillPersonMatching/personSkill")
	public CMRespDto<List<InterestPersonRespDto>> interestPersonSkillList(@RequestBody List<String> skillList,
			Model model) {
		List<InterestPersonRespDto> interestPersonDto = personService.????????????????????????(skillList);
		return new CMRespDto<>(1, "????????? ?????? ????????? ???????????? ??????", interestPersonDto);
	}

	// ????????? ???????????????
	@GetMapping("/s/personMypageForm")
	public CMRespDto<?> PersonMypageForm(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		PersonMyPageRespDto personMyPageRespDto = personService.????????????????????????????????????(userPS.getUserId());
		return new CMRespDto<>(1, "????????? ?????? ????????? ???????????? ??????", personMyPageRespDto);
	}

	// ????????? ??????????????? ????????????
	@PutMapping("/s/personMypageUpdate")
	public CMRespDto<?> updateToPerson(@RequestBody PersonMyPageUpdateReqDto personMyPageUpdateDto) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		personMyPageUpdateDto.setUserId(userPS.getUserId());
		PersonMyPageUpdateRespDto personMyPageUpdateRespDto = personService.???????????????????????????(personMyPageUpdateDto);
		return new CMRespDto<>(1, "??????????????????????????? ??????", personMyPageUpdateRespDto);
	}

	@DeleteMapping("/s/deleteResume/{resumeId}")
	public CMRespDto<?> resumeDelete(@PathVariable Integer resumeId) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		ResumeDeleteRespDto resumeDeleteRespDto = personService.?????????????????????(resumeId, userPS.getUserId());
		return new CMRespDto<>(1, "????????? ?????? ??????", resumeDeleteRespDto);
	}

	@GetMapping("/s/resumeManageForm")
	public CMRespDto<?> personResumeManage(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<Resume> resumeList = personService.???????????????????????????(userPS.getUserId());
		return new CMRespDto<>(1, "????????? ?????? ????????? ???????????? ??????", resumeList);
	}

	@GetMapping("/noticePerApplierForm/{noticeId}")
	public CMRespDto<?> findNoticePerApplier(@PathVariable Integer noticeId, Model model) {
		FindNoticePerApplierRespDto findNoticePerApplierRespDto = personService.???????????????????????????(noticeId);
		return new CMRespDto<>(1, "????????? ????????? ????????? ????????? ???????????? ??????", findNoticePerApplierRespDto);
	}

	@GetMapping("/s/noticeClose/{noticeId}")
	public CMRespDto<?> closeNotice(@PathVariable Integer noticeId) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		CloseNoticeRespDto closeNoticeRespDto = personService.??????????????????(noticeId, userPS.getUserId());
		return new CMRespDto<>(1, "?????? ??????", closeNoticeRespDto);
	}

	@GetMapping("/s/personApplyForm")
	public CMRespDto<?> personApply(Model model) {
		SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal");
		List<NoticeApplyRespDto> noticeApplyDtoList = personService.??????????????????(userPS.getUserId());
		return new CMRespDto<>(1, "?????? ?????? ????????? ???????????? ??????", noticeApplyDtoList);
	}

	@PostMapping("/s/submitResume")
	public CMRespDto<?> submitResume(@RequestBody SubmitResumeReqDto submitResumeReqDto) {
		SubmitResumeRespDto submitResumeRespDto = companyService.?????????????????????(submitResumeReqDto);
		return new CMRespDto<>(1, "????????? ?????? ??????", submitResumeRespDto);
	}
}