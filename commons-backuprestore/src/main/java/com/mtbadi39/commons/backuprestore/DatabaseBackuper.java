package com.mtbadi39.commons.backuprestore;

import org.springframework.core.io.Resource;

/**
 * Dumps and restores a database to and from a file. Very usefull for loading
 * test data in the database upon initialization (see the example in
 * {@link Hibernate3ToXstreamXmlDatabaseBackuper}. Can also be used for
 * sceduling database backups through quartz.
 *
 * @see AbstractDatabaseBackuper
 * @see Hibernate3ToXstreamXmlDatabaseBackuper
 * @author Geoffrey De Smet
 */
public interface DatabaseBackuper {

    /**
     * Set the location of database backup resource, for example as classpath
     * resource "classpath:/org/myproject/databaseBackup.xml". When using
     * {@link #dumpDatabase()}, this must be a writable file (no classpath
     * resource).
     *
     * @param databaseBackupLocation the location of the database backup file
     * containing a database dump
     */
    void setDatabaseBackupLocation(Resource databaseBackupLocation);

    /**
     * If true the database will be restored from the database backup resource
     * upon initialization of this bean. Defaults to false.
     *
     * @param restoreDatabaseOnInitialization restore the database after
     * properties set
     */
    void setRestoreDatabaseOnInitialization(boolean restoreDatabaseOnInitialization);

    /**
     * If true all records in the database will be removed before restoring the
     * database. Defaults to false.
     *
     * @param cleanDatabaseBeforeRestore clean the database before doing a
     * restore
     */
    void setCleanDatabaseBeforeRestore(boolean cleanDatabaseBeforeRestore);

    /**
     * Cleans the database. Depending on the implemenation, it could be that
     * only known tables will be cleaned.
     *
     * @throws java.lang.Exception
     */
    void cleanDatabase() throws Exception;

    /**
     * Dump the database to the database backup resource. If the backup file
     * exists, it will be overwritten.
     *
     * @throws java.lang.Exception
     */
    void dumpDatabase() throws Exception;

    /**
     * Restore the database from the database backup resource. If
     * cleanDatabaseBeforeRestore is true the database will be cleaned first.
     *
     * @throws java.lang.Exception
     */
    void restoreDatabase() throws Exception;
}
