package site.metacoding.miniproject.dto.response.resume;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.miniproject.domain.person.Person;
import site.metacoding.miniproject.domain.resume.Resume;

@Setter
@Getter
@AllArgsConstructor
public class ResumeDetailFormRespDto {
	private Integer resumeId;
	private Integer personId;
	private String resumeTitle;
	private String photo;
	private String myCloud;
	private String introduction;

	private Integer userId;
	private String personName;
	private String personEmail;
	private String degree;
	private String address;
	private Integer career;
	private List<String> personSkillList;

	public ResumeDetailFormRespDto(Resume resume) {
		this.resumeId = resume.getResumeId();
		this.personId = resume.getPersonId();
		this.resumeTitle = resume.getResumeTitle();
		this.photo = resume.getPhoto();
		this.myCloud = resume.getMyCloud();
		this.introduction = resume.getIntroduction();
	}

	public void insertPerson(Person person, List<String> personSkillLists) {
		this.userId = person.getUserId();
		this.personName = person.getPersonName();
		this.personEmail = person.getPersonEmail();
		this.degree = person.getDegree();
		this.address = person.getAddress();
		this.career = person.getCareer();
		this.personSkillList = personSkillLists;
	}
}
