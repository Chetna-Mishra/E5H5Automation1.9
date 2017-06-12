package com.adv.qa.selenium.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ISuite;
import org.testng.reporters.EmailableReporter;
import org.testng.xml.XmlSuite;

public class ReportListeners extends EmailableReporter{
	String prefix = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
	EmailableReporter email = new EmailableReporter();
	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1,String arg2)
	{
			super.generateReport(arg0, arg1, arg2);
	}

	@Override
	protected PrintWriter createWriter(String outdir) throws IOException {
		   new File(outdir).mkdirs();
		    return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir+"\\HTMLReports",
		        "emailable-report"+prefix+".html"))));
	}
}
