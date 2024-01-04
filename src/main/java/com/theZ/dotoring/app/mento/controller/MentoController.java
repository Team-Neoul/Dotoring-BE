package com.theZ.dotoring.app.mento.controller;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.mento.dto.*;
import com.theZ.dotoring.app.mento.handler.*;
import com.theZ.dotoring.app.mento.service.MentoService;
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
public class MentoController {

    private final SaveMentoHandler saveMentoHandler;
    private final FindAllMentoHandler findAllMentoHandler;
    private final FindMentoHandler findMentoHandler;
    private final FindMyMentoHandler findMyMentoHandler;
    private final UpdateMentoHandler updateMentoHandler;
    private final MentoService mentoService;

    @ApiOperation(value = "멘토 최종 회원가입때 사용")
    @PostMapping(value = "/signup-mento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMento(@ModelAttribute @Valid SaveMentoRqDTO saveMentoRqDTO) throws IOException {
        saveMentoHandler.execute(saveMentoRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 회원가입 시 닉네임 중복 검사할 때 사용", notes = "이름은 3자 이상 8자 이하로 입력해주세요.")
    @PostMapping("/mento/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMentoNickname(@RequestBody @Valid ValidateMentoNicknameRqDTO validateMentoNicknameRqDTO){
        mentoService.validateNickname(validateMentoNicknameRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 홈에서 도토링 추천 방식에따른 멘토들 추천")
    @GetMapping("/mento")
    public ApiResponse<ApiResponse.CustomBody<Slice<FindAllMentoRespDTO>>> findAllMento(
            @RequestParam(required = false) Long lastMentoId, @RequestParam(defaultValue = "10") Integer size, @AuthenticationPrincipal MemberDetails memberDetails){

        return ApiResponseGenerator.success(findAllMentoHandler.execute(lastMentoId, size, memberDetails.getId()),HttpStatus.OK);
    }

    @GetMapping("/wait-mento")
    public ApiResponse<ApiResponse.CustomBody<Page<FindWaitMentoRespDTO>>> findWaitMento(
        @PageableDefault(size = 20) Pageable pageable
    ){
        return ApiResponseGenerator.success(mentoService.findWaitMentos(pageable),HttpStatus.OK);
    }

    @PatchMapping("/mento/status")
    public ApiResponse<ApiResponse.CustomBody<Void>> approveMento(@RequestBody UpdateMentoStatusRqDTO approveWaitMentosRqDTO) {
        mentoService.updateActive(approveWaitMentosRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 홈에서 해당 멘토 상세 조회시 사용")
    @GetMapping("/mento/{id}")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> findMentoById(@PathVariable Long id){
        FindMentoByIdRespDTO findMentoByIdRespDTO = findMentoHandler.execute(id);
        return ApiResponseGenerator.success(findMentoByIdRespDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 마이페이지 정보 조회")
    @GetMapping("/mento/my-page")
    public ApiResponse<ApiResponse.CustomBody<FindMyMentoRespDTO>> findMentoMyPage(@AuthenticationPrincipal MemberDetails memberDetails) {
        FindMyMentoRespDTO findMyMentoRespDTO = findMyMentoHandler.execute(memberDetails.getId());
        return ApiResponseGenerator.success(findMyMentoRespDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 마이페이지 정보 수정")
    @PutMapping("/mento/my-page")
    public ApiResponse<ApiResponse.CustomBody<Void>> updateMento(@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute UpdateMentoRqDTO updateMentoRqDTO) throws IOException {
        updateMentoHandler.execute(memberDetails.getId(), updateMentoRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 마이페이지 태그 수정")
    @PatchMapping("/mento/tags")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateTags(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody @Valid UpdateTagsRqDTO updateTagsRqDTO) {
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateTags(memberDetails.getId(), updateTagsRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 마이페이지 멘토링 수행 방법 수정")
    @PatchMapping("/mento/mentoring-system")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoringSystem(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody @Valid UpdateMentoringSystemRqDTO updateMentoringSystemRqDTO) {
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateMentoringSystem(memberDetails.getId(), updateMentoringSystemRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO, HttpStatus.OK);
    }


}
