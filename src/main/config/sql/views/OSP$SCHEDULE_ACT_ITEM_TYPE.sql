-- View for Coeus compatibility 
CREATE OR REPLACE VIEW OSP$SCHEDULE_ACT_ITEM_TYPE AS SELECT 
    SCHEDULE_ACT_ITEM_TYPE_CODE, 
    DESCRIPTION, 
    UPDATE_TIMESTAMP, 
    UPDATE_USER
FROM SCHEDULE_ACT_ITEM_TYPE;
