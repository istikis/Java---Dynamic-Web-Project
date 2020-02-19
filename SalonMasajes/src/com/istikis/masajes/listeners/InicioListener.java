package com.istikis.masajes.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.istikis.masajes.repositorios.FabricaDaoProperties;
import com.istikis.masajes.controladores.Globales;
import com.istikis.masajes.repositorios.FabricaDao;


@WebListener
public class InicioListener implements ServletContextListener {
	
	public void contextDestroyed(ServletContextEvent sce) {}

    public void contextInitialized(ServletContextEvent sce)  { 
    	
        String pathConfiguracion = sce.getServletContext().getRealPath("/WEB-INF/") + "dao.properties";
    	
        FabricaDao fabricaDao = FabricaDaoProperties.getInstancia(pathConfiguracion);
    	
        Globales.daoSesion = fabricaDao.getSesionDao();        
        Globales.daoCliente = fabricaDao.getClienteDao();        
        Globales.daoTrabajador = fabricaDao.getTrabajadorDao();        
        Globales.daoServicio = fabricaDao.getServicioDao();
    }	
}
