package com.asu.seatr.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.asu.seatr.utils.GlobalConstants;

/**
 * 
 * @author Lakshmisagar Kusnoor
 *
 */
public class SessionFactoryUtil {

    private static SessionFactory sesFactory;
    private static ServiceRegistry sesRegistry;
    static Configuration cfg;
    public static SessionFactory initSessionFactory(String path){
        try{
        	StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder() .configure(path).build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            try{
                System.out.println("Connected to Master Database Server");
                return metadata.getSessionFactoryBuilder().build();
            }                   
            catch(Throwable ex){
                cfg= new Configuration().configure("lib/"+path); 
                sesRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
                sesFactory=cfg.buildSessionFactory(sesRegistry);
                System.out.println("Connected to Slave Database Server");
                return null;
            }
        }
        catch(Throwable ex){
            System.out.println("Master & Slave Database Error.");
            System.err.println("Initial SessionFactory Creation Failed"+ex);
            throw new ExceptionInInitializerError(ex);
        }
    }   
    public  static SessionFactory getSessionFactory(String dbConfigPath) {
    	if(sesFactory == null)
    	{
    		sesFactory = initSessionFactory(dbConfigPath);
    	}
            
    	return sesFactory;
    }
}
