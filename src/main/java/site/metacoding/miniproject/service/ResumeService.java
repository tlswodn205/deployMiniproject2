package site.metacoding.miniproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.miniproject.domain.person.Person;
import site.metacoding.miniproject.domain.person.PersonDao;
import site.metacoding.miniproject.domain.person_skill.PersonSkillDao;
import site.metacoding.miniproject.domain.resume.Resume;
import site.metacoding.miniproject.domain.resume.ResumeDao;
import site.metacoding.miniproject.dto.request.resume.ResumeWriteReqDto;
import site.metacoding.miniproject.dto.response.resume.ResumeDetailFormRespDto;

@RequiredArgsConstructor
@Service
public class ResumeService {
	private final ResumeDao resumeDao;
	private final PersonDao personDao;
	private final PersonSkillDao personSkillDao;

	@Transactional
	public void 이력서등록하기(ResumeWriteReqDto resumeWriteDto) {
		resumeDao.save(resumeWriteDto);
		System.out.println(resumeWriteDto.getResumeTitle());
	}

	@Transactional
	public ResumeDetailFormRespDto 이력서상세보기(Integer resumeId) {
		Resume resume = resumeDao.findById(resumeId);
		ResumeDetailFormRespDto resumeDetailFormDto = new ResumeDetailFormRespDto(resume);
		Integer personId = resume.getPersonId();
		Person person = personDao.findById(personId);
		resumeDetailFormDto.insertPerson(person, personSkillDao.findByPersonId(personId));
		return resumeDetailFormDto;
	}

	@Transactional
	public void 이력서수정하기(ResumeWriteReqDto resumeWriteDto) {
		resumeDao.update(resumeWriteDto);
	}
}