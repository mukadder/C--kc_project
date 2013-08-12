/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.proposaldevelopment.rule;

import org.kuali.kra.proposaldevelopment.rule.event.ProposalDataOverrideEvent;
import org.kuali.rice.krad.rules.rule.BusinessRule;

/**
 * The Copy Proposal Rule validates copying of a proposal.
 *
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public interface ProposalDataOverrideRule extends BusinessRule {
    
    /**
     * Validates the Overriding of a proposal data.  
     * 
     * @param document the proposal development document
     * @param proposalChangedData the user-specified overriding data
     * @return true if the update request is valid; otherwise false
     */
    public boolean processProposalDataOverrideRules(ProposalDataOverrideEvent proposalDataOverrideEvent);
}
