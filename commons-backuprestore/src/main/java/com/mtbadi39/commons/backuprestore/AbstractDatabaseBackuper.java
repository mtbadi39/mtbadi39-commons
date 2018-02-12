package com.mtbadi39.commons.backuprestore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * @author Geoffrey De Smet
 */
public abstract class AbstractDatabaseBackuper implements DatabaseBackuper, InitializingBean {

    /**
     * Logger available to subclasses
     */
    protected final transient Logger logger = LoggerFactory.getLogger(AbstractDatabaseBackuper.class);
    protected Resource databaseBackupLocation;
    protected boolean restoreDatabaseOnInitialization = false;
    protected boolean cleanDatabaseBeforeRestore = false;

    @Override
    public void setDatabaseBackupLocation(Resource databaseBackupLocation) {
        this.databaseBackupLocation = databaseBackupLocation;
    }

    @Override
    public void setRestoreDatabaseOnInitialization(boolean restoreDatabaseOnInitialization) {
        this.restoreDatabaseOnInitialization = restoreDatabaseOnInitialization;
    }

    @Override
    public void setCleanDatabaseBeforeRestore(boolean cleanDatabaseBeforeRestore) {
        this.cleanDatabaseBeforeRestore = cleanDatabaseBeforeRestore;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkConfig();
        if (restoreDatabaseOnInitialization) {
            restoreDatabase();
        }
    }

    protected void checkConfig() {
        Assert.notNull(databaseBackupLocation, "databaseBackupLocation is required");
    }

    @Override
    public abstract void cleanDatabase() throws Exception;

    @Override
    public abstract void dumpDatabase() throws Exception;

    @Override
    public abstract void restoreDatabase() throws Exception;
}
