package com.bjpowernode.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * ��hbm����ddl
 * @author Administrator
 *
 */
public class ExportDB {

	public static void main(String[] args) {
		
		//Ĭ�϶�ȡhibernate.cfg.xml�ļ�
		Configuration cfg = new Configuration().configure();
		
		SchemaExport export = new SchemaExport(cfg);
		export.create(true, true);
	}
}
