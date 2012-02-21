package cm.commons.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.stat.service.PortService;
import cm.commons.stat.service.RouterService;
import cm.commons.sys.service.RouterLogService;

@Controller
@RequestMapping("router")
public class RouterController {
	
	@Autowired
	private PortService portService;
	@Autowired
	private RouterService routerService;
	@Autowired
	private RouterLogService routerLogService;
	
	
}
