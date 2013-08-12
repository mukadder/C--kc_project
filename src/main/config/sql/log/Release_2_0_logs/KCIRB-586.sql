INSERT INTO KRIM_PERM_T (PERM_ID, PERM_TMPL_ID, NM, DESC_TXT, ACTV_IND, NMSPC_CD, OBJ_ID) 
VALUES (KRIM_PERM_ID_S.nextVal, 16, 'Modify Correspondence Template', null, 'Y', 'KC-PROTOCOL', SYS_GUID());

INSERT INTO KRIM_ROLE_PERM_T(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, OBJ_ID) 
VALUES (KRIM_ROLE_PERM_ID_S.nextval, 1119, KRIM_PERM_ID_S.currVal, 'Y', SYS_GUID());

INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
VALUES(KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), '1', KRIM_PERM_ID_S.currVal, '3', '13', 'ProtocolCorrespondenceTemplateMaintenanceDocument') ;


INSERT INTO KRIM_PERM_T (PERM_ID, PERM_TMPL_ID, NM, DESC_TXT, ACTV_IND, NMSPC_CD, OBJ_ID) 
VALUES (KRIM_PERM_ID_S.nextVal, 40, 'View Correspondence Template', null, 'Y', 'KC-PROTOCOL', SYS_GUID());

INSERT INTO KRIM_ROLE_PERM_T(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, OBJ_ID) 
VALUES (KRIM_ROLE_PERM_ID_S.nextval, 1120, KRIM_PERM_ID_S.currVal, 'Y', SYS_GUID());

INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
VALUES(KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), '1', KRIM_PERM_ID_S.currVal, '3', '13', 'ProtocolCorrespondenceTemplateMaintenanceDocument') ;

commit;