CREATE OR REPLACE VIEW OSP$COST_SHARING_TYPE AS SELECT 
	COST_SHARE_TYPE_CODE COST_SHARING_TYPE_CODE,
	DESCRIPTION DESCRIPTION,
	UPDATE_TIMESTAMP UPDATE_TIMESTAMP, 
	UPDATE_USER UPDATE_USER
FROM COST_SHARE_TYPE