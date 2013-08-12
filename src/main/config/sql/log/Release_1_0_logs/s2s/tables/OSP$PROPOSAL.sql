CREATE TABLE OSP$PROPOSAL ( 
  PROPOSAL_NUMBER               VARCHAR2 (8)  NOT NULL, 
  SPONSOR_PROPOSAL_NUMBER       VARCHAR2 (70), 
  SEQUENCE_NUMBER               NUMBER (4)    NOT NULL, 
  PROPOSAL_TYPE_CODE            NUMBER (3)    NOT NULL, 
  CURRENT_ACCOUNT_NUMBER        CHAR (7), 
  TITLE                         VARCHAR2 (150)  NOT NULL, 
  SPONSOR_CODE                  CHAR (6)      NOT NULL, 
  ROLODEX_ID                    NUMBER (6), 
  NOTICE_OF_OPPORTUNITY_CODE    NUMBER (3), 
  GRAD_STUD_HEADCOUNT           NUMBER (3), 
  GRAD_STUD_PERSON_MONTHS       NUMBER (5,2), 
  TYPE_OF_ACCOUNT               CHAR (1), 
  ACTIVITY_TYPE_CODE            NUMBER (3)    NOT NULL, 
  REQUESTED_START_DATE_INITIAL  DATE, 
  REQUESTED_START_DATE_TOTAL    DATE, 
  REQUESTED_END_DATE_INITIAL    DATE, 
  REQUESTED_END_DATE_TOTAL      DATE, 
  TOTAL_DIRECT_COST_INITIAL     NUMBER (12,2), 
  TOTAL_DIRECT_COST_TOTAL       NUMBER (12,2), 
  TOTAL_INDIRECT_COST_INITIAL   NUMBER (12,2), 
  TOTAL_INDIRECT_COST_TOTAL     NUMBER (12,2), 
  NUMBER_OF_COPIES              VARCHAR2 (7), 
  DEADLINE_DATE                 DATE, 
  DEADLINE_TYPE                 CHAR (1), 
  MAIL_BY                       CHAR (1), 
  MAIL_TYPE                     CHAR (1), 
  MAIL_ACCOUNT_NUMBER           CHAR (7), 
  SUBCONTRACT_FLAG              CHAR (1)      NOT NULL, 
  COST_SHARING_INDICATOR        CHAR (2)      NOT NULL, 
  IDC_RATE_INDICATOR            CHAR (2)      NOT NULL, 
  SPECIAL_REVIEW_INDICATOR      CHAR (2)      NOT NULL, 
  STATUS_CODE                   NUMBER (3)    NOT NULL, 
  SCIENCE_CODE_INDICATOR        CHAR (2)      NOT NULL, 
  NSF_CODE                      VARCHAR2 (15), 
  PRIME_SPONSOR_CODE            CHAR (6), 
  CREATE_TIMESTAMP              DATE          NOT NULL, 
  INITIAL_CONTRACT_ADMIN        VARCHAR2 (9), 
  IP_REVIEW_REQ_TYPE_CODE       NUMBER (3), 
  REVIEW_SUBMISSION_DATE        DATE, 
  REVIEW_RECEIVE_DATE           DATE, 
  REVIEW_RESULT_CODE            NUMBER (3), 
  IP_REVIEWER                   VARCHAR2 (9), 
  IP_REVIEW_ACTIVITY_INDICATOR  CHAR (2)      NOT NULL, 
  CURRENT_AWARD_NUMBER          CHAR (10), 
  CFDA_NUMBER                   VARCHAR2 (6), 
  OPPORTUNITY                   VARCHAR2 (50), 
  UPDATE_TIMESTAMP              DATE          NOT NULL, 
  UPDATE_USER                   VARCHAR2 (8)  NOT NULL, 
  CONSTRAINT PK_PROPOSAL_KRA
  PRIMARY KEY ( PROPOSAL_NUMBER, SEQUENCE_NUMBER ) ) ; 