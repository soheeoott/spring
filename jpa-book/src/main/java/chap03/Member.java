package chap03;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter @Getter
@Entity
@org.hibernate.annotations.DynamicUpdate // 동적 수정 쿼리 : 수정된 데이터만 사용한 쿼리(update) 생성
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;
    private Integer age;
}