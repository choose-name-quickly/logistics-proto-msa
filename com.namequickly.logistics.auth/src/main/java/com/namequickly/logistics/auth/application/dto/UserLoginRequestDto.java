package com.namequickly.logistics.auth.application.dto;

public record UserLoginRequestDto (
    String username,
    String password

    // 가입시 어떤 허브 소속(affilation_id), 허브관리자(role)

    /*
    public enum Hub_affliaciton {
    부천허브("HUB_123"),
    인천허브("HUB_124"),
    }

    public enum company_affliaciton {
        플라스틱업체("COMPANY_123"),
        재료허브("COMPANY_124"),
    }

    public enum Courier_affliaciton {
        부천배송기사("COURIER_123"),
        인천배송기사("COURIER_124"),
    }
    */
){}
