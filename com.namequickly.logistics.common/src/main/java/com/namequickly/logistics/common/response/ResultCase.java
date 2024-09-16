package com.namequickly.logistics.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCase {

    /* 성공 0번대 - 모든 성공 응답을 200으로 통일 */
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다."),

    /* 글로벌 1000번대 */

    // 권한 없음 403
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN, 1000, "해당 요청에 대한 권한이 없습니다."),
    // 잘못된 형식의 입력 400
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 1001, "유효하지 않은 입력값입니다."),
    // 존재하지 않는 값 404
    NOT_FOUND(HttpStatus.NOT_FOUND, 1002, "존재하지 않는 입력값입니다."),
    // 시스템 에러 500
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1003, "알 수 없는 에러가 발생했습니다."),

    /* 유저 2000번대 */

    // 존재하지 않는 사용자 404,
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "유저를 찾을 수 없습니다."),
    // 로그인 필요 401
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, 2001, "로그인이 필요합니다."),
    // 중복된 유저네임 입력 409
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, 2002, "중복된 유저네임을 입력하셨습니다."),
    // 유효하지 않은 토큰 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 2003, "유효하지 않은 토큰입니다."),
    // 만료된 액세스 토큰 401
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 2004, "만료된 Access Token"),
    // 만료된 리프레쉬 토큰 401
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 2005, "만료된 Refresh Token"),


    /* 허브 3000번대 */
    // 존재하지 않은 허브 404
    // TODO 허브 개발 완료되면 다시 변경 예정
    // 존재하지 않는 허브 404
    NOT_FOUND_HUB(HttpStatus.NOT_FOUND, 3000, "허브를 찾을 수 없습니다."),
    // 유효하지 않은 권한 403
    UNAUTHORIZED_HUB(HttpStatus.FORBIDDEN, 3001, "작업을 수행할 수 있는 권한이 없습니다."),


    /* 상품 관련 4000번대 */
    // 존재하지 않는 상품 404
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, 4000, "상품을 찾을 수 없습니다."),
    // 배송중인 상품 삭제 불가 400
    CANNOT_DELETE_PRODUCT_IN_DELIVERY(HttpStatus.BAD_REQUEST, 4001, "배송 중인 상품은 삭제할 수 없습니다."),
    // 중복된 상품명 입력 409
    DUPLICATED_PRODUCT_NAME(HttpStatus.CONFLICT, 4002, "중복된 상품명을 입력하셨습니다."),
    // 유효하지 않은 접근 400
    UNAUTHORIZED_DELETE_PRODUCT(HttpStatus.BAD_REQUEST, 4003, "삭제된 상품에 접근할 수 없습니다."),

    /* 회사 관련 5000번대 */
    // TODO 회사 개발 완료되면 다시 변경 예정
    // 존재하지 않는 업체 404
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, 5000, "업체를 찾을 수 없습니다."),
    // 유효하지 않은 권한 403
    UNAUTHORIZED_COMPANY(HttpStatus.FORBIDDEN, 5001, "작업을 수행할 수 있는 권한이 없습니다."),

    /* 배송 관련 6000번대 */
    NOT_FOUND_DELIVERY(HttpStatus.NOT_FOUND, 3000, "배송 정보를 찾을 수 없습니다."),

    /* 주문 관련 7000번대 */
    // 존재하지 않는 업체 404
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, 7000, "주문을 찾을 수 없습니다."),
    // 배송중인 주문 취소 불가 400
    CANNOT_DELETE_ORDER_IN_DELIVERY(HttpStatus.BAD_REQUEST, 7001, "배송 중인 주문은 취소할 수 없습니다."),
    // 배송중인 상품 수정 불가 400
    CANNOT_UPDATE_ORDER_IN_DELIVERY(HttpStatus.BAD_REQUEST, 7001, "배송 중인 주문은 변경할 수 없습니다.");


    private final HttpStatus httpStatus; // 응답 상태 코드
    private final Integer code; // 응답 코드. 도메인에 따라 1000번대로 나뉨
    private final String message; // 응답에 대한 설명
}
