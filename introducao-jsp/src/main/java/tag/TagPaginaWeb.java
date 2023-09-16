package tag;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class TagPaginaWeb extends SimpleTagSupport{

	@Override
	public void doTag() throws JspException, IOException {
		
		JspWriter outWriter = getJspContext().getOut();
			outWriter.println("Tag JSP customizada");
	}
}
