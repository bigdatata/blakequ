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


}
