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
package org.kuali.kra.coi.notification;

import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.common.notification.service.KcNotificationRoleQualifierService;

public interface CoiNotificationRoleQualifierService extends KcNotificationRoleQualifierService {

    public void setCoiDisclosure(CoiDisclosure coiDisclosure);
    public CoiDisclosure getCoiDisclosure();
        
}
