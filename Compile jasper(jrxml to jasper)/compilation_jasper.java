
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DispatchReportAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("application/pdf");
		response.setHeader("Pragma", "cache");
		response.setHeader("Cache-Control", "cache");
		/*
		 * To clear cache of browser and give every time a fresh response not
		 * from cache of browser response.setHeader( "Pragma", "cache" );
		 * response.setHeader( "Cache-Control",
		 * "cache, no-store, must-revalidate" ); response.setDateHeader(
		 * "Expires", 0);
		 */
		ServletOutputStream sos = response.getOutputStream();		
		/* below code for compilation */
		HttpSession session = request.getSession(false);
		String file =session.getServletContext().getRealPath("/jaspersrc/TEST.jrxml");
		String file1 =session.getServletContext().getRealPath("/jaspersrc/TEST.jasper");
		JasperCompileManager.compileReportToFile(file,file1);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
		
		InputStream reportStream = getServlet().getServletConfig().getServletContext()
				.getResourceAsStream("/jaspersrc/TEST.jasper");		
		
		System.out.println("Compiling: completed!");
		try {

			Connection con = DBService.getConnection();			

			HashMap parameters = new HashMap();
			parameters.put("ProposalId", proposalId);
			parameters.put("Test", tests);
			parameters.put("Prop", proposalno);
 
			JasperPrint jasperPrint = null;
			try {
				
				jasperPrint = JasperFillManager.fillReport(reportStream, parameters, con);
				
			} catch (JRException e) {
				e.printStackTrace();
			}

			JRPdfExporter pdfexporter = new JRPdfExporter();
			pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			pdfexporter.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);

			try {
				pdfexporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
			}

			sos.flush();
			sos.close();

		} catch (Exception e) {
			// display stack trace in the browser
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}

		return null;
	}
}

