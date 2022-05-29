package tags;

import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.tagext.BodyTagSupport;
import ctf.CtfBean;


public class IterateHandler extends BodyTagSupport {
	private List list;
	private Integer size;
	private Iterator iterator;
	
	public void setList(List list) {
		this.list = list;
	}

	public int doStartTag() throws JspException {
		size = list.size();
		return size > 0 ? EVAL_BODY_BUFFERED : SKIP_BODY;
	}

	public void doInitBody() throws JspException {
		iterator = list.iterator();
		
		Object e = iterator.next();
		
		pageContext.setAttribute("item", e);
	}

	public int doAfterBody() throws JspException {
		if (iterator.hasNext()) {
			Object e = iterator.next();
						
			pageContext.setAttribute("item", e);
			
			return EVAL_BODY_AGAIN;
		} else {
			try {
				getBodyContent().writeOut(getPreviousOut());
				}
				catch (java.io.IOException e) {
				throw new JspException (e.getMessage());
				}
				return SKIP_BODY;
		}
	}
}

