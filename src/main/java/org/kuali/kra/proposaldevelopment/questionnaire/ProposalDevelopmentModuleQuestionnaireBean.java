/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.proposaldevelopment.questionnaire;

import java.util.HashMap;
import java.util.Map;

import org.kuali.kra.bo.CoeusModule;
import org.kuali.kra.bo.CoeusSubModule;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.krms.KrmsRulesContext;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;
import org.kuali.kra.questionnaire.answer.ModuleQuestionnaireBean;
import org.kuali.rice.krad.service.BusinessObjectService;

public class ProposalDevelopmentModuleQuestionnaireBean extends ModuleQuestionnaireBean {
    
    private DevelopmentProposal developmentProposal;
    
    public ProposalDevelopmentModuleQuestionnaireBean(DevelopmentProposal developmentProposal) {
        super(CoeusModule.PROPOSAL_DEVELOPMENT_MODULE_CODE, developmentProposal.getProposalNumber(), CoeusSubModule.ZERO_SUBMODULE, "0", 
                developmentProposal.getProposalDocument().getDocumentHeader().hasWorkflowDocument() ? developmentProposal.getProposalDocument().getDocumentHeader().getWorkflowDocument().isApproved() : true);
        this.developmentProposal = developmentProposal;
    }
    
    public ProposalDevelopmentModuleQuestionnaireBean(DevelopmentProposal developmentProposal, boolean finalDoc) {
        this(developmentProposal);
        setFinalDoc(finalDoc);
    }
    
    public ProposalDevelopmentModuleQuestionnaireBean(String moduleItemCode, String moduleItemKey, String moduleSubItemCode, String moduleSubItemKey, boolean finalDoc) {
        super(moduleItemCode, moduleItemKey, moduleSubItemCode, moduleSubItemKey, finalDoc);
    }

    @Override
    public KrmsRulesContext getKrmsRulesContextFromBean() {
        if (developmentProposal != null) {
            return developmentProposal.getKrmsRulesContext();
        } else {
            return loadKrmsRulesContext(getModuleItemKey());

        }
    }
    
    protected KrmsRulesContext loadKrmsRulesContext(String proposalNumber) {
        DevelopmentProposal proposal = KraServiceLocator.getService(BusinessObjectService.class).findBySinglePrimaryKey(DevelopmentProposal.class, proposalNumber);
        return proposal.getKrmsRulesContext();
    }

    public DevelopmentProposal getDevelopmentProposal() {
        return developmentProposal;
    }

    public void setDevelopmentProposal(DevelopmentProposal developmentProposal) {
        this.developmentProposal = developmentProposal;
    }
    
}
