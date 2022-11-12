package site.metacoding.miniproject.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.metacoding.miniproject.dto.SessionUserDto;
import site.metacoding.miniproject.dto.request.resume.ResumeWriteReqDto;
import site.metacoding.miniproject.dto.response.CMRespDto;
import site.metacoding.miniproject.dto.response.resume.ResumeDetailFormRespDto;
import site.metacoding.miniproject.dto.response.resume.ResumeFormRespDto;
import site.metacoding.miniproject.service.PersonService;
import site.metacoding.miniproject.service.ResumeService;

@RequiredArgsConstructor
@RestController
public class ResumeController {

  private final HttpSession session;
  private final PersonService personService;
  private final ResumeService resumeService;

  // 이력서 등록 페이지
  @GetMapping("/s/resumeWriteForm")
  public CMRespDto<?> resumeForm() {
    SessionUserDto userPS = (SessionUserDto) session.getAttribute("principal"); // 로그인 정보 가져오기
    ResumeFormRespDto resumeFormRespDto = personService.이력서내용가져오기(userPS.getUserId()); // 이력서내용가져오기
    return new CMRespDto<>(1, "이력서 쓰기 페이지 불러오기 성공", resumeFormRespDto);
  }

  @PostMapping(value = "/resume/save")
  public CMRespDto<?> create(
      @RequestPart("file") MultipartFile file,
      @RequestPart("resumeWriteDto") ResumeWriteReqDto resumeWriteDto) throws Exception {
    int pos = file.getOriginalFilename().lastIndexOf(".");
    String extension = file.getOriginalFilename().substring(pos + 1);
    String filePath = "C:\\temp\\img\\";
    String imgSaveName = UUID.randomUUID().toString();
    String imgName = imgSaveName + "." + extension;

    File makeFileFolder = new File(filePath);
    if (!makeFileFolder.exists()) {
      if (!makeFileFolder.mkdir()) {
        throw new Exception("File.mkdir():Fail.");
      }
    }

    File dest = new File(filePath, imgName);
    try {
      Files.copy(file.getInputStream(), dest.toPath());
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("사진저장");
    }
    resumeWriteDto.setPhoto(imgName);
    resumeService.이력서등록하기(resumeWriteDto);
    return new CMRespDto<>(1, "업로드 성공", imgName);
  }

  @PostMapping(value = "/resume/update")
  public CMRespDto<?> update(
      @RequestPart("file") MultipartFile file,
      @RequestPart("resumeWriteDto") ResumeWriteReqDto resumeWriteDto) throws Exception {
    int pos = file.getOriginalFilename().lastIndexOf(".");
    String extension = file.getOriginalFilename().substring(pos + 1);
    String filePath = "C:\\temp\\img\\";
    String imgSaveName = UUID.randomUUID().toString();
    String imgName = imgSaveName + "." + extension;

    File makeFileFolder = new File(filePath);
    if (!makeFileFolder.exists()) {
      if (!makeFileFolder.mkdir()) {
        throw new Exception("File.mkdir():Fail.");
      }
    }

    File dest = new File(filePath, imgName);
    try {
      Files.copy(file.getInputStream(), dest.toPath());
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("사진저장");
    }
    resumeWriteDto.setPhoto(imgName);
    resumeService.이력서수정하기(resumeWriteDto);
    return new CMRespDto<>(1, "수정 성공", imgName);
  }

  // 이력서 상세보기 페이지
  @GetMapping("/resumeDetailForm/{resumeId}")
  public CMRespDto<?> resumeDetailForm(
      Model model,
      @PathVariable Integer resumeId) {
    ResumeDetailFormRespDto personPS2 = resumeService.이력서상세보기(resumeId);
    return new CMRespDto<>(
        1,
        "이력서 상세보기 페이지 불러오기 성공",
        personPS2);
  }
}
