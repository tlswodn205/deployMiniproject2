package site.metacoding.miniproject.domain.submit_resume;

import java.util.List;

import site.metacoding.miniproject.dto.request.resume.SubmitResumeReqDto;
import site.metacoding.miniproject.dto.response.resume.SubmitResumeRespDto;

public interface SubmitResumeDao {
    public void insert(SubmitResumeReqDto submitResumeReqDto);

    public SubmitResume findById(Integer submitResumeId);

    public List<SubmitResume> findAll();

    public void update(SubmitResume submitResume); // dto생각

    public void deleteById(Integer submitResumeId);

    public List<SubmitResume> findByNoticeId(Integer noticeId);

    public SubmitResumeRespDto submitResumeResult(SubmitResumeReqDto submitResumeReqDto);
}
