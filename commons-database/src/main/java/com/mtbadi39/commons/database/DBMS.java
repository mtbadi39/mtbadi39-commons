package com.mtbadi39.commons.database;

/**
 *
 * @author badi.mohammedtahar
 */
public class DBMS {

    private String name;
    private String jdbcDriver;
    private String hibernateDialect;
    private String defaultPort;
    private String urlExtra;

    public DBMS() {
    }

    public DBMS(String name, String jdbcDriver, String hibernateDialect, String defaultPort, String urlExtra) {
        this.name = name;
        this.jdbcDriver = jdbcDriver;
        this.hibernateDialect = hibernateDialect;
        this.defaultPort = defaultPort;
        this.urlExtra = urlExtra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    public String getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(String defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getUrlExtra() {
        return urlExtra;
    }

    public void setUrlExtra(String urlExtra) {
        this.urlExtra = urlExtra;
    }

}
