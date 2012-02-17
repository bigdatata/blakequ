package cm.commons.controller;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.commons.pojos.Computer;
import cm.commons.stat.service.ComputerService;
import cm.commons.sys.service.ComputerLogService;

@Controller
public class ComputerController {
	
	private ComputerService computerService;
	private ComputerLogService computerLogService;

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	public void setComputerLogService(ComputerLogService computerLogService) {
		this.computerLogService = computerLogService;
	}


	@RequestMapping("computer")
	public void ComputerService(PrintWriter out){
		Computer c = (Computer) computerService.get(1);
		out.print("nihao "+c);
	}

}
