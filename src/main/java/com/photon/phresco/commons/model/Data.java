package com.photon.phresco.commons.model;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.Gson;
import com.photon.phresco.util.Credentials;

public class Data {
    
    private String contentResourceURI;
    private String id;
    private String name;
    private String provider;
    private String providerRole;
    private String format;
    private String repoType;
    private boolean exposed;
    private String writePolicy;
    private boolean browseable;
    private boolean indexable;
    private int notFoundCacheTTL;
    private String repoPolicy;
    private boolean downloadRemoteIndexes;
    private String defaultLocalStorageUrl;
    
    public Data() {
        
    }
    
    /**
     * @return
     */
    public String getContentResourceURI() {
        return contentResourceURI;
    }
    
    /**
     * @param contentResourceURI
     */
    public void setContentResourceURI(String contentResourceURI) {
        this.contentResourceURI = contentResourceURI;
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
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return
     */
    public String getProvider() {
        return provider;
    }
    
    /**
     * @param provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    /**
     * @return
     */
    public String getProviderRole() {
        return providerRole;
    }
    
    /**
     * @param providerRole
     */
    public void setProviderRole(String providerRole) {
        this.providerRole = providerRole;
    }
    
    /**
     * @return
     */
    public String getFormat() {
        return format;
    }
    
    /**
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }
    
    /**
     * @return
     */
    public String getRepoType() {
        return repoType;
    }
    
    /**
     * @param repoType
     */
    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }
    
    /**
     * @return
     */
    public boolean getExposed() {
        return exposed;
    }
    
    /**
     * @param exposed
     */
    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }
    
    /**
     * @return
     */
    public String getWritePolicy() {
        return writePolicy;
    }
    
    /**
     * @param writePolicy
     */
    public void setWritePolicy(String writePolicy) {
        this.writePolicy = writePolicy;
    }
    
    /**
     * @return
     */
    public boolean isBrowseable() {
        return browseable;
    }
    
    /**
     * @param browseable
     */
    public void setBrowseable(boolean browseable) {
        this.browseable = browseable;
    }
    
    /**
     * @return
     */
    public boolean isIndexable() {
        return indexable;
    }
    
    /**
     * @param indexable
     */
    public void setIndexable(boolean indexable) {
        this.indexable = indexable;
    }
    
    /**
     * @return
     */
    public int getNotFoundCacheTTL() {
        return notFoundCacheTTL;
    }
    
    /**
     * @param notFoundCacheTTL
     */
    public void setNotFoundCacheTTL(int notFoundCacheTTL) {
        this.notFoundCacheTTL = notFoundCacheTTL;
    }
    
    /**
     * @return
     */
    public String getRepoPolicy() {
        return repoPolicy;
    }
    
    /**
     * @param repoPolicy
     */
    public void setRepoPolicy(String repoPolicy) {
        this.repoPolicy = repoPolicy;
    }
    
    /**
     * @return
     */
    public boolean isDownloadRemoteIndexes() {
        return downloadRemoteIndexes;
    }
    
    /**
     * @param downloadRemoteIndexes
     */
    public void setDownloadRemoteIndexes(boolean downloadRemoteIndexes) {
        this.downloadRemoteIndexes = downloadRemoteIndexes;
    }
    
    /**
     * @return
     */
    public String getDefaultLocalStorageUrl() {
        return defaultLocalStorageUrl;
    }
    
    /**
     * @param defaultLocalStorageUrl
     */
    public void setDefaultLocalStorageUrl(String defaultLocalStorageUrl) {
        this.defaultLocalStorageUrl = defaultLocalStorageUrl;
    }
    
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("contentResourceURI", contentResourceURI)
                .append("id", id)
                .append("name", name)
                .append("provider", provider)
                .append("providerRole", providerRole)
                .append("format", format)
                .append("repoType", repoType)
                .append("exposed", exposed)
                .append("writePolicy", writePolicy)
                .append("browseable", browseable)
                .append("indexable", indexable)
                .append("notFoundCacheTTL", notFoundCacheTTL)
                .append("repoPolicy", repoPolicy)
                .append("downloadRemoteIndexes", downloadRemoteIndexes)
                .append("defaultLocalStorageUrl", defaultLocalStorageUrl)
                .toString();
    }
    
    public static void main(String[] args) {
        byte[] encodeBase64 = Base64.encodeBase64("myphresco.123".getBytes());
        String encodedPassword = new String(encodeBase64);
        Credentials cre = new Credentials("kumar_s", encodedPassword);
        String json = new Gson().toJson(cre);
        System.out.println(json);
    }
}
