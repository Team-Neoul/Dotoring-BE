package com.theZ.dotoring.app.menti.controller;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.menti.dto.*;
import com.theZ.dotoring.app.menti.handler.*;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.dto.UpdateTagsRqDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentiController {

    private final SaveMentiHandler saveMentiHandler;
    private final FindMentiHandler findMentiHandler;
    private final FindMyMentiHandler findMyMentiHandler;
    private final MentiService mentiService;
    private final FindAllMentiHandler findAllMentiHandler;
    private final UpdateMentiHandler updateMentiHandler;

    @ApiOperation(value = "멘티 최종 회원가입때 사용")
    @PostMapping(value = "/signup-menti", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMenti(@ModelAttribute @Valid SaveMentiRqDTO saveMentiRqDTO) throws IOException {
        saveMentiHandler.execute(saveMentiRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 회원가입 시 닉네임 중복 검사할 때 사용", notes = "이름은 3자 이상 8자 이하로 입력해주세요.")
    @PostMapping("/menti/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMentiNickname(@RequestBody @Valid ValidateMentiNicknameRqDTO validateMentiNicknameRqDTO){
        mentiService.validateNickname(validateMentiNicknameRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 홈에서 해당 멘티 상세 조회시 사용")
    @GetMapping("/menti/{id}")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> findMentiById(@PathVariable Long id){
        FindMentiByIdRespDTO findMentiByIdRespDTO = findMentiHandler.execute(id);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 홈에서 도토링 추천 방식에따른 멘티들 추천")
    @GetMapping("/menti")
    public ApiResponse<ApiResponse.CustomBody<Slice<FindAllMentiRespDTO>>> findAllMenti(
            @RequestParam(required = false) Long lastMentiId, @RequestParam(defaultValue = "10") Integer size, @AuthenticationPrincipal MemberDetails memberDetails){
        return ApiResponseGenerator.success(findAllMentiHandler.execute(lastMentiId, size, memberDetails.getId()),HttpStatus.OK);
    }

    @GetMapping("/wait-menti")
    public ApiResponse<ApiResponse.CustomBody<Page<FindWaitMentiRespDTO>>> findWaitMenti(
            @PageableDefault(size = 20) Pageable pageable
    ){
        return ApiResponseGenerator.success(mentiService.findWaitMentis(pageable),HttpStatus.OK);
    }

    @PatchMapping("/menti/status")
    public ApiResponse<ApiResponse.CustomBody<Void>> approveWaitMentis(@RequestBody UpdateMentiStatusRqDTO updateMentiStatusRqDTO) {
        mentiService.updateActive(updateMentiStatusRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 마이페이지 정보 조회")
    @GetMapping("/menti/my-page")
    public ApiResponse<ApiResponse.CustomBody<FindMyMentiRespDTO>> findMyMenti(@AuthenticationPrincipal MemberDetails memberDetails) {
        FindMyMentiRespDTO findMyMentiRespDTO = findMyMentiHandler.execute(memberDetails.getId());
        return ApiResponseGenerator.success(findMyMentiRespDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 마이페이지 정보 수정")
    @PatchMapping("/menti/my-page")
    public ApiResponse<ApiResponse.CustomBody<Void>> updateMenti(@ModelAttribute @Valid UpdateMentiRqDTO updateMentiRqDTO, @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
        updateMentiHandler.execute(memberDetails.getId(), updateMentiRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 태그 수정")
    @PatchMapping("/menti/tags")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateTags(@RequestBody UpdateTagsRqDTO updateTagsRqDTO, @AuthenticationPrincipal MemberDetails memberDetails) {
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updateTags(memberDetails.getId(), updateTagsRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 마이페이지 선호 멘토링 수정")
    @PatchMapping("/menti/preferred-mentoring")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updatePreferredMentoring(@RequestBody UpdatePreferredMentoringRqDTO updatePreferredMentoringRqDTO, @AuthenticationPrincipal MemberDetails memberDetails) {
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updatePreferredMentoring(memberDetails.getId(), updatePreferredMentoringRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }

}

