-- View for Coeus compatibility 
CREATE OR REPLACE VIEW OSP$PROPOSAL_NOTEPAD AS SELECT 
    PROPOSAL_NUMBER, 
    ENTRY_NUMBER, 
    COMMENTS, 
    RESTRICTED_VIEW, 
    UPDATE_TIMESTAMP, 
    UPDATE_USER
FROM PROPOSAL_NOTEPAD;
