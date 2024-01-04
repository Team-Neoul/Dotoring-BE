package com.theZ.dotoring.app.mento.model;


import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.common.CommonEntity;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.enums.Status;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 멘토 엔티티
 *
 * @author Sonny
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mento extends CommonEntity {

    @Id
    private Long mentoId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberAccountId")
    private MemberAccount memberAccount;

    @Column(unique = true)
    @Size(min = 3, max = 8)
    private String nickname;

    private String tags;

    private String school;

    private Long grade;

    @Size(min = 10, max = 300)
    @ColumnDefault("''")
    private String mentoringSystem;

    private Integer mentoringCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "mento")
    private List<DesiredField> desiredFields = new ArrayList<>();

    @OneToMany(mappedBy = "mento")
    private List<MemberMajor> memberMajors = new ArrayList<>();

    private Long viewCount;


    @Builder
    public Mento(String nickname, String tags, String school, Long grade, Integer mentoringCount) {
        this.nickname = nickname;
        this.tags = tags;
        this.school = school;
        this.grade = grade;
        this.mentoringCount = mentoringCount;
        this.status = Status.WAIT;
        viewCount = 0L;
    }

    public static Mento createMento(String nickname, String tags, String school, Long grade, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Mento mento = Mento.builder()
                .nickname(nickname)
                .tags(tags)
                .school(school)
                .grade(grade)
                .mentoringCount(0)
                .build();
        mento.mappingMemberAccount(memberAccount);
        mento.mappingProfile(profile);
        mento.addDesiredFields(desiredFields);
        mento.addMemberMajors(memberMajors);
        return mento;
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public void updateTags(String tags) {
        this.tags = tags;
    }

    public void mappingProfile(Profile profile) {
        this.profile = profile;
    }

    private void mappingMemberAccount(MemberAccount memberAccount) {
        this.memberAccount = memberAccount;
    }

    private void addDesiredFields(List<DesiredField> desiredFields) {
        if(desiredFields.isEmpty()){
            throw new IllegalArgumentException("희망 멘토링 분야가 1개 이상 있어야합니다.");
        }
        for(DesiredField desiredField : desiredFields){
            desiredField.mappingMento(this);
            this.desiredFields.add(desiredField);
        }
    }

    private void addMemberMajors(List<MemberMajor> memberMajors){
        if(memberMajors.isEmpty()){
            throw new IllegalArgumentException("1개 이상 학과를 가지고 있어야합니다.");
        }
        for(MemberMajor memberMajor : memberMajors){
            memberMajor.mappingMento(this);
            this.memberMajors.add(memberMajor);
        }
    }

    public void updateMentoringSystem(String mentoringSystem){
        this.mentoringSystem = mentoringSystem;
    }

    public void updateIntroduction(String introduction){
        this.tags = introduction;
    }

    public void updateDesiredField(List<DesiredField> desiredFields) {
        if(desiredFields.isEmpty()){
            throw new IllegalArgumentException("희망 멘토링 분야가 1개 이상 있어야합니다.");
        }
        this.desiredFields.clear();
        for(DesiredField desiredField : desiredFields){
            desiredField.mappingMento(this);
            this.desiredFields.add(desiredField);
        }
    }

    // todo 멘토 학년 재 설정 요청보내기! - 매학기 시작할 때마다

    @Override
    public String toString() {
        return "Mento{" +
                "mentoId=" + mentoId +
                ", memberAccount=" + memberAccount +
                ", nickname='" + nickname + '\'' +
                ", introduction='" + tags + '\'' +
                ", school='" + school + '\'' +
                ", grade=" + grade +
                ", mentoringSystem='" + mentoringSystem + '\'' +
                ", mentoringCount=" + mentoringCount +
                ", profile=" + profile +
                ", desiredFields=" + desiredFields +
                ", memberMajors=" + memberMajors +
                ", viewCount=" + viewCount +
                '}';
    }


    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateActive() {
        this.status = Status.ACTIVE;
    }

    public void updateWait() {
        this.status = Status.WAIT;
    }
}

