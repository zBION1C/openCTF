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
		
		CtfBean ctf = (CtfBean) iterator.next();
		
		pageContext.setAttribute("id", ctf.getId());
		pageContext.setAttribute("title", ctf.getTitle());
		pageContext.setAttribute("difficulty", ctf.getDifficulty());
		pageContext.setAttribute("date", ctf.getDate());
		pageContext.setAttribute("description", ctf.getDescription());
		pageContext.setAttribute("flag", ctf.getFlag());
		pageContext.setAttribute("creator", ctf.getCreator());
		pageContext.setAttribute("category", ctf.getCategory());
	}

	public int doAfterBody() throws JspException {
		if (iterator.hasNext()) {
			CtfBean ctf = (CtfBean) iterator.next();
						
			pageContext.setAttribute("id", ctf.getId());
			pageContext.setAttribute("title", ctf.getTitle());
			pageContext.setAttribute("difficulty", ctf.getDifficulty());
			pageContext.setAttribute("date", ctf.getDate());
			pageContext.setAttribute("description", ctf.getDescription());
			pageContext.setAttribute("flag", ctf.getFlag());
			pageContext.setAttribute("creator", ctf.getCreator());
			pageContext.setAttribute("category", ctf.getCategory());
			
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