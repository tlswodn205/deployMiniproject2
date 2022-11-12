package site.metacoding.miniproject.domain.person;

import java.util.List;

import site.metacoding.miniproject.dto.request.person.PersonMyPageReqDto;
import site.metacoding.miniproject.dto.request.person.PersonMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.response.person.PersonInfoRespDto;
import site.metacoding.miniproject.dto.response.person.PersonJoinRespDto;
import site.metacoding.miniproject.dto.response.person.PersonMyPageRespDto;
import site.metacoding.miniproject.dto.response.person.PersonMyPageUpdateRespDto;
import site.metacoding.miniproject.dto.response.person.PersonRecommendListRespDto;

public interface PersonDao {
	public void insert(Person person);

	public Person findById(Integer personId);

	public PersonInfoRespDto personInfo(Integer personId);

	public List<Person> findAll();

	public void update(Person person); // dto생각

	public void deleteById(Integer personId);

	public List<Integer> findByDegree(String degree);

	public List<Integer> findByCareer(Integer career);

	public Integer findToId(Integer userId);

	public List<PersonRecommendListRespDto> findToPersonRecommned();

	public PersonMyPageRespDto findToPersonMyPage(Integer userId);

	public void updateToPerson(PersonMyPageUpdateReqDto personMyPageUpdateDto);

	public PersonMyPageUpdateRespDto personMyPageUpdateResult(Integer userId);
}
