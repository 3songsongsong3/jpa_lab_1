package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// @Entity는 이 클래스를 테이블과 매핑한다고 JPA에게 알려준다.
// @Entity가 사용된 클래스를 엔티티 클래스라고 한다.
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id // 식별자 필드
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    // 매핑 정보가 없는 필드
    // 필드명을 사용해서 컬럼명으로 매핑한다
    // 대소문자를 구분하는 데이터베이스라면 @Column(name = "AGE")처럼 명시적으로 매핑해야한다.
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
