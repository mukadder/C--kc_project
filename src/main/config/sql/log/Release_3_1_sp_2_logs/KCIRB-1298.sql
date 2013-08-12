-- ORACLE
ALTER TABLE RESEARCH_AREAS ADD (ACTIVE_FLAG VARCHAR2(1));
UPDATE RESEARCH_AREAS SET ACTIVE_FLAG = 'Y';
ALTER TABLE RESEARCH_AREAS MODIFY (ACTIVE_FLAG NOT NULL);

-- MySQL
ALTER TABLE RESEARCH_AREAS ADD ACTIVE_FLAG VARCHAR(1);
UPDATE RESEARCH_AREAS SET ACTIVE_FLAG = 'Y';
ALTER TABLE RESEARCH_AREAS MODIFY ACTIVE_FLAG VARCHAR(1) NOT NULL;
