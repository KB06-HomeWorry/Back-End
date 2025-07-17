create database home_test;

use home_test;

-- 사용자 정보
CREATE TABLE User (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50),
                      email VARCHAR(100) UNIQUE,
                      password VARCHAR(200),
                      phone VARCHAR(20),
                      user_type VARCHAR(20)
);

CREATE TABLE user_member_auth (
                                  auth_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  user_id BIGINT NOT NULL,
                                  auth VARCHAR(50) NOT NULL,
                                  FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- 공인중개사 정보
CREATE TABLE Agent (
                       agent_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       name VARCHAR(50),
                       office_name VARCHAR(100),
                       license_number VARCHAR(50) UNIQUE,
                       profile_image VARCHAR(255),
                       specialties VARCHAR(200),
                       badge BOOLEAN,
                       description TEXT,
                       FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- 중개사 사무소 정보
CREATE TABLE AgencyOffice (
                              office_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(100),
                              address VARCHAR(255),
                              lat DOUBLE,
                              lng DOUBLE,
                              license_number VARCHAR(50) UNIQUE,
                              phone VARCHAR(20)
);

-- 매물 정보
CREATE TABLE Listing (
                         listing_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         office_id BIGINT NOT NULL,
                         agent_id BIGINT NOT NULL,
                         address VARCHAR(255),
                         lat DOUBLE,
                         lng DOUBLE,
                         price BIGINT,
                         sale_type VARCHAR(20),
                         description TEXT,
                         images TEXT,
                         FOREIGN KEY (office_id) REFERENCES AgencyOffice(office_id),
                         FOREIGN KEY (agent_id) REFERENCES Agent(agent_id)
);

-- 매물 상세 정보
CREATE TABLE ListingDetail (
                               detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               listing_id BIGINT NOT NULL,
                               area_m2 FLOAT,
                               floor INT,
                               total_floor INT,
                               room_count INT,
                               bath_count INT,
                               built_year INT,
                               heating_type VARCHAR(50),
                               direction VARCHAR(20),
                               parking BOOLEAN,
                               elevator BOOLEAN,
                               loan_available BOOLEAN,
                               management_fee INT,
                               entrance_type VARCHAR(20),
                               FOREIGN KEY (listing_id) REFERENCES Listing(listing_id)
);

-- 매물 옵션 정보
CREATE TABLE ListingOption (
                               option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               listing_id BIGINT NOT NULL,
                               option_name VARCHAR(50),
                               included BOOLEAN,
                               FOREIGN KEY (listing_id) REFERENCES Listing(listing_id)
);

-- 매물 가격 이력
CREATE TABLE ListingPriceHistory (
                                     price_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     listing_id BIGINT NOT NULL,
                                     price BIGINT,
                                     price_type VARCHAR(20),
                                     start_date DATE,
                                     end_date DATE,
                                     reg_date DATETIME,
                                     note TEXT,
                                     FOREIGN KEY (listing_id) REFERENCES Listing(listing_id)
);

-- 거래 이력
CREATE TABLE Deal (
                      deal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      listing_id BIGINT NOT NULL,
                      agent_id BIGINT NOT NULL,
                      deal_status VARCHAR(20),
                      stage VARCHAR(20),
                      FOREIGN KEY (user_id) REFERENCES User(user_id),
                      FOREIGN KEY (listing_id) REFERENCES Listing(listing_id),
                      FOREIGN KEY (agent_id) REFERENCES Agent(agent_id)
);

-- 계약서/서류 파일
CREATE TABLE Document (
                          document_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          deal_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          doc_type VARCHAR(30),
                          file_url VARCHAR(255),
                          uploaded_at DATETIME,
                          FOREIGN KEY (deal_id) REFERENCES Deal(deal_id),
                          FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- 체크리스트 템플릿
CREATE TABLE ChecklistTemplate (
                                   template_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   name VARCHAR(50),
                                   sale_type VARCHAR(20),
                                   stage VARCHAR(20)
);

-- 체크리스트 질문
CREATE TABLE ChecklistQuestion (
                                   question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   template_id BIGINT NOT NULL,
                                   content TEXT,
                                   type VARCHAR(20),
                                   order_num INT,
                                   risk_weight INT,
                                   FOREIGN KEY (template_id) REFERENCES ChecklistTemplate(template_id)
);

-- 체크리스트 이력
CREATE TABLE Checklist (
                           checklist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           deal_id BIGINT NOT NULL,
                           user_id BIGINT NOT NULL,
                           template_id BIGINT NOT NULL,
                           completed_at DATETIME,
                           FOREIGN KEY (deal_id) REFERENCES Deal(deal_id),
                           FOREIGN KEY (user_id) REFERENCES User(user_id),
                           FOREIGN KEY (template_id) REFERENCES ChecklistTemplate(template_id)
);

-- 체크리스트 답변
CREATE TABLE ChecklistUserAnswer (
                                     answer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     checklist_id BIGINT NOT NULL,
                                     question_id BIGINT NOT NULL,
                                     user_id BIGINT NOT NULL,
                                     answer TEXT,
                                     answered_at DATETIME,
                                     FOREIGN KEY (checklist_id) REFERENCES Checklist(checklist_id),
                                     FOREIGN KEY (question_id) REFERENCES ChecklistQuestion(question_id),
                                     FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- 중개사 리뷰
CREATE TABLE AgentReview (
                             review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             agent_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             rating INT,
                             content TEXT,
                             created_at DATETIME,
                             FOREIGN KEY (agent_id) REFERENCES Agent(agent_id),
                             FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- 중개사 이미지(프로필/자격증/활동 등)
CREATE TABLE AgentImage (
                            image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            agent_id BIGINT NOT NULL,
                            image_url VARCHAR(255),
                            image_type VARCHAR(20),
                            description TEXT,
                            uploaded_at DATETIME,
                            FOREIGN KEY (agent_id) REFERENCES Agent(agent_id)
);

-- 인프라 정보
CREATE TABLE InfraInfo (
                           infra_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(50),
                           type VARCHAR(30),
                           address VARCHAR(255),
                           lat DOUBLE,
                           lng DOUBLE,
                           detail TEXT
);

-- 사기 계약서 샘플
CREATE TABLE ScamContractSample (
                                    sample_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100),
                                    content TEXT,
                                    description TEXT,
                                    created_at DATETIME
);

-- 사기 위험 문구
CREATE TABLE ScamRiskPhrase (
                                phrase_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                phrase VARCHAR(255),
                                sample_id BIGINT,
                                risk_level FLOAT,
                                description TEXT,
                                created_at DATETIME,
                                FOREIGN KEY (sample_id) REFERENCES ScamContractSample(sample_id)
);

-- 점수/등급별 안내문구/설명/이미지 관리
CREATE TABLE RiskScoreMessage (
                                  grade VARCHAR(20) PRIMARY KEY,
                                  min_score FLOAT,
                                  max_score FLOAT,
                                  message TEXT,
                                  description TEXT,
                                  image_url VARCHAR(255)
);

-- 1. 사용자(User)
INSERT INTO User (name, email, password, phone, user_type)
VALUES
    ('홍길동', 'hong@test.com', 'pwenc1', '010-1111-2222', '일반'),
    ('김중개', 'agentkim@test.com', 'pwenc2', '010-2222-3333', '중개사');

-- user_member_auth (회원 권한/역할 부여 예시)
INSERT INTO user_member_auth (user_id, auth)
VALUES
    (1, 'ROLE_USER'),    -- 일반 사용자(홍길동)
    (2, 'ROLE_AGENT'),   -- 중개사(김중개)
    (2, 'ROLE_USER');    -- 중개사도 사용자 권한 동시 부여


-- 2. 공인중개사(Agent)
INSERT INTO Agent (user_id, name, office_name, license_number, profile_image, specialties, badge, description)
VALUES
    (2, '김중개', '강남스마트부동산', 'A12345678', '/img/profile1.jpg', '#아파트 #오피스텔', 1, '강남권 부동산 전문 중개사입니다.');

-- 3. 중개사 사무소(AgencyOffice)
INSERT INTO AgencyOffice (name, address, lat, lng, license_number, phone)
VALUES
    ('강남스마트부동산', '서울 강남구 테헤란로 1', 37.4981, 127.0276, 'A12345678', '02-555-8888');

-- 4. 매물(Listing)
INSERT INTO Listing (office_id, agent_id, address, lat, lng, price, sale_type, description, images)
VALUES
    (1, 1, '서울 강남구 테헤란로 5', 37.4985, 127.0280, 850000000, '매매', '강남역 도보 5분 아파트', '/img/apt1.jpg,/img/apt1_2.jpg');

-- 5. 매물 상세(ListingDetail)
INSERT INTO ListingDetail (listing_id, area_m2, floor, total_floor, room_count, bath_count, built_year, heating_type, direction, parking, elevator, loan_available, management_fee, entrance_type)
VALUES
    (1, 84.7, 11, 20, 3, 2, 2018, '개별', '남향', 1, 1, 1, 110000, '계단식');

-- 6. 매물 옵션(ListingOption)
INSERT INTO ListingOption (listing_id, option_name, included)
VALUES
    (1, '에어컨', 1),
    (1, '냉장고', 1),
    (1, '붙박이장', 0);

-- 7. 매물 가격 이력(ListingPriceHistory)
INSERT INTO ListingPriceHistory (listing_id, price, price_type, start_date, end_date, reg_date, note)
VALUES
    (1, 850000000, '매매가', '2024-06-01', NULL, NOW(), '현재가'),
    (1, 830000000, '매매가', '2024-05-01', '2024-05-31', '2024-05-01 09:00:00', '이전 시세');

-- 8. 거래(Deal)
INSERT INTO Deal (user_id, listing_id, agent_id, deal_status, stage)
VALUES
    (1, 1, 1, '진행', '계약전');

-- 9. 계약서/서류(Document)
INSERT INTO Document (deal_id, user_id, doc_type, file_url, uploaded_at)
VALUES
    (1, 1, '계약서', '/doc/contract_1.pdf', NOW());

-- 10. 체크리스트 템플릿(ChecklistTemplate)
INSERT INTO ChecklistTemplate (name, sale_type, stage)
VALUES
    ('전세 입금 전 체크리스트', '전세', '입금 전'),
    ('매매 계약전 체크리스트', '매매', '계약전');

-- 11. 체크리스트 질문(ChecklistQuestion)
INSERT INTO ChecklistQuestion (template_id, content, type, order_num, risk_weight)
VALUES
    (1, '등기부등본상 소유자와 계약자가 일치합니까?', '객관식', 1, 10),
    (1, '입금 계좌가 안전한지 확인하셨나요?', '객관식', 2, 20),
    (2, '매매계약서 특약을 모두 읽어보셨나요?', '객관식', 1, 5);

-- 12. 체크리스트(Checklist)
INSERT INTO Checklist (deal_id, user_id, template_id, completed_at)
VALUES
    (1, 1, 1, NOW());

-- 13. 체크리스트 답변(ChecklistUserAnswer)
INSERT INTO ChecklistUserAnswer (checklist_id, question_id, user_id, answer, answered_at)
VALUES
    (1, 1, 1, '예', NOW()),
    (1, 2, 1, '아니오', NOW());

-- 14. 중개사 리뷰(AgentReview)
INSERT INTO AgentReview (agent_id, user_id, rating, content, created_at)
VALUES
    (1, 1, 5, '매우 친절하고 신속하게 처리해주셨습니다!', NOW());

-- 15. 중개사 이미지(AgentImage)
INSERT INTO AgentImage (agent_id, image_url, image_type, description, uploaded_at)
VALUES
    (1, '/img/cert1.jpg', '자격증', '공인중개사 자격증', NOW()),
    (1, '/img/office1.jpg', '사무소', '사무소 외관', NOW());

-- 16. 인프라 정보(InfraInfo)
INSERT INTO InfraInfo (name, type, address, lat, lng, detail)
VALUES
    ('역삼 CCTV 1', 'CCTV', '서울 강남구 역삼동 1-1', 37.4990, 127.0310, '횡단보도 앞'),
    ('GS25 강남점', '편의시설', '서울 강남구 역삼동 2-1', 37.4995, 127.0320, '24시간 운영'),
    ('역삼파출소', '치안시설', '서울 강남구 역삼동 3-1', 37.4980, 127.0300, '파출소 위치');

-- 17. 사기 계약서 샘플(ScamContractSample)
INSERT INTO ScamContractSample (name, content, description, created_at)
VALUES
    ('입금 계좌 사기 샘플', '...계좌 불일치...', '입금 계좌를 실제 소유주와 다르게 기재', NOW());

-- 18. 사기 위험 문구(ScamRiskPhrase)
INSERT INTO ScamRiskPhrase (phrase, sample_id, risk_level, description, created_at)
VALUES
    ('입금 계좌가 계약자와 다릅니다', 1, 0.9, '입금 계좌 불일치', NOW());

-- 19. 점수/등급별 안내문구/설명/이미지(RiskScoreMessage)
INSERT INTO RiskScoreMessage (grade, min_score, max_score, message, description, image_url)
VALUES
    ('Low', 0, 30, '안전합니다.', '위험 요소가 없습니다.', '/img/low.png'),
    ('Medium', 31, 70, '주의가 필요합니다.', '일부 위험 신호가 있습니다.', '/img/medium.png'),
    ('High', 71, 100, '전문가 상담이 필요합니다.', '위험도가 높습니다.', '/img/high.png');


SELECT checklist_id
FROM home_test.checklist
WHERE user_id = 1 AND template_id = 1;

SELECT * FROM home_test.checklist WHERE user_id = 1 AND template_id = 1;

SELECT checklist_id FROM home_test.checklist WHERE user_id = 1 AND template_id = 4;

DELETE from home_test.checklistuseranswer where checklist_id = 13 and user_id = 1;
DELETE  from home_test.checklist where checklist_id = 15;