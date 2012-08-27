package com.photon.phresco.commons.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RepoInfo {
    
    private String id;
    private String customerId;
    private String releaseRepoURL;
    private String snapshotRepoURL;
    private String groupRepoURL;
    private String repoUserName;
    private String repoPassword;
    
    /**
     * 
     */
    public RepoInfo() {
        
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return
     */
    public String getReleaseRepoURL() {
        return releaseRepoURL;
    }

    /**
     * @param repoURL
     */
    public void setReleaseRepoURL(String repoURL) {
        this.releaseRepoURL = repoURL;
    }

    /**
     * @return
     */
    public String getRepoUserName() {
        return repoUserName;
    }

    /**
     * @param repoUserName
     */
    public void setRepoUserName(String repoUserName) {
        this.repoUserName = repoUserName;
    }

    /**
     * @return
     */
    public String getRepoPassword() {
        return repoPassword;
    }

    /**
     * @param repoPassword
     */
    public void setRepoPassword(String repoPassword) {
        this.repoPassword = repoPassword;
    }
    
    /**
     * @return
     */
    public String getSnapshotRepoURL() {
        return snapshotRepoURL;
    }

    /**
     * @param snapshotRepoURL
     */
    public void setSnapshotRepoURL(String snapshotRepoURL) {
        this.snapshotRepoURL = snapshotRepoURL;
    }

    /**
     * @return
     */
    public String getGroupRepoURL() {
        return groupRepoURL;
    }

    /**
     * @param groupRepoURL
     */
    public void setGroupRepoURL(String groupRepoURL) {
        this.groupRepoURL = groupRepoURL;
    }

    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("id", id)
                .append("customerId", customerId)
                .append("repoURL", releaseRepoURL)
                .append("repoUserName", repoUserName)
                .append("repoPassword", repoPassword)
                .append("snapshotRepoURL", snapshotRepoURL)
                .append("groupRepoURL", groupRepoURL)
                .toString();
    }
}
