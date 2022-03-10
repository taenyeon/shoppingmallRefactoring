CREATE TABLE "SHOP_MEMBER_TB"
(
    "MEMBER_ID"                VARCHAR2(20) primary key,
    "MEMBER_PWD"               VARCHAR2(70)  NOT NULL,
    "MEMBER_LEVEL"             VARCHAR2(50)   DEFAULT 'USER',
    "MEMBER_NAME"              VARCHAR2(20)  NOT NULL,
    "MEMBER_EMAIL"             VARCHAR2(20)  NOT NULL,
    "MEMBER_ADDRESS"           VARCHAR2(100) NOT NULL,
    "MEMBER_DETAIL_ADDRESS"    varchar2(100) not null,
    "MEMBER_TEL"               VARCHAR2(14)  NULL,
    "MEMBER_BIRTH"             DATE,
    "BUSINESS_REGISTRATION_NO" VARCHAR2(20)  NULL,
    "BUSINESS_NAME"            VARCHAR2(100) NULL,
    business_info              varchar2(2000) default '상점 정보를 입력해주세요.',
    is_delete                  varchar2(1)    default '0'
);
CREATE TABLE "SHOP_ITEM_TB"
(
    "ITEM_CODE"    NUMBER primary key,
    "MEMBER_ID"    VARCHAR2(20)   NOT NULL,
    "COUNTRY_CODE" NUMBER,
    "ITEM_NAME"    VARCHAR2(400)  NOT NULL,
    "ITEM_PRICE"   NUMBER         NOT NULL,
    "ITEM_IMAGE"   VARCHAR2(400)  NOT NULL,
    "ITEM_INFO"    VARCHAR2(4000) NULL,
    ITEM_BRAND     varchar2(30)   not null,
    BUSINESS_NAME  VARCHAR2(100)  NULL,
    is_delete      varchar2(1) default 0,
    item_hit       number      default 0
);
CREATE TABLE "SHOP_ITEM_OPTIONS_TB"
(
    "OPTION_CODE"     NUMBER primary key,
    "ITEM_CODE"       NUMBER       NOT NULL,
    "OPTION_NAME"     VARCHAR2(20) NOT NULL,
    "OPTION_PRICE_UD" NUMBER      DEFAULT 0,
    "OPTION_STOCK"    NUMBER      DEFAULT 0,
    is_delete         varchar2(1) default '0'
);

CREATE TABLE "SHOP_COUNTRY_TB"
(
    "COUNTRY_CODE"     NUMBER primary key,
    "COUNTRY_NAME"     VARCHAR2(100),
    country_post_price number(5)
);

CREATE TABLE "SHOP_REVIEW_TB"
(
    "REVIEW_CODE"    NUMBER primary key,
    "ITEM_CODE"      NUMBER,
    "MEMBER_ID"      VARCHAR2(20),
    "REVIEW_CONTENT" VARCHAR2(500) NOT NULL,
    review_star      number(1)     not null
);
CREATE TABLE "SHOP_QNABOARD_TB"
(
    "BOARD_ID"      NUMBER primary key,
    "ITEM_CODE"     NUMBER,
    "MEMBER_ID"     VARCHAR2(20),
    "BOARD_TITLE"   VARCHAR2(50)  NOT NULL,
    "BOARD_CONTENT" VARCHAR2(500) NOT NULL,
    "BOARD_REPLY" VARCHAR2(500) NULL
--     글 순서
);

CREATE TABLE "SHOP_CART_TB"
(
    "CART_ID"     NUMBER PRIMARY KEY,
    "MEMBER_ID"   VARCHAR2(50) NOT NULL,
    "OPTION_CODE" NUMBER,
    "AMOUNT"      NUMBER
);

create table shop_order_tb
(
    order_code      varchar2(50)  not null primary key,
    member_id       varchar2(20)  not null,
    total_pay       number(10)    not null,
    buyer_name      varchar2(30)  not null,
    buyer_tel       varchar2(14)  not null,
    buyer_email     varchar2(50)  not null,
    buyer_addr      varchar2(200) not null,
    buyer_post_code varchar2(9)   not null,
    is_paid         varchar2(10) default 'NotPaid',
    imp_uid         varchar2(200) null,
    paid_at         date          null,
    change          number(10)   default 0
);

create table shop_order_detail_tb
(
    order_detail_code number primary key,
    order_code        varchar2(50) not null,
    option_code       number       not null,
    amount            number       not null,
    posted_status     varchar2(10) default 'NotPaid', -- NotPaid , Paid , Refund, Ready , Posted , Done
    order_price       number(10)   not null,
    post_price        number(10)
);

CREATE SEQUENCE CODE_COUNTRY_SQ;
CREATE SEQUENCE CODE_REVIEW_SQ;
CREATE SEQUENCE CODE_ITEM_SQ;
CREATE SEQUENCE CODE_ITEM_OPTIONS_SQ;
CREATE SEQUENCE ID_QNABOARD_SQ;
create sequence CODE_ORDER_DETAIL_SQ;
create sequence CODE_CARD_SQ;
create sequence CODE_ORDER_DETAIL_SQ;
create sequence board_id_sq;
ALTER TABLE "SHOP_ITEM_TB"
    ADD CONSTRAINT "FK_MEMBER_TO_ITEM" FOREIGN KEY (
                                                    "MEMBER_ID"
        )
        REFERENCES "SHOP_MEMBER_TB" (
                                     "MEMBER_ID"
            );

ALTER TABLE "SHOP_ITEM_TB"
    ADD CONSTRAINT "FK_COUNTRY_TO_ITEM" FOREIGN KEY (
                                                     "COUNTRY_CODE"
        )
        REFERENCES "SHOP_COUNTRY_TB" (
                                      "COUNTRY_CODE"
            );

ALTER TABLE "SHOP_ITEM_OPTIONS_TB"
    ADD CONSTRAINT "FK_ITEM_TO_ITEM_OPTIONS" FOREIGN KEY (
                                                          "ITEM_CODE"
        )
        REFERENCES "SHOP_ITEM_TB" (
                                   "ITEM_CODE"
            );

ALTER TABLE "SHOP_REVIEW_TB"
    ADD CONSTRAINT "FK_ITEM_TO_REVIEW" FOREIGN KEY (
                                                    "ITEM_CODE"
        )
        REFERENCES "SHOP_ITEM_TB" (
                                   "ITEM_CODE"
            );

ALTER TABLE "SHOP_REVIEW_TB"
    ADD CONSTRAINT "FK_MEMBER_TO_REVIEW" FOREIGN KEY (
                                                      "MEMBER_ID"
        )
        REFERENCES "SHOP_MEMBER_TB" (
                                     "MEMBER_ID"
            );

ALTER TABLE "SHOP_QNABOARD_TB"
    ADD CONSTRAINT "FK_ITEM_TO_QNABOARD" FOREIGN KEY (
                                                      "ITEM_CODE"
        )
        REFERENCES "SHOP_ITEM_TB" (
                                   "ITEM_CODE"
            );

ALTER TABLE "SHOP_QNABOARD_TB"
    ADD CONSTRAINT "FK_MEMBER_TO_QNABOARD" FOREIGN KEY (
                                                        "MEMBER_ID"
        )
        REFERENCES "SHOP_MEMBER_TB" (
                                     "MEMBER_ID"
            );
