alter table AWARD_COMMENT modify award_number varchar2(12);
alter table AWARD_COST_SHARE modify award_number varchar2(12);
alter table AWARD_PAYMENT_SCHEDULE modify award_number varchar2(12);
alter table AWARD_APPROVED_EQUIPMENT modify award_number varchar2(12);
alter table AWARD_REPORT_TERMS modify award_number varchar2(12);
alter table AWARD modify award_number varchar2(12);
alter table AWARD_APPROVED_FOREIGN_TRAVEL modify award_number varchar2(12);
alter table AWARD_UNIT_CONTACTS modify award_number varchar2(12);
alter table AWARD_IDC_RATE modify award_number varchar2(12);
alter table AWARD_APPROVED_SUBAWARDS modify award_number varchar2(12);
alter table AWARD_SPONSOR_TERM modify award_number varchar2(12);
alter table AWARD_PERSONS modify award_number varchar2(12);
alter table AWARD_CLOSEOUT modify award_number varchar2(12);
alter table AWARD_AMT_FNA_DISTRIBUTION modify award_number varchar2(12);
alter table AWARD_TRANSFERRING_SPONSOR modify award_number varchar2(12);
alter table AWARD_AMOUNT_INFO modify award_number varchar2(12);
alter table AWARD_SPONSOR_CONTACTS modify award_number varchar2(12);
alter table AWARD_CUSTOM_DATA modify award_number varchar2(12);
commit;