package site.metacoding.miniproject.dto.response.company;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.domain.company.Company;
import site.metacoding.miniproject.dto.response.notice.NoticeRespDto;
import site.metacoding.miniproject.dto.response.recommend.RecommendDetailRespDto;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDetailRespDto {

    private Integer userId;
    private String companyName;
    private String companyEmail;
    private String companyPhone;
    private String tech;
    private String address;
    private String history;
    private String introduction;
    private String photo;
    private String companyGoal;
    private String ceoName;
    private Integer recommendCount;
    private Integer recommendId;
    private Integer subscribeId;
    List<NoticeRespDto> noticeRespDtoList;

    public void InsertCompany(Company company) {
        this.userId = company.getUserId();
        this.companyName = company.getCompanyName();
        this.companyEmail = company.getCompanyEmail();
        this.companyPhone = company.getCompanyPhone();
        this.tech = company.getTech();
        this.address = company.getAddress();
        this.history = company.getHistory();
        this.introduction = company.getIntroduction();
        this.photo = company.getPhoto();
        this.companyGoal = company.getCompanyGoal();
        this.ceoName = company.getCeoName();
    }

    public void insertRecommend(RecommendDetailRespDto recommendDetailRespDto) {
        this.recommendCount = recommendDetailRespDto.getRecommendCount();
        this.recommendId = recommendDetailRespDto.getRecommendId();
    }

}
