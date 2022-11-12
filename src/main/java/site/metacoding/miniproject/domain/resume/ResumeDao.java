package site.metacoding.miniproject.domain.resume;

import java.util.List;

import site.metacoding.miniproject.dto.request.resume.ResumeWriteReqDto;
import site.metacoding.miniproject.dto.response.resume.ResumeDeleteRespDto;
import site.metacoding.miniproject.dto.response.resume.ResumeWriteRespDto;

public interface ResumeDao {

	public void save(ResumeWriteReqDto resumeWriteDto);

	public void insert(Resume resume);

	public Resume findById(Integer resumeId);

	public List<Resume> findAll();

	public void update(ResumeWriteReqDto resumeWriteDto); // dto생각

	public void deleteById(Integer resumeId);

	public List<Resume> findByPersonId(Integer personId);

	public ResumeWriteRespDto resumeWriteResult(Integer personId);
	
	public ResumeDeleteRespDto resumeDeleteResult(Integer resumeId);
}
