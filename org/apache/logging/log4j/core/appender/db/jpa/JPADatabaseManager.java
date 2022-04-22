package org.apache.logging.log4j.core.appender.db.jpa;

import org.apache.logging.log4j.core.appender.db.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.core.*;
import javax.persistence.*;
import org.apache.logging.log4j.core.appender.*;

public final class JPADatabaseManager extends AbstractDatabaseManager
{
    private static final JPADatabaseManagerFactory FACTORY;
    private final String entityClassName;
    private final Constructor entityConstructor;
    private final String persistenceUnitName;
    private EntityManagerFactory entityManagerFactory;
    
    private JPADatabaseManager(final String s, final int n, final Class clazz, final Constructor entityConstructor, final String persistenceUnitName) {
        super(s, n);
        this.entityClassName = clazz.getName();
        this.entityConstructor = entityConstructor;
        this.persistenceUnitName = persistenceUnitName;
    }
    
    @Override
    protected void connectInternal() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
    }
    
    @Override
    protected void disconnectInternal() {
        if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
            this.entityManagerFactory.close();
        }
    }
    
    @Override
    protected void writeInternal(final LogEvent logEvent) {
        if (!this.isConnected() || this.entityManagerFactory == null) {
            throw new AppenderLoggingException("Cannot write logging event; JPA manager not connected to the database.");
        }
        final AbstractLogEventWrapperEntity abstractLogEventWrapperEntity = this.entityConstructor.newInstance(logEvent);
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist((Object)abstractLogEventWrapperEntity);
        transaction.commit();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
    
    public static JPADatabaseManager getJPADatabaseManager(final String s, final int n, final Class clazz, final Constructor constructor, final String s2) {
        return (JPADatabaseManager)AbstractDatabaseManager.getManager(s, new FactoryData(n, clazz, constructor, s2), JPADatabaseManager.FACTORY);
    }
    
    JPADatabaseManager(final String s, final int n, final Class clazz, final Constructor constructor, final String s2, final JPADatabaseManager$1 object) {
        this(s, n, clazz, constructor, s2);
    }
    
    static {
        FACTORY = new JPADatabaseManagerFactory(null);
    }
    
    private static final class JPADatabaseManagerFactory implements ManagerFactory
    {
        private JPADatabaseManagerFactory() {
        }
        
        public JPADatabaseManager createManager(final String s, final FactoryData factoryData) {
            return new JPADatabaseManager(s, factoryData.getBufferSize(), FactoryData.access$100(factoryData), FactoryData.access$200(factoryData), FactoryData.access$300(factoryData), null);
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        JPADatabaseManagerFactory(final JPADatabaseManager$1 object) {
            this();
        }
    }
    
    private static final class FactoryData extends AbstractFactoryData
    {
        private final Class entityClass;
        private final Constructor entityConstructor;
        private final String persistenceUnitName;
        
        protected FactoryData(final int n, final Class entityClass, final Constructor entityConstructor, final String persistenceUnitName) {
            super(n);
            this.entityClass = entityClass;
            this.entityConstructor = entityConstructor;
            this.persistenceUnitName = persistenceUnitName;
        }
        
        static Class access$100(final FactoryData factoryData) {
            return factoryData.entityClass;
        }
        
        static Constructor access$200(final FactoryData factoryData) {
            return factoryData.entityConstructor;
        }
        
        static String access$300(final FactoryData factoryData) {
            return factoryData.persistenceUnitName;
        }
    }
}
