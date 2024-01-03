package com.theZ.dotoring.common;

public enum MessageCode {

    REQUIRE_ACCESS("9003","권한이 없습니다."),
    EXPIRED_ACCESS_TOKEN("9001","만료된 AccessToken입니다. 재발행 요청을 해주세요."),
    EXPIRED_REFRESH_TOKEN("9002","만료된 RefreshToken입니다. 다시 로그인을 해주세요."),

    INVALID_ACCESS_TOKEN("9000","잘못된 AccessToken입니다."),
    INVALID_REFRESH_TOKEN("9004","잘못된 RefreshToken입니다. 다시 로그인을 해주세요."),
    NOT_ALLOWED_FILE_EXT("4003","파일 확장명은 pdf,img만 가능합니다."),
    FILE_NOT_INPUT_OUTPUT("4004","파일 입출력 오류입니다."),
    FILE_SAVE_FAIL("4005","파일 저장에 실패하였습니다."),
    FIlE_NOT_FOUND("4006","서버로 파일 전송에 실패하여, 파일이 존재하지 않습니다."),

    INPUT_NOT_FOUND("4747", "아이디와 비밀번호가 제대로 전달되지 않았습니다."),

    ALREADY_EXISTS_EMAIL("4133","이미 등록된 이메일입니다. 아이디 찾기를 해주세요!"),

    DUPLICATED_NICKNAME("4009","중복된 닉네임이 존재합니다."),
    DUPLICATED_LOGIN_ID("4010","중복된 아이디가 존재합니다."),
    DUPLICATED_VALUE("4023","중복된 값이 존재합니다."),

    EMAIL_NOT_FOUND("4323","등록되지 않은 이메일입니다."),
    IMAGE_NOT_FOUND("4333","기본 프로필이 저장되어 있지 않습니다."),

    NOT_MATCH_CODE("4077","해당 이메일과 인증코드가 일치하지 않습니다."),
    WRONG_REQUEST("4444","잘못된 요청입니다."),

    VALIDATION_FAIL("4011","유효성 검증 실패"),

    LIMIT_FILE_SIZE("4012","파일 사이즈는 최대 20MB 입니다."),
    MEMBER_NOT_FOUND("4938","회원이 존재하지 않습니다."),

    ROOM_NOT_FOUND("4013","채팅방이 존재하지 않습니다."),

    LETTER_NOT_FOUND("4014","쪽지가 존재하지 않습니다."),

    NOT_AUTHOR_NOTIFICATION("4443","지원 공고 작성자만 수정할 수 있습니다."),

    DUPLICATE_PARTICIPATION_NOTIFICATION("4444","이미 지원한 공고입니다."),

    NOTIFICATION_NOT_FOUND("4445","지원 공고가 존재하지 않습니다.");



    private final String code;
    private final String value;

    MessageCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }


}

