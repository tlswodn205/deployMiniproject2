package site.metacoding.miniproject.domain.need_skill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NeedSkillDao {
	public void insert(NeedSkill needSkill);

	public NeedSkill findById(Integer needSkillId);

	public List<NeedSkill> findAll();

	public void update(NeedSkill needSkill); // dto생각

	public void deleteById(Integer needSkillId);

	public List<String> findByNoticeId(Integer noticeId);

	public List<Integer> findBySkill(String skill);

	public Integer findBySkillAndNoticeId(@Param("skill") String skill, @Param("noticeId") Integer noticeId);

	public List<String> findByUserId(Integer userId);
}