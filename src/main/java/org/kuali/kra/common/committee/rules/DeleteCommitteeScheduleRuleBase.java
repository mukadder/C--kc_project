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
package org.kuali.kra.common.committee.rules;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kra.common.committee.bo.CommitteeBase;
import org.kuali.kra.common.committee.bo.CommitteeScheduleBase;
import org.kuali.kra.common.committee.document.CommitteeDocumentBase;
import org.kuali.kra.common.committee.rule.event.DeleteCommitteeScheduleEventBase;
import org.kuali.kra.common.committee.service.CommitteeServiceBase;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.rule.BusinessRuleInterface;
import org.kuali.kra.rules.ResearchDocumentRuleBase;

/**
 * 
 * This class implements rule for deleting committee schedule.
 */
public abstract class DeleteCommitteeScheduleRuleBase  extends ResearchDocumentRuleBase implements  BusinessRuleInterface<DeleteCommitteeScheduleEventBase> {
    
    private static final String ID = "document.committeeList[0].committeeSchedules[";
   
    /**
     * If committee schedule has protocol submitted or has meeting data, then it can not be deleted.
     * @see org.kuali.kra.rule.BusinessRuleInterface#processRules(org.kuali.kra.rule.event.KraDocumentEventBaseExtension)
     */
    public boolean processRules(DeleteCommitteeScheduleEventBase deleteCommitteeScheduleEvent) {

        boolean rulePassed = true;
        List<CommitteeScheduleBase> schedules = deleteCommitteeScheduleEvent.getCommitteeSchedules();
        CommitteeBase activeCommittee = getCommitteeService().getCommitteeById(
                ((CommitteeDocumentBase) deleteCommitteeScheduleEvent.getDocument()).getCommittee().getCommitteeId());
        if (activeCommittee != null) {
            int i = 0;
            for (CommitteeScheduleBase schedule : schedules) {
                if (schedule.isSelected() && canNotDelete(activeCommittee.getCommitteeSchedules(), schedule.getScheduleId())) {
                    reportError(ID + i + "].selected", KeyConstants.ERROR_COMMITTEESCHEDULE_DELETE);
                    rulePassed = false;
                }
                i++;
            }
        }
        return rulePassed;
    }

    /*
     * check if the matching schedule contain meeting data which also include whether protocol submitted to this schedule.
     */
    private boolean canNotDelete(List<CommitteeScheduleBase> schedules, String scheduleId) {
        for (CommitteeScheduleBase committeeSchedule : schedules) {
            if (StringUtils.equals(committeeSchedule.getScheduleId(), scheduleId)) {
                return isNotEmptyData(committeeSchedule);
            }
        }
        return false;
    }

    private CommitteeServiceBase getCommitteeService() {
        return KraServiceLocator.getService(getCommitteeServiceClassHook());
    }
    
    protected abstract Class<? extends CommitteeServiceBase> getCommitteeServiceClassHook();

    /*
     * check if there is any meeting data in this schedule.
     */
    private boolean isNotEmptyData(CommitteeScheduleBase schedule) {
        return CollectionUtils.isNotEmpty(schedule.getCommitteeScheduleAttendances())
                || CollectionUtils.isNotEmpty(schedule.getCommitteeScheduleMinutes())
                || CollectionUtils.isNotEmpty(schedule.getCommScheduleActItems())
                || CollectionUtils.isNotEmpty(schedule.getMinuteDocs())
                || CollectionUtils.isNotEmpty(schedule.getScheduleAgendas())
                || CollectionUtils.isNotEmpty(schedule.getLatestProtocolSubmissions());

    }

}
