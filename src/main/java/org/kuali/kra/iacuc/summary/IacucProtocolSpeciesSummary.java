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
package org.kuali.kra.iacuc.summary;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.iacuc.species.IacucProtocolSpecies;

public class IacucProtocolSpeciesSummary implements Serializable { 

    private static final long serialVersionUID = 9204826126972393444L;

    private Integer speciesId; 
    private String  species;
    private boolean speciesChanged;
    private String  speciesGroup; 
    private boolean speciesGroupChanged;
    private boolean usdaCovered; 
    private boolean usdaCoveredChanged;
    private String  strain; 
    private boolean strainChanged;
    private int     speciesCount; 
    private boolean speciesCountChanged;
    private String  speciesCountType;
    private boolean speciesCountTypeChanged;
    private String  painCategory;
    private boolean painCategoryChanged;
    private String  procedureSummary;
    private boolean procedureSummaryChanged;
    
    public IacucProtocolSpeciesSummary(IacucProtocolSpecies iacucSpecies) {
        speciesId = iacucSpecies.getSpeciesId();
        species = iacucSpecies.getSpeciesName();
        speciesGroup = iacucSpecies.getSpeciesGroup(); 
        usdaCovered = iacucSpecies.getUsdaCovered();
        strain = iacucSpecies.getStrain();
        speciesCount = iacucSpecies.getSpeciesCount().intValue();
        speciesCountType = iacucSpecies.getCountTypeName();
        painCategory = iacucSpecies.getPainCategoryName();
        procedureSummary = iacucSpecies.getProcedureSummary();
    }
    public Integer getSpeciesId() {
        return speciesId;
    }
    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }
    public String getSpecies() {
        return species;
    }
    public void setSpecies(String species) {
        this.species = species;
    }
    public boolean isSpeciesChanged() {
        return speciesChanged;
    }
    public void setSpeciesChanged(boolean speciesChanged) {
        this.speciesChanged = speciesChanged;
    }
    public String getSpeciesGroup() {
        return speciesGroup;
    }
    public void setSpeciesGroup(String speciesGroup) {
        this.speciesGroup = speciesGroup;
    }
    public boolean isSpeciesGroupChanged() {
        return speciesGroupChanged;
    }
    public void setSpeciesGroupChanged(boolean speciesGroupChanged) {
        this.speciesGroupChanged = speciesGroupChanged;
    }
    public boolean isUsdaCovered() {
        return usdaCovered;
    }
    public void setUsdaCovered(boolean usdaCovered) {
        this.usdaCovered = usdaCovered;
    }
    public boolean isUsdaCoveredChanged() {
        return usdaCoveredChanged;
    }
    public void setUsdaCoveredChanged(boolean usdaCoveredChanged) {
        this.usdaCoveredChanged = usdaCoveredChanged;
    }
    public String getStrain() {
        return strain;
    }
    public void setStrain(String strain) {
        this.strain = strain;
    }
    public boolean isStrainChanged() {
        return strainChanged;
    }
    public void setStrainChanged(boolean strainChanged) {
        this.strainChanged = strainChanged;
    }
    public int getSpeciesCount() {
        return speciesCount;
    }
    public void setSpeciesCount(int speciesCount) {
        this.speciesCount = speciesCount;
    }
    public boolean isSpeciesCountChanged() {
        return speciesCountChanged;
    }
    public void setSpeciesCountChanged(boolean speciesCountChanged) {
        this.speciesCountChanged = speciesCountChanged;
    }
    public String getSpeciesCountType() {
        return speciesCountType;
    }
    public void setSpeciesCountType(String speciesCountType) {
        this.speciesCountType = speciesCountType;
    }
    public boolean isSpeciesCountTypeChanged() {
        return speciesCountTypeChanged;
    }
    public void setSpeciesCountTypeChanged(boolean speciesCountTypeChanged) {
        this.speciesCountTypeChanged = speciesCountTypeChanged;
    }
    public String getPainCategory() {
        return painCategory;
    }
    public void setPainCategory(String painCategory) {
        this.painCategory = painCategory;
    }
    public boolean isPainCategoryChanged() {
        return painCategoryChanged;
    }
    public void setPainCategoryChanged(boolean painCategoryChanged) {
        this.painCategoryChanged = painCategoryChanged;
    }
    public String getProcedureSummary() {
        return procedureSummary;
    }
    public void setProcedureSummary(String procedureSummary) {
        this.procedureSummary = procedureSummary;
    }
    public boolean isProcedureSummaryChanged() {
        return procedureSummaryChanged;
    }
    public void setProcedureSummaryChanged(boolean procedureSummaryChanged) {
        this.procedureSummaryChanged = procedureSummaryChanged;
    }
   
    public void compare(IacucProtocolSummary other) {
        IacucProtocolSpeciesSummary otherSummary = (other == null) ? null : other.findSpeciesSummary(speciesId);
        if (otherSummary == null) {
            speciesChanged = true;
            speciesGroupChanged = true;
            usdaCoveredChanged = true;
            strainChanged = true;
            speciesCountChanged = true;
            speciesCountTypeChanged = true;
            painCategoryChanged = true;
            procedureSummaryChanged = true;
        } else {
            speciesChanged = !StringUtils.equals(species, otherSummary.species);
            speciesGroupChanged = !StringUtils.equals(speciesGroup, otherSummary.speciesGroup);
            usdaCoveredChanged = usdaCovered != otherSummary.usdaCoveredChanged;
            strainChanged = !StringUtils.equals(strain, otherSummary.strain);
            speciesCountChanged = speciesCount != otherSummary.speciesCount;
            speciesCountTypeChanged = !StringUtils.equals(speciesCountType, otherSummary.speciesCountType);
            painCategoryChanged = !StringUtils.equals(painCategory, otherSummary.painCategory);
            procedureSummaryChanged = !StringUtils.equals(procedureSummary, otherSummary.procedureSummary);
        }
    }
}