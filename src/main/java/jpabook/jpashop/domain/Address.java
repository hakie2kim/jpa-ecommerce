package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@AllArgsConstructor
@Getter
public class Address {
    private String city;
    private String street;
    private String zipCode;

    // JPA의 reflection 위해 기본 생성자 필수
    protected Address() { // protected JPA 허용 범위 - 상속하지 않는 이상 new 객체 생성 불가
    }
}
