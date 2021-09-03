package com.weijin.hadoopdemo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfo implements Serializable {
	
	private String host;

	private String user;
	
    private String password;

    private String selectPriv;

    private String insertPriv;

    private String updatePriv;

    private String deletePriv;

    private String createPriv;

    private String dropPriv;

    private String reloadPriv;

    private String shutdownPriv;

    private String processPriv;

    private String filePriv;

    private String grantPriv;

    private String referencesPriv;

    private String indexPriv;

    private String alterPriv;

    private String showDbPriv;

    private String superPriv;

    private String createTmpTablePriv;

    private String lockTablesPriv;

    private String executePriv;

    private String replSlavePriv;

    private String replClientPriv;

    private String createViewPriv;

    private String showViewPriv;

    private String createRoutinePriv;

    private String alterRoutinePriv;

    private String createUserPriv;

    private String sslType;

    private Integer maxQuestions;

    private Integer maxUpdates;

    private Integer maxConnections;

    private Integer maxUserConnections;
    
    private byte[] sslCipher;

    private byte[] x509Issuer;

    private byte[] x509Subject;

}