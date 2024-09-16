package com.namequickly.logistics.common.shared.affiliation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CompanyAffiliation implements Affiliation {

    // 업체 ID
    COMPANY_1("업체 1"),
    COMPANY_2("업체 2"),
    COMPANY_3("업체 3"),
    COMPANY_4("업체 4"),
    COMPANY_5("업체 5"),
    COMPANY_6("업체 6"),
    COMPANY_7("업체 7"),
    COMPANY_8("업체 8"),
    COMPANY_9("업체 9"),
    COMPANY_10("업체 10");

    private final String companyName;

}
