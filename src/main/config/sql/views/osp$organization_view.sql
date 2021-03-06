create or replace view OSP$ORGANIZATION as
 select ORGANIZATION_ID, ORGANIZATION_NAME, CONTACT_ADDRESS_ID, ADDRESS,                       
 CABLE_ADDRESS, TELEX_NUMBER, COUNTY, CONGRESSIONAL_DISTRICT, INCORPORATED_IN,               
 INCORPORATED_DATE, NUMBER_OF_EMPLOYEES, IRS_TAX_EXCEMPTION, FEDRAL_EMPLOYER_ID,            
 MASS_TAX_EXCEMPT_NUM,AGENCY_SYMBOL, VENDOR_CODE, COM_GOV_ENTITY_CODE,           
 MASS_EMPLOYEE_CLAIM, DUNS_NUMBER, DUNS_PLUS_FOUR_NUMBER, DODAC_NUMBER,                  
 CAGE_NUMBER, HUMAN_SUB_ASSURANCE, ANIMAL_WELFARE_ASSURANCE,  SCIENCE_MISCONDUCT_COMPL_DATE, 
 PHS_ACOUNT, NSF_INSTITUTIONAL_CODE, INDIRECT_COST_RATE_AGREEMENT, COGNIZANT_AUDITOR,             
 ONR_RESIDENT_REP, UPDATE_TIMESTAMP,  UPDATE_USER           
 from ORGANIZATION;
