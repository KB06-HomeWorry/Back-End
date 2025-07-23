SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS ScamRiskPhrase;
DROP TABLE IF EXISTS ScamContractSample;
DROP TABLE IF EXISTS Document;
DROP TABLE IF EXISTS InfraInfo;
DROP TABLE IF EXISTS AgentReview;
DROP TABLE IF EXISTS RiskScoreMessage;
DROP TABLE IF EXISTS ChecklistUserAnswer;
DROP TABLE IF EXISTS Checklist;
DROP TABLE IF EXISTS ChecklistQuestion;
DROP TABLE IF EXISTS ChecklistTemplate;
DROP TABLE IF EXISTS riskscoremessage;
DROP TABLE IF EXISTS Deal;
DROP TABLE IF EXISTS ListingOption;
DROP TABLE IF EXISTS ListingPriceHistory;
DROP TABLE IF EXISTS ListingDetail;
DROP TABLE IF EXISTS ListingImage;
DROP TABLE IF EXISTS Listing;
DROP TABLE IF EXISTS AgentImage;
DROP TABLE IF EXISTS Agent;
DROP TABLE IF EXISTS AgencyOffice;
DROP TABLE IF EXISTS user_member_auth;
DROP TABLE IF EXISTS User;

SET FOREIGN_KEY_CHECKS = 1;
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 사용자 (회원) 정보
CREATE TABLE User
(
    user_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '회원 고유번호 (PK)',
    name      VARCHAR(50)  NOT NULL COMMENT '이름',
    email     VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일(로그인 ID)',
    password  VARCHAR(200) NOT NULL COMMENT '비밀번호(암호화)',
    phone     VARCHAR(20) COMMENT '연락처',
    user_type VARCHAR(20) COMMENT '사용자 유형(일반, 중개사 등)'
);

-- 2. 회원 권한 (1:N)
CREATE TABLE user_member_auth
(
    auth_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '권한 고유번호 (PK)',
    user_id BIGINT      NOT NULL COMMENT '연결된 회원 (FK)',
    auth    VARCHAR(50) NOT NULL COMMENT '권한명 (예: ROLE_USER, ROLE_AGENT 등)',
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE
);

-- 3. 중개사 사무소
CREATE TABLE AgencyOffice
(
    office_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사무소 고유번호 (PK)',
    name      VARCHAR(100) NOT NULL COMMENT '사무소명',
    address   VARCHAR(255) COMMENT '주소',
    lat       DOUBLE COMMENT '위도',
    lng       DOUBLE COMMENT '경도',
    phone     VARCHAR(20) COMMENT '연락처',
    point     BIGINT COMMENT '우수중개사 신뢰 점수'
);

-- 4. 공인중개사 정보
CREATE TABLE Agent
(
    agent_id       BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '중개사 고유번호 (PK)',
    office_id      BIGINT      NOT NULL COMMENT '소속 사무소 (FK)',
    name           VARCHAR(50) NOT NULL COMMENT '중개사명',
    license_number VARCHAR(50) NOT NULL UNIQUE COMMENT '공인중개사 등록번호',
    profile_image  VARCHAR(255) COMMENT '프로필 이미지',
    specialties    VARCHAR(200) COMMENT '전문분야(예: #아파트, #전세)',
    description    TEXT COMMENT '자기소개글',
    FOREIGN KEY (office_id) REFERENCES AgencyOffice (office_id) ON DELETE CASCADE
);

-- 5. 중개사 이미지
CREATE TABLE AgentImage
(
    image_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고유 이미지 번호 (PK)',
    agent_id    BIGINT       NOT NULL COMMENT '연결 중개사 (FK)',
    image_url   VARCHAR(255) NOT NULL COMMENT '이미지 파일 경로/URL',
    description TEXT COMMENT '이미지 설명(선택)',
    uploaded_at DATETIME COMMENT '업로드 일시',
    FOREIGN KEY (agent_id) REFERENCES Agent (agent_id) ON DELETE CASCADE
);

-- 6. 매물
CREATE TABLE Listing
(
    listing_id  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '매물 고유번호 (PK)',
    office_id   BIGINT NOT NULL COMMENT '소속 사무소 (FK)',
    agent_id    BIGINT NOT NULL COMMENT '담당 중개사 (FK)',
    address     VARCHAR(255) COMMENT '매물 주소',
    lat         DOUBLE COMMENT '위도',
    lng         DOUBLE COMMENT '경도',
    price       BIGINT COMMENT '가격',
    sale_type   VARCHAR(20) COMMENT '매매/전세/월세',
    description TEXT COMMENT '매물 설명',
    FOREIGN KEY (office_id) REFERENCES AgencyOffice (office_id) ON DELETE CASCADE,
    FOREIGN KEY (agent_id) REFERENCES Agent (agent_id) ON DELETE CASCADE
);

-- 7. 매물 이미지
CREATE TABLE ListingImage
(
    image_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고유 이미지 번호 (PK)',
    listing_id  BIGINT       NOT NULL COMMENT '연결된 매물 (FK)',
    image_url   VARCHAR(255) NOT NULL COMMENT '이미지 파일 경로/URL',
    uploaded_at DATETIME COMMENT '업로드 일시',
    order_num   INT COMMENT '정렬 순서(썸네일/대표이미지 등)',
    FOREIGN KEY (listing_id) REFERENCES Listing (listing_id) ON DELETE CASCADE
);

-- 8. 매물 상세 정보
CREATE TABLE ListingDetail
(
    detail_id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상세정보 고유번호 (PK)',
    listing_id     BIGINT NOT NULL COMMENT '연결된 매물 (FK)',
    area_m2        FLOAT COMMENT '전용면적(㎡)',
    floor          INT COMMENT '해당 층',
    total_floor    INT COMMENT '전체 층수',
    room_count     INT COMMENT '방 개수',
    bath_count     INT COMMENT '욕실 개수',
    built_year     INT COMMENT '건축년도',
    heating_type   VARCHAR(50) COMMENT '난방 방식',
    direction      VARCHAR(20) COMMENT '방향(남향 등)',
    parking        BOOLEAN COMMENT '주차 가능 여부',
    elevator       BOOLEAN COMMENT '엘리베이터 여부',
    loan_available BOOLEAN COMMENT '대출 가능 여부',
    management_fee INT COMMENT '관리비(월, 원)',
    entrance_type  VARCHAR(20) COMMENT '현관구조',
    FOREIGN KEY (listing_id) REFERENCES Listing (listing_id) ON DELETE CASCADE
);

-- 9. 매물 가격 이력
CREATE TABLE ListingPriceHistory
(
    price_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고유 가격 이력 번호 (PK)',
    listing_id BIGINT NOT NULL COMMENT '연결된 매물 (FK)',
    price      BIGINT COMMENT '가격(원)',
    price_type VARCHAR(20) COMMENT '매매가/전세가/월세/실거래가 등',
    start_date DATE COMMENT '적용 시작일',
    end_date   DATE COMMENT '적용 종료일(NULL: 현재가)',
    reg_date   DATETIME COMMENT '이력 등록일시',
    note       TEXT COMMENT '비고/메모',
    FOREIGN KEY (listing_id) REFERENCES Listing (listing_id) ON DELETE CASCADE
);

-- 10. 매물 옵션
CREATE TABLE ListingOption
(
    option_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '옵션 고유번호 (PK)',
    listing_id  BIGINT NOT NULL COMMENT '연결된 매물 (FK)',
    option_name VARCHAR(50) COMMENT '옵션명(예: 에어컨)',
    included    BOOLEAN COMMENT '포함 여부',
    FOREIGN KEY (listing_id) REFERENCES Listing (listing_id) ON DELETE CASCADE
);

-- 11. 거래(실거래) 이력
CREATE TABLE Deal
(
    deal_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '거래 고유번호 (PK)',
    rcpt_yr     INT COMMENT '접수연도',
    cgg_nm      VARCHAR(50) COMMENT '자치구명',
    stdg_nm     VARCHAR(100) COMMENT '법정동명',
    lotno_se_nm VARCHAR(20) COMMENT '지번구분명',
    mno         INT COMMENT '본번',
    sno         INT COMMENT '부번',
    bldg_nm     VARCHAR(255) COMMENT '건물명',
    ctrt_day    DATE COMMENT '계약일',
    thing_amt   BIGINT COMMENT '물건금액(만원)',
    arch_area   DOUBLE COMMENT '건물면적(㎡)',
    land_area   DOUBLE COMMENT '토지면적(㎡)',
    flr         VARCHAR(10) COMMENT '층',
    arch_yr     INT COMMENT '건축년도',
    bldg_usg    VARCHAR(50) COMMENT '건물용도'
);

-- 12. 체크리스트 템플릿(질문 세트)
CREATE TABLE ChecklistTemplate
(
    template_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '템플릿 고유번호 (PK)',
    name        VARCHAR(50) NOT NULL COMMENT '템플릿명',
    sale_type   VARCHAR(20) NOT NULL COMMENT '매매/전세',
    stage       VARCHAR(20) NOT NULL COMMENT '입금 전/입금 후 등'
);

-- 13. 체크리스트 질문(템플릿별)
CREATE TABLE ChecklistQuestion
(
    question_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '질문 고유번호 (PK)',
    template_id   BIGINT NOT NULL COMMENT '소속 템플릿 (FK)',
    content       TEXT   NOT NULL COMMENT '질문 내용',
    effectiveness TEXT COMMENT '질문 효과',
    necessity_title     TEXT COMMENT '없을때 안좋은 점',
    necessity_content     TEXT COMMENT '없을때 안좋은 점',
    order_num     INT COMMENT '질문 순서',
    risk_weight   INT COMMENT '위험도 가중치',
    FOREIGN KEY (template_id) REFERENCES ChecklistTemplate (template_id) ON DELETE CASCADE
);

-- 14. 체크리스트 실행 이력(사용자별)
CREATE TABLE Checklist
(
    checklist_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '체크리스트 고유번호 (PK)',
    user_id      BIGINT NOT NULL COMMENT '사용자 (FK)',
    template_id  BIGINT NOT NULL COMMENT '사용 템플릿 (FK)',
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE,
    FOREIGN KEY (template_id) REFERENCES ChecklistTemplate (template_id) ON DELETE CASCADE
);

-- 15. 체크리스트 답변(사용자별 질문별)
CREATE TABLE ChecklistUserAnswer
(
    answer_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '답변 고유번호 (PK)',
    checklist_id BIGINT NOT NULL COMMENT '체크리스트 이력 (FK)',
    question_id  BIGINT NOT NULL COMMENT '질문 (FK)',
    user_id      BIGINT NOT NULL COMMENT '사용자 (FK)',
    answer       BOOLEAN COMMENT '답변 내용',
    FOREIGN KEY (checklist_id) REFERENCES Checklist (checklist_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES ChecklistQuestion (question_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE
);

-- 점수/등급별 안내문구/설명/이미지 관리 테이블
CREATE TABLE RiskScoreMessage
(
    grade       VARCHAR(20) NOT NULL,-- 등급 (예: Low/Medium/High)
    template_id BIGINT      NOT NULL, -- 사용 템플릿 (ChecklistTemplate.template_id)
    min_score   FLOAT, -- 등급별 최소 점수
    max_score   FLOAT, -- 등급별 최대 점수
    message     TEXT, -- 사용자 안내문구
    description TEXT, -- 상세 설명
    image_url   VARCHAR(255), -- 안내 이미지(아이콘/배너 등) URL
    PRIMARY KEY (grade, template_id), -- 복합 기본키: 등급 + 템플릿ID
    CONSTRAINT fk_riskscore_template
        FOREIGN KEY (template_id)
            REFERENCES ChecklistTemplate (template_id)
            ON DELETE CASCADE
);

-- 17. 중개사 리뷰
CREATE TABLE AgentReview
(
    review_id  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '리뷰 고유번호 (PK)',
    agent_id   BIGINT NOT NULL COMMENT '중개사 (FK)',
    user_id    BIGINT NOT NULL COMMENT '사용자 (FK)',
    rating     INT COMMENT '평점(1~5)',
    content    TEXT COMMENT '리뷰 내용',
    created_at DATETIME COMMENT '작성 일시',
    FOREIGN KEY (agent_id) REFERENCES Agent (agent_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE
);

-- 18. 인프라 정보
CREATE TABLE InfraInfo
(
    infra_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '인프라 고유번호 (PK)',
    name     VARCHAR(50) COMMENT '인프라명',
    type     VARCHAR(30) COMMENT 'CCTV/편의시설/치안시설 등',
    address  VARCHAR(255) COMMENT '주소',
    lat      DOUBLE COMMENT '위도',
    lng      DOUBLE COMMENT '경도',
    detail   TEXT COMMENT '세부 설명'
);

-- 19. 계약서/서류 파일 업로드 및 AI 분석 결과
CREATE TABLE Document
(
    document_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '서류 고유번호 (PK)',
    user_id     BIGINT NOT NULL COMMENT '업로드한 회원 (FK)',
    doc_type    VARCHAR(30) COMMENT '서류 종류',
    file_url    VARCHAR(255) COMMENT '파일 경로',
    uploaded_at DATETIME COMMENT '업로드 일시',
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE
);

-- 20. 사기 계약서 샘플(전문)
CREATE TABLE ScamContractSample
(
    sample_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '샘플 고유번호 (PK)',
    name        VARCHAR(100) COMMENT '샘플명',
    content     TEXT COMMENT '계약서 전문',
    description TEXT COMMENT '설명',
    created_at  DATETIME COMMENT '생성 일시'
);

-- 21. 사기 위험 문구/패턴
CREATE TABLE ScamRiskPhrase
(
    phrase_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '문구 고유번호 (PK)',
    phrase      VARCHAR(255) COMMENT '위험 문구',
    sample_id   BIGINT NOT NULL COMMENT '연결 샘플 (FK)',
    risk_level  FLOAT COMMENT '위험도',
    description TEXT COMMENT '설명',
    created_at  DATETIME COMMENT '생성 일시',
    FOREIGN KEY (sample_id) REFERENCES ScamContractSample (sample_id) ON DELETE CASCADE
);

SET FOREIGN_KEY_CHECKS = 1;
-- 1. 회원
INSERT INTO User (name, email, password, phone, user_type)
VALUES ('홍길동', 'gildong@example.com', 'encrypted_pw1', '010-1111-2222', '일반'),
       ('김중개', 'agentkim@example.com', 'encrypted_pw2', '010-3333-4444', '중개사');

-- 2. 권한
INSERT INTO user_member_auth (user_id, auth)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_AGENT');

-- 3. 중개사 사무소
INSERT INTO AgencyOffice (name, address, lat, lng, phone, point)
VALUES ('꿈에그린공인중개사사무소', '서울특별시 강남구 테헤란로 123', 37.5013, 127.0396, '02-555-5555', 95);

-- 4. 중개사
INSERT INTO Agent (office_id, name, license_number, profile_image, specialties, description)
VALUES (1, '김중개', 'A-2024-00001', '/images/agent/kim.jpg', '#아파트,#전세', '20년 경력의 강남 전문 중개사입니다.');

-- 5. 중개사 이미지
INSERT INTO AgentImage (agent_id, image_url, description, uploaded_at)
VALUES (1, '/images/agent/kim_cert1.jpg', '공인중개사 자격증', NOW());

-- 6. 매물
INSERT INTO Listing (office_id, agent_id, address, lat, lng, price, sale_type, description)
VALUES (1, 1, '서울특별시 강남구 역삼동 123-45', 37.4981, 127.0276, 900000000, '매매', '강남역 도보 3분 거리 신축 아파트 매물입니다.');

-- 7. 매물 이미지
INSERT INTO ListingImage (listing_id, image_url, uploaded_at, order_num)
VALUES (1, '/images/listing/apt1_1.jpg', NOW(), 1),
       (1, '/images/listing/apt1_2.jpg', NOW(), 2);

-- 8. 매물 상세
INSERT INTO ListingDetail (listing_id, area_m2, floor, total_floor, room_count, bath_count, built_year, heating_type,
                           direction, parking, elevator, loan_available, management_fee, entrance_type)
VALUES (1, 84.32, 10, 20, 3, 2, 2022, '개별난방', '남향', TRUE, TRUE, TRUE, 180000, '계단식');

-- 9. 매물 가격 이력
INSERT INTO ListingPriceHistory (listing_id, price, price_type, start_date, end_date, reg_date, note)
VALUES (1, 920000000, '매매가', '2024-04-01', '2024-05-10', '2024-04-01 09:00:00', '최초 등록가'),
       (1, 900000000, '매매가', '2024-05-11', NULL, NOW(), '가격 인하');

-- 10. 매물 옵션
INSERT INTO ListingOption (listing_id, option_name, included)
VALUES (1, '에어컨', TRUE),
       (1, '냉장고', TRUE),
       (1, '세탁기', FALSE);

-- 11. 실거래 이력
INSERT INTO Deal (rcpt_yr, cgg_nm, stdg_nm, lotno_se_nm, mno, sno, bldg_nm, ctrt_day, thing_amt, arch_area, land_area,
                  flr, arch_yr, bldg_usg)
VALUES (2024, '강남구', '역삼동', '본번', 123, 45, '현대아파트', '2024-05-02', 92000, 84.32, 45.0, '10', 2022, '공동주택');

-- 11. 체크리스트 템플릿
INSERT INTO ChecklistTemplate (name, sale_type, stage)
VALUES ('매매 계약 전 체크리스트', '매매', '계약 전'),
       ('매매 중도금 납부 체크리스트', '매매', '중도금 납부'),
       ('매매 잔금 및 입주 체크리스트', '매매', '잔금 및 소유권 이전'),
       ('매매 입주 후 체크리스트', '매매', '입주 후'),
       ('임대차 계약 전 체크리스트', '전/월세', '계약 전'),
       ('임대차 중도금 납부 체크리스트', '전/월세', '중도금 납부'),
       ('임대차 잔금 및 입주 체크리스트', '전/월세', '잔금 및 입주'),
       ('임대차 입주 후 체크리스트', '전/월세', '입주 후');

-- 11. 체크리스트 질문
-- [매매] 계약 전 (template_id=1)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (1, '등기부등본을 확인했습니까?', '소유자와 권리관계를 사전에 확인하여 예기치 않은 법적 문제를 막습니다.', 1, 15,
        '명의 도용 및 권리관계 불일치','등기부등본을 확인하지 않으면 소유자가 아닌 자와 계약하거나, 근저당·가압류 등 숨겨진 권리관계를 모르고 계약하여 무효 계약, 보증금 사기 등 심각한 피해가 발생할 수 있습니다.<br></br>등기부등본을 꼼꼼히 확인하면 실제 소유주와 권리관계를 명확히 파악해 명의 도용, 권리침해 등 사기를 예방할 수 있습니다.'),

       (1, '건축물대장을 확인했습니까?', '위법 건축 여부를 살펴 거래가 무효 처리되는 일을 방지합니다.', 2, 12,
        '불법 건축물 계약','건축물대장을 확인하지 않으면 불법 증축, 용도 위반 등 건축물의 법적 문제를 모른 채 계약해 계약 무효, 철거, 이행강제금 등 금전적 피해를 입을 수 있습니다.<br></br>건축물대장을 통해 건물의 적법성과 구조, 용도 등을 사전에 파악해 불법 건축물과 관련된 분쟁을 예방할 수 있습니다.'),

       (1, '토지이용계획을 확인했습니까?', '토지의 용도나 제한사항을 미리 파악하여 활용상 불이익을 줄입니다.', 3, 9,
        '용도제한 및 활용 불가 토지 계약','토지이용계획을 확인하지 않으면 건축이나 개발이 불가능한 토지를 잘못 매입해, 투자금 손실·활용 불능 등 심각한 금전 피해가 발생할 수 있습니다.<br></br>사전에 토지이용계획을 확인하면 용도지역, 제한사항 등 활용 가능성을 정확히 파악해 불필요한 매입 및 분쟁을 사전에 차단할 수 있습니다.'),

       (1, '실매물 여부를 확인했습니까?', '광고와 실제 매물의 일치 여부를 확인해 허위 계약을 피합니다.', 4, 12,
        '허위 매물 및 중복 계약','실매물 여부를 확인하지 않으면 광고와 다른 허위 매물에 속거나, 이미 거래된 매물을 계약해 보증금 편취, 계약 무효 등의 사기를 당할 수 있습니다.<br></br>실매물 존재와 실제 소유주 확인 등 현장 확인을 하면 허위 계약, 중복 계약 등으로 인한 금전 피해를 막을 수 있습니다.'),

       (1, '주변 시세를 비교했습니까?', '인근 시세를 참고해 부당하게 높은 가격에 계약하는 일을 예방합니다.', 5, 9,
        '과도한 가격 계약','주변 시세를 확인하지 않으면 시세보다 과도하게 높은 가격에 계약해 금전적으로 큰 손해를 입을 수 있습니다.<br></br>인근 시세를 비교하고 협상하면 적정가로 거래해 부당한 가격 피해를 막을 수 있습니다.'),

       (1, '중개사 자격(공인중개사 등록)을 확인했습니까?', '공인중개사 등록 여부를 점검해 불법 중개로 인한 사고를 줄입니다.', 6, 15,
        '무자격 중개 및 불법 중개 사기','공인중개사 등록 여부를 확인하지 않으면 무자격 중개로 인해 사고 발생 시 피해 구제도 어렵고, 금전 손실이 커질 수 있습니다.<br></br>등록 여부를 꼼꼼히 확인하면 합법적이고 신뢰할 수 있는 거래로 불법 중개 피해를 예방할 수 있습니다.'),

       (1, '계약금 반환 조항을 협의했습니까?', '계약 해지 시 반환 조건을 명확히 하여 분쟁 소지를 줄입니다.', 7, 13,
        '계약금 반환 분쟁','계약금 반환 조항이 없으면 계약 해지나 분쟁 시 계약금을 돌려받지 못해 금전적 손실, 소송 등 분쟁이 자주 발생합니다.<br></br>반환 조건을 명확히 협의하면 해지 시 금전 분쟁을 미리 예방하고, 계약의 안전성을 높일 수 있습니다.');

-- [매매] 중도금 납부 (template_id=2)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (2, '중도금 일정과 금액을 확인했습니까?', '납부 계획을 정확히 하여 착오나 지연을 피할 수 있습니다.', 1, 19,
        '납부 착오 및 연체','중도금 일정이나 금액을 명확히 확인하지 않으면 납부일을 놓쳐 연체이자가 발생하거나, 잘못된 금액 이체로 추가 비용, 계약 해지 등 분쟁에 휘말릴 수 있습니다.<br></br>납부 일정을 꼼꼼히 체크하면 실수 없이 중도금을 이체할 수 있어 연체, 추가비용 등 예기치 못한 피해를 미리 예방할 수 있습니다.'),

       (2, '입금 계좌 명의를 확인했습니까?', '송금 전 계좌 명의를 확인해 금전 사고를 방지합니다.', 2, 24,
        '계좌 명의 오류','입금 계좌 명의를 확인하지 않으면 명의가 다른 계좌로 잘못 송금해 보증금이나 중도금이 사기꾼에게 넘어가는 사례가 빈번하게 발생합니다. 실제로 거래 당사자가 아닌 계좌로 송금하여 돌려받지 못하는 피해가 반복되고 있습니다.<br></br>계좌 명의를 철저히 확인하면 실제 거래 당사자에게만 송금됨을 보장해 금전 피해, 이중계약, 횡령 등 각종 사기를 원천적으로 차단할 수 있습니다.'),

       (2, '대출 실행 준비를 점검했습니까?', '대출 요건과 절차를 점검해 실행 과정의 차질을 줄입니다.', 3, 24,
        '대출 실행 차질','대출 실행 준비가 부족하면 자금 조달이 지연되어 중도금이나 잔금 납부에 실패하고, 위약금이나 계약 해지로 이어질 수 있습니다.<br></br>미리 대출 조건과 일정을 확인하면 자금 부족, 계약 위반 등 피해를 사전에 막을 수 있습니다.'),

       (2, '분양 보증보험 가입 여부를 확인했습니까?', '분양의 경우 보증 가입 여부를 확인해 보증금 손실에 대비합니다.', 4, 18,
        '보증금 미회수 위험','분양 보증보험에 가입하지 않으면 분양사가 부도나 미이행 시 보증금을 돌려받지 못하는 심각한 피해가 발생할 수 있습니다.<br></br>분양 보증보험 가입 여부를 반드시 확인하면 분양사 부도 등 비상상황에도 보증금 회수가 가능해집니다.');

-- [매매] 잔금 및 입주 (template_id=3)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (3, '최종 등기부를 확인했습니까?', '잔금 납부 전 권리 상태를 다시 확인하여 추가 근저당 설정을 막습니다.', 1, 20,
        '권리 변동 누락','잔금 납부 직전 등기부를 다시 확인하지 않으면 추가 근저당이나 가압류가 설정되어 소유권 이전이 불가능해지거나 피해를 입을 수 있습니다.<br></br>최종 등기부를 다시 확인하면 모든 권리 변동을 체크할 수 있어 안전한 소유권 이전이 보장됩니다.'),

       (3, '등기 신청에 필요한 서류를 모두 준비했습니까?', '서류를 빠짐없이 준비해 등기 이전이 지연되지 않도록 합니다.', 2, 16,
        '등기 신청 서류 미비','등기 신청 서류가 부족하면 소유권 이전이 지연되거나, 위조·누락 등으로 등기 자체가 무효가 되는 분쟁이 발생할 수 있습니다.<br></br>필요한 서류를 미리 준비하면 신속한 등기 이전과 등기 무효 및 지연, 분쟁 위험을 예방할 수 있습니다.'),

       (3, '세금 납부 준비를 하였습니까?', '취득세 등 세금 납부 일정을 관리하여 가산세 부담을 피합니다.', 3, 12,
        '세금 납부 지연','세금 납부 계획이 부족하면 취득세 등 세금이 연체되어 가산세, 불이익 등 경제적 손해를 입을 수 있습니다.<br></br>세금 일정을 미리 체크하면 연체, 가산세 부담 등 금전적 위험을 예방할 수 있습니다.'),

       (3, '이사 일정을 협의했습니까?', '매도인과 일정을 조율하여 원활한 입주가 가능하도록 합니다.', 4, 12,
        '이사 일정 혼선','이사 일정을 제대로 협의하지 않으면 이중입주, 입주일 충돌, 분쟁 등으로 불편과 피해가 발생할 수 있습니다.<br></br>미리 일정을 맞추면 원활한 입주, 분쟁 없는 이사 진행이 가능해집니다.'),

       (3, '수수료 정산을 정확히 하였습니까?', '정확한 금액을 확인해 과다 청구나 금전 분쟁을 방지합니다.', 5, 12,
        '수수료 과다·이중 청구','수수료 정산이 명확하지 않으면 과다 청구나 이중 청구 등 금전 분쟁이 잦게 일어납니다.<br></br>정확한 정산을 통해 불필요한 비용 손실, 분쟁, 신뢰 저하를 미리 막을 수 있습니다.'),

       (3, '열쇠와 비밀번호를 인수받았습니까?', '출입 수단을 확보해 입주 당일 불편함이 없도록 합니다.', 6, 13,
        '출입 수단 미인수','열쇠나 비밀번호를 제대로 받지 않으면 입주 당일 집에 들어가지 못하거나, 이전 세입자 무단출입 등 분쟁이 발생할 수 있습니다.<br></br>입주 전 출입 수단을 모두 인수받으면 안전하고 편리한 입주가 보장됩니다.');

-- [매매] 입주 후 (template_id=4)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (4, '전입신고와 확정일자를 완료했습니까?', '대항력을 확보해 보증금 반환에 대한 법적 보호를 받습니다.', 1, 22,
        '보증금 미회수','전입신고와 확정일자를 하지 않으면 보증금 반환소송에서 대항력을 주장할 수 없어 보증금을 돌려받지 못할 수 있습니다.<br></br>전입신고와 확정일자를 모두 완료하면 법적 보호를 받아 우선변제권 등 권리를 확보할 수 있습니다.'),

       (4, '공과금 명의를 본인으로 변경했습니까?', '요금 청구를 본인 명의로 바꿔 체납 책임이 전가되는 일을 막습니다.', 2, 18,
        '공과금 체납 전가','공과금 명의를 변경하지 않으면 이전 거주자의 체납 요금이 본인에게 전가되어 추가 비용을 부담하게 됩니다.<br></br>공과금 명의를 변경하면 체납, 과다 청구 등 불필요한 피해를 미리 방지할 수 있습니다.'),

       (4, '체납 여부를 확인했습니까?', '이전 세입자의 미납 내역을 파악해 단전·단수 같은 불편을 예방합니다.', 3, 18,
        '체납 내역 미확인','체납 내역을 확인하지 않으면 입주 직후 전기, 수도, 가스 등의 사용 제한(단전·단수), 미납금 청구 등 피해가 발생할 수 있습니다.<br></br>입주 전 체납 여부를 확인하면 미납금 부담, 사용제한 등 불이익을 예방할 수 있습니다.'),

       (4, '입주 직후 집의 하자 여부를 점검했습니까?', '입주 직후 하자를 점검하여 책임 소재를 분명히 합니다.', 4, 18,
        '하자 책임 분쟁','입주 직후 하자를 확인하지 않으면 이후 하자 발생 시 책임 소재가 불분명해져 수리비 부담, 분쟁이 생깁니다.<br></br>즉시 하자를 점검하면 책임 소재를 명확히 하여 수리비 부담, 분쟁을 줄일 수 있습니다.'),

       (4, '건강보험 등록을 변경했습니까?', '주소 이전에 따른 보험료 체계 변경을 반영해 불이익을 줄입니다.', 5, 9,
        '보험료 불이익','주소 이전 후 건강보험 등록을 바꾸지 않으면 보험료가 과다 청구되거나, 불필요한 불이익을 당할 수 있습니다.<br></br>등록 변경을 하면 적정 보험료가 적용되어 불필요한 금전적 손실을 막을 수 있습니다.');

-- [임대차] 계약 전 (template_id=5)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (5, '등기부등본을 확인했습니까?', '임대인과 소유자 일치 여부를 확인해 무효 계약을 피합니다.', 1, 14,
        '임대인 명의 도용','등기부등본을 확인하지 않으면 임대인과 소유자가 달라 무효 계약이나 보증금 사기 피해가 발생할 수 있습니다.<br></br>등기부등본을 반드시 확인하면 소유자 확인, 명의 도용, 이중 임대 등 사기를 예방할 수 있습니다.'),

       (5, '건축물대장을 확인했습니까?', '건물의 법적 상태를 점검하여 문제가 생기지 않도록 합니다.', 2, 11,
        '불법 건축물 임대','건축물대장을 확인하지 않으면 불법 증축, 용도 위반 건물 임대 등으로 계약 무효나 과태료, 사용 제한 등 피해를 입을 수 있습니다.<br></br>건축물대장을 점검하면 불법 건축물 계약 등 위험을 미리 차단할 수 있습니다.'),

       (5, '실매물 여부를 확인했습니까?', '실제 존재하는 매물인지 확인해 허위 광고에 속지 않도록 합니다.', 3, 11,
        '허위 매물 계약','실매물 여부를 확인하지 않으면 존재하지 않는 매물이나 이미 거래된 매물을 계약해 보증금 사기를 당할 수 있습니다.<br></br>실매물 여부를 확인하면 허위 매물, 중복계약 등 사기를 막을 수 있습니다.'),

       (5, '주변 시세를 비교했습니까?', '주변 시세를 비교해 과도한 임대 조건을 피할 수 있습니다.', 4, 9,
        '과도한 임대료 청구','주변 시세를 비교하지 않으면 부당하게 높은 임대료로 계약하는 피해를 입을 수 있습니다.<br></br>시세를 참고하면 합리적인 임대료로 계약할 수 있어 금전적 손실을 예방합니다.'),

       (5, '중개사 등록 여부를 확인했습니까?', '공인된 중개인인지 검토해 불법 중개 피해를 줄입니다.', 5, 14,
        '무자격 중개 사기','중개사 등록을 확인하지 않으면 무자격자가 불법 중개 후 책임 회피, 금전 피해를 남기는 사례가 있습니다.<br></br>등록 여부를 반드시 확인하면 불법 중개 피해, 피해 구제 곤란 등을 예방할 수 있습니다.'),

       (5, '전입신고 가능 여부를 확인했습니까?', '대항력 확보 가능성을 따져 보증금 회수 위험을 줄입니다.', 6, 14,
        '대항력 상실','전입신고가 불가한 주택에 계약하면 대항력을 주장할 수 없어 보증금 미회수 등 큰 피해가 생길 수 있습니다.<br></br>전입신고 가능 여부를 확인하면 법적 보호를 받고 보증금 회수 가능성이 높아집니다.'),

       (5, '보증금 반환 조항을 협의했습니까?', '계약 중도 해지 시 반환 조건을 정해 분쟁을 예방합니다.', 7, 12,
        '보증금 반환 분쟁','반환 조항이 없으면 해지 시 보증금을 돌려받지 못하는 분쟁, 소송 등 금전 손실이 자주 발생합니다.<br></br>반환 조건을 미리 협의하면 계약 해지, 반환 거부 등 금전적 분쟁을 미리 예방할 수 있습니다.');

-- [임대차] 중도금 납부 (template_id=6)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (6, '중도금 납부 일정을 확인했습니까?', '중도금 납부 일정을 명확히 하여 연체 없이 준비합니다.', 1, 16,
        '연체 및 계약 해지','중도금 납부 일정 미확인 시 연체, 계약 해지, 추가 이자 부담 등 금전 손실이 발생할 수 있습니다.<br></br>일정을 철저히 확인하면 연체, 위약금, 해지 등 예기치 않은 손해를 예방할 수 있습니다.'),

       (6, '입금 계좌 명의를 확인했습니까?', '송금 전 계좌 정보를 확인해 제3자 사기를 막습니다.', 2, 27,
        '송금 사기','계좌 명의를 확인하지 않으면 타인 명의 계좌로 송금해 보증금, 중도금 사기 피해를 입을 수 있습니다.<br></br>계좌 명의 확인만으로도 금전 피해, 중복 송금 등 사기를 막을 수 있습니다.'),

       (6, '반환보증 가입 여부를 확인했습니까?', '임대인 부도 대비를 위해 보증 가입 여부를 확인합니다.', 3, 21,
        '보증금 회수 실패','보증보험 미가입 시 임대인 부도, 계약 불이행 시 보증금을 돌려받지 못하는 심각한 피해가 생깁니다.<br></br>반드시 가입 여부를 확인해 임대인 부도에도 보증금 회수가 가능하게 대비할 수 있습니다.'),

       (6, '입주 전 시설 및 가전을 점검했습니까?', '입주 전 상태를 확인해 수리 책임을 분명히 합니다.', 4, 21,
        '하자·고장 분쟁','시설, 가전 미점검 시 하자, 고장 발견 후 수리비 부담, 분쟁 등 피해가 자주 발생합니다.<br></br>입주 전 점검을 통해 책임 소재 명확화, 불필요한 비용 발생을 막을 수 있습니다.');

-- [임대차] 잔금 및 입주 (template_id=7)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (7, '집 상태를 재확인했습니까?', '하자나 훼손 여부를 다시 확인해 책임 소재를 명확히 합니다.', 1, 28,
        '집 상태 미확인','입주 직전 집 상태를 확인하지 않으면 하자, 훼손 등으로 분쟁과 수리비 부담이 커집니다.<br></br>입주 전에 꼼꼼히 체크하면 분쟁을 예방하고 쾌적한 입주가 가능합니다.'),

       (7, '열쇠와 비밀번호를 인계받았습니까?', '입주 당일 출입이 가능하도록 사전 준비를 합니다.', 2, 21,
        '출입 수단 인수 누락','열쇠, 비밀번호 인계가 미흡하면 입주일 출입 불가, 무단출입 등 불편과 분쟁이 생깁니다.<br></br>미리 인계받으면 입주와 동시에 바로 출입할 수 있어 안심입니다.'),

       (7, '공과금 명의를 변경했습니까?', '사용 요금의 청구 주체를 바꿔 불필요한 요금 부담을 막습니다.', 3, 21,
        '공과금 체납 책임 전가','명의 변경을 하지 않으면 이전 세입자 체납요금이 본인에게 청구되는 피해가 생깁니다.<br></br>명의를 확실히 변경하면 체납, 과다 청구 등 손해를 막을 수 있습니다.'),

       (7, '월세 자동이체를 설정했습니까?', '월세 자동 납부를 설정해 연체를 방지합니다.', 4, 15,
        '월세 연체','자동이체 미설정 시 월세 연체, 연체료 부담, 계약 위반 분쟁이 발생할 수 있습니다.<br></br>자동이체 설정만으로 연체 위험과 불필요한 분쟁을 예방할 수 있습니다.');

-- [임대차] 입주 후 (template_id=8)
INSERT INTO ChecklistQuestion (template_id, content, effectiveness, order_num, risk_weight, necessity_title, necessity_content)
VALUES (8, '전입신고와 확정일자를 완료했습니까?', '보증금에 대한 법적 보호를 확보합니다.', 1, 24,
        '법적 보호 미확보','전입신고와 확정일자를 하지 않으면 보증금 반환 소송 등에서 대항력, 우선변제권을 주장할 수 없어 보증금을 돌려받지 못하는 피해가 발생할 수 있습니다.<br></br>전입신고와 확정일자를 모두 완료하면 법적 보호를 받아 우선변제권 등 권리를 확보할 수 있습니다.'),

       (8, '공과금 명의를 이전했습니까?', '청구 명의를 변경해 이전 체납에 휘말리는 일을 방지합니다.', 2, 19,
        '체납 청구 피해','공과금 명의를 이전하지 않으면 이전 사용자의 체납이 본인에게 청구되어 예상치 못한 비용 부담이 생깁니다.<br></br>명의 이전만으로 체납, 분쟁, 금전 손해를 막을 수 있습니다.'),

       (8, '입주 후 시설 하자를 통보했습니까?', '하자 발생 시점을 기록해 수리 책임을 분명히 합니다.', 3, 19,
        '하자 책임 분쟁','입주 후 하자 통보를 하지 않으면 하자 발생 시 책임소재가 불분명해 분쟁, 비용 부담이 커질 수 있습니다.<br></br>즉시 통보하면 책임 소재를 명확히 하여 분쟁, 비용 발생을 막을 수 있습니다.'),

       (8, '기존 미납 내역을 확인했습니까?', '기존 미납 확인으로 입주 후 불편을 줄입니다.', 4, 14,
        '기존 미납금 전가','기존 미납 내역을 확인하지 않으면 입주 후 체납금이 본인에게 청구되거나, 사용 제한(단전·단수 등)으로 불이익이 발생할 수 있습니다.<br></br>입주 전 미납 내역 확인만으로 비용 부담과 사용 제한 등 문제를 미리 차단할 수 있습니다.'),

       (8, '계약 및 입금 내역을 정리했습니까?', '문서 정리를 통해 향후 분쟁에 대비합니다.', 5, 9,
        '계약 내역 미정리','계약, 입금 내역을 정리하지 않으면 추후 분쟁, 입금 누락, 조건 불이행 등 다양한 문제가 발생할 수 있습니다.<br></br>계약 및 입금 내역을 철저히 정리하면 분쟁 발생 시 신속한 대응과 피해 구제가 가능합니다.');

-- 14. 체크리스트 실행 이력
INSERT INTO Checklist (user_id, template_id)
VALUES (1, 1), -- user 1, 매매 계약 전
       (2, 2), -- user 2, 매매 중도금 납부
       (1, 3), -- user 1, 매매 잔금 및 입주
       (2, 4), -- user 2, 매매 입주 후
       (1, 5), -- user 1, 임대차 계약 전
       (2, 6), -- user 2, 임대차 중도금 납부
       (1, 7), -- user 1, 임대차 잔금 및 입주
       (2, 8), -- user 2, 임대차 입주 후
       (1, 2), -- user 1, 매매 중도금 납부
       (2, 5);
-- user 2, 임대차 계약 전

-- Checklist 1 (template_id=1, 질문 1~7, user_id=1)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (1, 1, 1, TRUE),
       (1, 2, 1, TRUE),
       (1, 3, 1, FALSE),
       (1, 4, 1, TRUE),
       (1, 5, 1, FALSE),
       (1, 6, 1, TRUE),
       (1, 7, 1, TRUE);

-- Checklist 2 (template_id=2, 질문 8~11, user_id=2)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (2, 8, 2, TRUE),
       (2, 9, 2, TRUE),
       (2, 10, 2, FALSE),
       (2, 11, 2, TRUE);

-- Checklist 3 (template_id=3, 질문 12~17, user_id=1)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (3, 12, 1, FALSE),
       (3, 13, 1, TRUE),
       (3, 14, 1, TRUE),
       (3, 15, 1, FALSE),
       (3, 16, 1, TRUE),
       (3, 17, 1, TRUE);

-- Checklist 4 (template_id=4, 질문 18~22, user_id=2)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (4, 18, 2, TRUE),
       (4, 19, 2, TRUE),
       (4, 20, 2, TRUE),
       (4, 21, 2, FALSE),
       (4, 22, 2, TRUE);

-- Checklist 5 (template_id=5, 질문 23~29, user_id=1)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (5, 23, 1, TRUE),
       (5, 24, 1, FALSE),
       (5, 25, 1, TRUE),
       (5, 26, 1, TRUE),
       (5, 27, 1, FALSE),
       (5, 28, 1, TRUE),
       (5, 29, 1, TRUE);

-- Checklist 6 (template_id=6, 질문 30~33, user_id=2)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (6, 30, 2, TRUE),
       (6, 31, 2, FALSE),
       (6, 32, 2, TRUE),
       (6, 33, 2, TRUE);

-- Checklist 7 (template_id=7, 질문 34~37, user_id=1)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (7, 34, 1, TRUE),
       (7, 35, 1, TRUE),
       (7, 36, 1, FALSE),
       (7, 37, 1, TRUE);

-- Checklist 8 (template_id=8, 질문 38~42, user_id=2)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (8, 38, 2, FALSE),
       (8, 39, 2, TRUE),
       (8, 40, 2, TRUE),
       (8, 41, 2, FALSE),
       (8, 42, 2, TRUE);

-- Checklist 9 (template_id=2, 질문 8~11, user_id=1)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (9, 8, 1, TRUE),
       (9, 9, 1, TRUE),
       (9, 10, 1, FALSE),
       (9, 11, 1, TRUE);

-- Checklist 10 (template_id=5, 질문 23~29, user_id=2)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer)
VALUES (10, 23, 2, TRUE),
       (10, 24, 2, FALSE),
       (10, 25, 2, TRUE),
       (10, 26, 2, TRUE),
       (10, 27, 2, FALSE),
       (10, 28, 2, TRUE),
       (10, 29, 2, TRUE);

-- 16. 점수/등급별 안내문구
-- 매매 계약 전 체크리스트 (template_id = 1)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 1, 0, 39, '위험 낮음', '계약 전 체크리스트 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 1, 40, 59, '주의 필요', '일부 위험 요소가 있으니 꼼꼼히 확인하세요.', '/images/risk_medium.png'),
       ('High', 1, 60, 79, '위험 높음', '여러 위험 신호가 있으니 전문가 상담이 필요합니다.', '/images/risk_high.png'),
       ('VeryHigh', 1, 80, 100, '거래 주의', '위험 요소가 매우 많으니 거래를 신중히 결정하세요.', '/images/risk_veryhigh.png');

-- 매매 중도금 납부 체크리스트 (template_id = 2)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 2, 0, 39, '위험 낮음', '중도금 납부 단계에서 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 2, 40, 59, '주의 필요', '중도금 납부 시 일부 주의사항을 확인하세요.', '/images/risk_medium.png'),
       ('High', 2, 60, 79, '위험 높음', '중도금 납부 시 다수 위험 요소가 있습니다.', '/images/risk_high.png'),
       ('VeryHigh', 2, 80, 100, '거래 주의', '중도금 납부 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');

-- 매매 잔금 및 입주 체크리스트 (template_id = 3)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 3, 0, 39, '위험 낮음', '잔금 및 입주 단계에서 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 3, 40, 59, '주의 필요', '잔금/입주 시 몇 가지 주의사항이 필요합니다.', '/images/risk_medium.png'),
       ('High', 3, 60, 79, '위험 높음', '잔금/입주 시 여러 위험 신호가 있습니다.', '/images/risk_high.png'),
       ('VeryHigh', 3, 80, 100, '거래 주의', '잔금/입주 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');

-- 매매 입주 후 체크리스트 (template_id = 4)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 4, 0, 39, '위험 낮음', '입주 후 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 4, 40, 59, '주의 필요', '입주 후 일부 점검이 필요합니다.', '/images/risk_medium.png'),
       ('High', 4, 60, 79, '위험 높음', '입주 후 다수 위험 요소가 있습니다.', '/images/risk_high.png'),
       ('VeryHigh', 4, 80, 100, '거래 주의', '입주 후 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');

-- 임대차 계약 전 체크리스트 (template_id = 5)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 5, 0, 39, '위험 낮음', '임대차 계약 전 단계에서 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 5, 40, 59, '주의 필요', '임대차 계약 전 단계에서 일부 주의사항이 있습니다.', '/images/risk_medium.png'),
       ('High', 5, 60, 79, '위험 높음', '임대차 계약 전 단계에서 위험 신호가 감지됩니다.', '/images/risk_high.png'),
       ('VeryHigh', 5, 80, 100, '거래 주의', '임대차 계약 전 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');

-- 임대차 중도금 납부 체크리스트 (template_id = 6)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 6, 0, 39, '위험 낮음', '임대차 중도금 납부 단계에서 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 6, 40, 59, '주의 필요', '임대차 중도금 납부 시 주의사항이 있습니다.', '/images/risk_medium.png'),
       ('High', 6, 60, 79, '위험 높음', '임대차 중도금 납부 단계에서 여러 위험 요소가 있습니다.', '/images/risk_high.png'),
       ('VeryHigh', 6, 80, 100, '거래 주의', '임대차 중도금 납부 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');

-- 임대차 잔금 및 입주 체크리스트 (template_id = 7)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 7, 0, 39, '위험 낮음', '임대차 잔금 및 입주 단계에서 위험 신호가 적지만 여전히 주의가 필요합니다.', '/images/risk_low.png'),
       ('Medium', 7, 40, 59, '주의 필요', '임대차 잔금 및 입주 단계에서 주의사항이 필요합니다.', '/images/risk_medium.png'),
       ('High', 7, 60, 79, '위험 높음', '임대차 잔금 및 입주 단계에서 다수 위험 신호가 있습니다.', '/images/risk_high.png'),
       ('VeryHigh', 7, 80, 100, '거래 주의', '임대차 잔금 및 입주 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');

-- 임대차 입주 후 체크리스트 (template_id = 8)
INSERT INTO RiskScoreMessage (grade, template_id, min_score, max_score, message, description, image_url)
VALUES ('Low', 8, 0, 39, '위험 낮음', '임대차 입주 후 특별한 위험 신호가 적습니다.', '/images/risk_low.png'),
       ('Medium', 8, 40, 59, '주의 필요', '임대차 입주 후 일부 점검이 필요합니다.', '/images/risk_medium.png'),
       ('High', 8, 60, 79, '위험 높음', '임대차 입주 후 여러 위험 요소가 있습니다.', '/images/risk_high.png'),
       ('VeryHigh', 8, 80, 100, '거래 주의', '임대차 입주 후 단계에서 매우 높은 위험이 감지됩니다.', '/images/risk_veryhigh.png');


-- 17. 중개사 리뷰
INSERT INTO AgentReview (agent_id, user_id, rating, content, created_at)
VALUES (1, 1, 5, '친절하고 신속한 중개! 만족합니다.', NOW());

-- 18. 인프라 정보
INSERT INTO InfraInfo (name, type, address, lat, lng, detail)
VALUES ('서울역 CCTV', 'CCTV', '서울특별시 중구', 37.5547, 126.9706, '서울역 광장 주요 CCTV'),
       ('강남초등학교', '교육시설', '서울특별시 강남구', 37.4957, 127.0622, '강남구 대표 초등학교');

-- 19. 계약서/서류 업로드
INSERT INTO Document (user_id, doc_type, file_url, uploaded_at)
VALUES (1, '전세계약서', '/docs/lease_2024_05.pdf', NOW());

-- 20. 사기 계약서 샘플
INSERT INTO ScamContractSample (name, content, description, created_at)
VALUES ('가짜 계약서 샘플1', '여기에 사기 계약서 전문이 들어갑니다.', '전세사기 유형 샘플', NOW());

-- 21. 사기 위험 문구/패턴
INSERT INTO ScamRiskPhrase (phrase, sample_id, risk_level, description, created_at)
VALUES ('입주 전 반드시 잔금 입금 요청', 1, 8.5, '계약서에 명시된 비정상 입금 요구', NOW());


SELECT * FROM home_test.checklistquestion WHERE template_id = 2;

drop table illegal_building_check;
use home_test;
drop table illegal_building_judge;
CREATE TABLE illegal_building_judge (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '내부 식별자',
                                        plat_plc VARCHAR(255) COMMENT '대지위치',
                                        sgg_cd_nm VARCHAR(50) COMMENT '시군구코드명',
                                        stdg_cd_nm VARCHAR(50) COMMENT '법정동코드명',
                                        bdrg_sn VARCHAR(30) NOT NULL UNIQUE COMMENT '건축물대장일련번호',

                                        ldgr_se_cd_nm VARCHAR(20) COMMENT '대장구분코드명',
                                        ldgr_kind_cd_nm VARCHAR(20) COMMENT '대장종류코드명',

                                        mn_usg_cd_nm VARCHAR(50) COMMENT '주용도코드명',
                                        etc_usg_cn VARCHAR(255) COMMENT '기타용도내용',

                                        bdcvrt DECIMAL(5,2) COMMENT '건폐율',
                                        gfa DECIMAL(18,2) COMMENT '연면적',
                                        grnd_nofl INT COMMENT '지상층수',
                                        udgd_nofl INT COMMENT '지하층수',

                                        prmsn_ymd CHAR(15) COMMENT '허가일자(YYYYMMDD)',
                                        use_aprv_ymd CHAR(15) COMMENT '사용승인일자(YYYYMMDD)',

                                        rser_design_aplcn_yn CHAR(1) COMMENT '내진설계적용여부(Y/N)',

                                        judge_result VARCHAR(50) COMMENT '불법여부(자동판정결과)',
                                        judge_reason VARCHAR(255) COMMENT '불법 사유/근거'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='불법 건축물 판정에 필요한 최소 정보 테이블';


