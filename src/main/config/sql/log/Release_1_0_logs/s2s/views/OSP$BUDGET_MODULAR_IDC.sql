
CREATE OR REPLACE VIEW osp$budget_modular_idc ( PROPOSAL_NUMBER, VERSION_NUMBER, BUDGET_PERIOD, RATE_NUMBER, DESCRIPTION, IDC_RATE, IDC_BASE, FUNDS_REQUESTED, UPDATE_TIMESTAMP, UPDATE_USER ) 
AS select PROPOSAL_NUMBER, VERSION_NUMBER, BUDGET_PERIOD, RATE_NUMBER, DESCRIPTION, IDC_RATE, IDC_BASE, FUNDS_REQUESTED, UPDATE_TIMESTAMP, UPDATE_USER
	from budget_modular_idc;
