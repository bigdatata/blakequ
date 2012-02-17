package cm.commons.controller;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.commons.pojos.Computer;
import cm.commons.pojos.ComputerLog;
import cm.commons.stat.service.ComputerService;
import cm.commons.sys.service.ComputerLogService;

@Controller
@RequestMapping("computer")
public class ComputerController {
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerLogService computerLogService;

	@RequestMapping("computer")
	public void computerService(PrintWriter out){
		Computer c = (Computer) computerService.get(1);
		
		out.println(c);
//		out.println(c.getStation());
//		
//		Iterator i = c.getComputerLogs().iterator();
//		while(i.hasNext()){
//			ComputerLog cl = (ComputerLog) i.next();
//			out.println(cl);
//		}
	}
	
	@RequestMapping("2")
	public void test1(PrintWriter out){
		Computer c = (Computer) computerService.get(1);
		out.println(c.getStation());
	}
	
	@RequestMapping("3")
	public void test2(PrintWriter out){
		ComputerLog cl = (ComputerLog) computerLogService.get(1);
		out.println(cl);
	}
	
	@RequestMapping("4")
	public void test3(PrintWriter out){
		ComputerLog cl = (ComputerLog) computerLogService.get(1);
		out.println(cl.getComputer());
	}

}
