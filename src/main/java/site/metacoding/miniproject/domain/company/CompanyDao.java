package site.metacoding.miniproject.domain.company;

import java.util.List;

import site.metacoding.miniproject.dto.request.company.CompanyInsertReqDto;
import site.metacoding.miniproject.dto.request.company.CompanyMyPageUpdateReqDto;
import site.metacoding.miniproject.dto.response.company.CompanyInsertRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyJoinRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyMyPageRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyMyPageUpdateRespDto;
import site.metacoding.miniproject.dto.response.company.CompanyRecommendRespDto;

public interface CompanyDao {
	public void insert(Company company);

	public Company findById(Integer companyId);

	public List<Company> findAll();

	public void update(Company company); // dto생각

	public void deleteById(Integer companyId);

	public List<CompanyRecommendRespDto> findToRecommned();

	public CompanyMyPageRespDto findToCompanyMyPage(Integer userId);

	public void updateToCompany(CompanyMyPageUpdateReqDto companyMyPageUpdateReqDto);

	public void updateCompanyIntroduction(CompanyInsertReqDto companyInsertReqDto);

	public CompanyInsertRespDto companyInfoUpdateResult(Integer companyId);

	public CompanyRecommendRespDto findToNoticeId(Integer noticeId);

	public Company findByUserId(Integer userId);

	public CompanyMyPageUpdateRespDto CompanyMyPageUpdateResult(Integer userId);

	public CompanyJoinRespDto CompanyJoinResult(Integer userId);
}
