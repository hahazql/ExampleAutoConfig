package model.config;

import model.dao.*;

public class ServerContent extends Context {


	// //////////////
	//
	public static ConfigDataDaoImpl configDataDao = (ConfigDataDaoImpl) Context.serverCtx.getBean("ConfigDataDao");
	public static ProjectDataDaoImpl projectDataDao = (ProjectDataDaoImpl) Context.serverCtx.getBean("ProjectDataDao");
	public static ServerConfig serverConfigDao = (ServerConfig) Context.serverCtx.getBean("ServerConfig");
}
