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
package org.kuali.kra.protocol.noteattachment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.kuali.kra.bo.AttachmentFile;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.protocol.ProtocolDocumentBase;
import org.kuali.kra.protocol.ProtocolFormBase;
import org.kuali.kra.service.VersionException;
import org.kuali.kra.service.VersioningService;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.util.ObjectUtils;

/**
 * Class used for versioning protocol attachments.
 * 
 * <p>
 * The basic algorithm for versioning is the following:
 * 
 * <ol>
 * <li> check if versioning is required </li>
 * <li> version the File BO </li>
 * <li> associate the new File version with an "existing" Attachment BO </li>
 * <li> create a new ProtocolBase Document </li>
 * <li> version the ProtocolBase and all its BOs which includes all attachment BOs (that are referencing the new file BO) </li>
 * <li> set the new ProtocolBase Document w/ the new ProtocolBase version </li>
 * <li> set the struts form to reference the new ProtocolBase Document </li>
 * </ol>
 * </p>
 * 
 * It was easier to create the version of the file before the version of the ProtocolBase to allow for easy updating of file references.
 * Also, versioning of the ProtocolBase naturally only needs to happen once, at the end of this process.
 */
public abstract class ProtocolAttachmentVersioningUtilityBase {

    protected final ProtocolAttachmentService notesService;
    protected final VersioningService versionService;
    protected final DocumentService docService;
    
    protected final ProtocolFormBase form;
    protected static final String ATTACHMENT_DELETED = "3";
    protected static final String ATTACHMENT_DRAFTED = "1";
    protected ProtocolDocumentBase newDocumentVersion;
    
    /**
     * Constructs a versioning util.
     * @param form the form
     * @param notesService the notesService
     * @param versionService the versionService
     * @param docService the docService
     * @throws IllegalArgumentException if the form, notesService, versionService, or docService is null
     */
    protected ProtocolAttachmentVersioningUtilityBase(final ProtocolFormBase form, final ProtocolAttachmentService notesService, final VersioningService versionService, final DocumentService docService) {
        if (form == null) {
            throw new IllegalArgumentException("the form was null");
        }
        
        if (notesService == null) {
            throw new IllegalArgumentException("the notesService was null");
        }
        
        if (versionService == null) {
            throw new IllegalArgumentException("the versionService was null");
        }
        
        if (docService == null) {
            throw new IllegalArgumentException("the docService was null");
        }
        
        this.form = form;
        this.versionService = versionService;
        this.docService = docService;
        this.notesService = notesService;
    }
    
    /** 
     * Versions the passed in attachment.  The act of versioning will do the following:
     * Create a new document and create a new protocol version.
     * 
     * The new attachment will be added to the protocol and the new protocol will set on the document.
     * Everything will be saved to the database and the document will be set on the passed in form.
     * As a result, the new document will become the "open" document on the form.
     * 
     * @param toVersion the attachment to version.
     * @throws IllegalArgumentException if the attachment is null or if the attachment is not "new"
     */
    void versionNewAttachment(final ProtocolAttachmentBase toVersion) {
        if (toVersion == null) {
            throw new IllegalArgumentException("the attachment to version was null");
        }
        
        if (!toVersion.isNew()) {
            throw new IllegalArgumentException("the attachment to version is not new " + toVersion);
        }
        
        this.addAttachment(toVersion);
    }
    
    
    /** 
     * Versions the passed in attachment.  The act of versioning will do the following:
     * Create a new document, create a new protocol version, and create a new attachment
     * version indicating that the attachment is now deleted.
     * 
     * The new attachment will be added to the protocol and the new protocol will set on the document.
     * Everything will be saved to the database and the document will be set on the passed in form.
     * As a result, the new document will become the "open" document on the form.
     * 
     * @param toVersion the attachment to version.
     * @throws IllegalArgumentException if the attachment is null or if the attachment is "new"
     */
    void versionDeletedAttachment(final ProtocolAttachmentBase toVersion) {
        if (toVersion == null) {
            throw new IllegalArgumentException("the attachment to version was null");
        }
        
        if (toVersion.isNew()) {
            throw new IllegalArgumentException("the attachment to version is new " + toVersion);
        }
        ProtocolAttachmentProtocolBase newAttachment = (ProtocolAttachmentProtocolBase) this.deleteAttachment(toVersion);
        newAttachment.setDocumentStatusCode(ATTACHMENT_DELETED);
        this.form.getProtocolDocument().getProtocol().getAttachmentProtocols().add(newAttachment);
    }
    
    
    /**
     * 
     * This method retrieves all the existing attachments on the passed in form.  For every modified attachment,
     * this method will create a new attachment version.
     * 
     * If any attachments are versioned this method will do the following: Create a new document, create a new protocol
     * version, and create a new attachment version.  This method will only trigger one new document or protocol version
     * regardless of the amount of modified attachments.
     * 
     * The new attachments will be added to the protocol and the new protocol will set on the document.
     * Everything will be saved to the database and the document will be set on the passed in form.
     * As a result, the new document will become the "open" document on the form.
     */
    void versionExstingAttachments() {
        //only need to version attachment protocols at the moment
        boolean createVersion = this.versionExstingAttachments(this.form.getProtocolDocument().getProtocol().getAttachmentProtocols(), getProtocolAttachmentProtocolClassHook());
    }

    /** 
     * Versions all attachments for a given Collection of attachments on the protocol.  Any changes done on a passed in
     * attachment will effectively be reverted to what is in the database and those changes will be reflected
     * in a new attachment & protocol version.  The passed in Collection or objects in Collection may be modified.
     * <T> the type of attachments
     * @param attachments the attachments.
     * @param class1 the class token for type of attachments (required for adding to generic Collection)
     */
    protected <T extends ProtocolAttachmentBase> boolean versionExstingAttachments(final Collection<ProtocolAttachmentProtocolBase> attachments,
            final Class<? extends ProtocolAttachmentProtocolBase> class1) {
        assert attachments != null : "the attachments was null";
        assert class1 != null : "the type was null";
        List<ProtocolAttachmentProtocolBase> attachmentProtocols = new ArrayList<ProtocolAttachmentProtocolBase>();
        boolean createVersion = false;
        for (final ProtocolAttachmentProtocolBase attachment : ProtocolAttachmentBase.filterExistingAttachments(attachments)) {
            final ProtocolAttachmentProtocolBase persistedAttachment = this.notesService.getAttachment(class1, attachment.getId());

            boolean isReplaceFile = hasChanged(attachment.getFile(), persistedAttachment.getFile());
            if (attachment instanceof ProtocolAttachmentProtocolBase
                    && ("2".equals(((ProtocolAttachmentProtocolBase) attachment).getDocumentStatusCode()) || "3"
                            .equals(((ProtocolAttachmentProtocolBase) attachment).getDocumentStatusCode()))
                    && (isReplaceFile || !attachment.equals(persistedAttachment))) {
                createVersion = true;
                // change the attachments file...when the doc is versioned the change will come w/ it
                if (ATTACHMENT_DELETED.equals(((ProtocolAttachmentProtocolBase) attachment).getDocumentStatusCode())
                        && notesService.isNewAttachmentVersion((ProtocolAttachmentProtocolBase) attachment)) {
                    ((ProtocolAttachmentProtocolBase) attachment).setDocumentStatusCode(ATTACHMENT_DRAFTED);
                    ((ProtocolAttachmentProtocolBase) attachment).setChanged(true);
                } else {

                    if (!isChangeDeletedAtt((List<ProtocolAttachmentProtocolBase>)attachments, attachment.getDocumentId())) {
                        attachmentProtocols.add(createFileVersionOnAttachment(attachment, isReplaceFile));
                    }
                    restoreAttachment((ProtocolAttachmentProtocolBase) persistedAttachment, (ProtocolAttachmentProtocolBase) attachment);
                }
            } else if (ATTACHMENT_DRAFTED.equals(((ProtocolAttachmentProtocolBase) attachment).getDocumentStatusCode())
                    && (isReplaceFile || !attachment.equals(persistedAttachment))) {
                ((ProtocolAttachmentProtocolBase) attachment).setChanged(true);
            }

        }
        attachments.addAll(attachmentProtocols);
        return createVersion;
    }
    
    /*
     * check if the attachment is deleted and also some date change, for example 'description'
     * then don'e create new version because the deleted already has a new version
     */
    private boolean isChangeDeletedAtt(List<ProtocolAttachmentProtocolBase>attachments, Integer documentId) {
        boolean isChangedDelete = false;
        for (ProtocolAttachmentProtocolBase attachment : attachments) {
            if (documentId.equals(attachment.getDocumentId()) && "3".equals(attachment.getDocumentStatusCode())) {
                isChangedDelete = true;
                break;
            }
        }
        return isChangedDelete;
    }   
    
    /*
     * Since the finalized attachment should not be changed.  we can't remove it from attachments and then add the persisted one
     * because there is complication of delete attachment also delete file, and subsequently all attachment that reference this file.
     */
    private void restoreAttachment(ProtocolAttachmentProtocolBase persistedAttachment, ProtocolAttachmentProtocolBase attachment) {
        attachment.setStatusCode(persistedAttachment.getStatusCode());
        attachment.setComments(persistedAttachment.getComments());
        attachment.setContactEmailAddress(persistedAttachment.getContactEmailAddress());
        attachment.setContactName(persistedAttachment.getContactName());
        attachment.setContactPhoneNumber(persistedAttachment.getContactPhoneNumber());
        attachment.setFileId(persistedAttachment.getFileId());
        attachment.setFile(persistedAttachment.getFile());
        attachment.setDescription(persistedAttachment.getDescription());
    }
    
    /**
     * Adds an attachment to the protocol.
     * @param attachment the attachment to add.
     */
    private void addAttachment(ProtocolAttachmentBase attachment) {
        assert attachment != null : "the attachment is null";
        if (attachment instanceof ProtocolAttachmentProtocolBase) {
            ((ProtocolAttachmentProtocolBase)attachment).setDocumentStatusCode(ATTACHMENT_DRAFTED);
            attachment.setDocumentId(getNextDOcumentId(this.form.getProtocolDocument().getProtocol().getAttachmentProtocols()));
        }
        this.form.getProtocolDocument().getProtocol().addAttachmentsByType(attachment);
    }
    
    private int getNextDOcumentId(List<? extends ProtocolAttachmentBase> attachments) {
        int nextDocumentId = 0;
        for (ProtocolAttachmentBase attachment : attachments) {
            if (attachment.getDocumentId() > nextDocumentId) {
                nextDocumentId = attachment.getDocumentId();
            }
        }
        return ++nextDocumentId;
    }
    /**
     * Delete an attachment from the protocol.
     * @param attachment the attachment to delete.
     */
    private ProtocolAttachmentBase deleteAttachment(ProtocolAttachmentBase attachment) {
        assert attachment != null : "the attachment is null";
        
        ProtocolAttachmentBase newAttachment = this.createFileVersionOnAttachment(attachment, false);

        return newAttachment;
    }
    
    /**
     * Checks if a version of an attachment should be created.
     * 
     * @param localFile the local attachment file
     * @param persistedFile the persisted attachment file with same id
     * @return true if should be versioned false if not.
     */
    private static boolean hasChanged(final AttachmentFile localFile, final AttachmentFile persistedFile) {
        
        if (persistedFile == null || localFile == null) {
            return false;
        }
                
        return !isSameFile(persistedFile, localFile);
    }
    
    /*
     * file1.equals(file2) may have issue with getclass() comparison. sometimes class may be AttachmentFile
     * some maybe Attachment$EnhancerCGLIB.  so retest equal with following for now.
     */
    private static boolean isSameFile(AttachmentFile obj, AttachmentFile other) {
        if (!Arrays.equals(obj.getData(), other.getData())) {
            return false;
        } else {
            if (obj.getId() != null && other.getId() != null && !obj.getId().equals(other.getId())) {
                return false;
            } else if (other.getId() == null) {
                return false;
            }
        }
        if (obj.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        }
        else if (!obj.getName().equals(other.getName())) {
            return false;
        }
        if (obj.getType() == null) {
            if (other.getType() != null) {
                return false;
            }
        }
        else if (!obj.getType().equals(other.getType())) {
            return false;
        }
        return true;
    }

    /**
     * Creates a version of an attachment's file. The act of versioning associates the passed in attachment with the new file.
     * 
     * @param <T> the attachment type
     * @param attachment the attachment
     * @return the attachment with the new file version
     * @throws VersionCreationExeption if unable to create a version
     */
    private <T extends ProtocolAttachmentBase> T createFileVersionOnAttachment(final T attachment, boolean isReplaceFile) {
        assert attachment != null : "the attachment was null";

        final AttachmentFile newVersion;
        final ProtocolAttachmentProtocolBase newAttachment;

        try {
            if (isReplaceFile) {
                newVersion = this.versionService.versionAssociate(attachment.getFile());
            } else {
                newVersion = null;
            }
            newAttachment = (ProtocolAttachmentProtocolBase) ObjectUtils.deepCopy(attachment);
            // newAttachment.setSequenceNumber(newAttachment.getSequenceNumber()+1);
        }
        catch (final VersionException e) {
            throw new VersionCreationExeption(e);
        }

        if (isReplaceFile) {
            newAttachment.setFile(newVersion);
            newAttachment.setFileId(null);
        }
        newAttachment.setUpdateTimestamp(null);
        newAttachment.setCreateTimestamp(null);
        newAttachment.setUpdateUser(null);
        newAttachment.setDocumentStatusCode(ATTACHMENT_DRAFTED);
        newAttachment.setId(null);
        ((ProtocolAttachmentProtocolBase)newAttachment).setAttachmentVersion(((ProtocolAttachmentProtocolBase)attachment).getAttachmentVersion() + 1);

        return (T) newAttachment;
    }
    
//Generic Versioning methods
    /**
     * Methods checks whether versioning is required (i.e. does adding/deleting/modfying
     * an attachment trigger a version or not)
     * @return true if versioning is required.
     */
    boolean versioningRequired() {
        return this.form.getProtocolDocument().getProtocol().isVersioningRequired();
    }    
    
    /**
     * Exception thrown for a version creation problem.
     */
    private static class VersionCreationExeption extends RuntimeException {
        
        private static final long serialVersionUID = -8816811550466248534L;

        /**
         * wraps a throwable.
         * @param t the original throwable.
         */
        public VersionCreationExeption(final Throwable t) {
            super(t);
        }
    }
    
    protected abstract Class<? extends ProtocolAttachmentProtocolBase> getProtocolAttachmentProtocolClassHook();
}
