package site.metacoding.miniproject.domain.person_skill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.miniproject.dto.response.person.PersonInfoRespDto;

public interface PersonSkillDao {
	public void insert(@Param("personId") Integer personId, @Param("skill") String skill);

	public PersonSkill findById(Integer personSkillId);

	public List<PersonSkill> findAll();

	public void update(PersonSkill personSkill); // dto생각

	public void deleteById(Integer personSkillId);

	public List<Integer> findBySkill(String skill);

	public Integer findBySkillAndPersonId(@Param("skill") String skill, @Param("personId") Integer personId);

	public List<String> findByPersonId(Integer personId);
}
