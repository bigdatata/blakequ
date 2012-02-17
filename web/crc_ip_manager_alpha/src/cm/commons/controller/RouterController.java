package cm.commons.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.stat.service.RouterService;
import cm.commons.sys.service.RouterLogService;

@Controller
@RequestMapping("router")
public class RouterController {

	@Autowired
	private RouterService routerService;
	@Autowired
	private RouterLogService routerLogService;
	
	@RequestMapping("1")
	public void test1(PrintWriter out){
		Router r = (Router) routerService.get(1);
		out.println(r);
	}
	
	@RequestMapping("2")
	public void test2(PrintWriter out){
		Router r = (Router) routerService.get(1);
		out.println(r.getStation());
	}
	
	@RequestMapping("3")
	public void test3(PrintWriter out){
		RouterLog rl = (RouterLog) routerLogService.get(1);
		out.println(rl);
	}
	
	@RequestMapping("4")
	public void test4(PrintWriter out){
		RouterLog rl = (RouterLog) routerLogService.get(1);
		out.println(rl.getRouter());
	}
}
