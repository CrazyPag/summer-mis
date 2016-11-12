package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.ActionForm;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.Field;

public abstract class Grid extends Component implements DataView {
	// 数据源
	private DataSet dataSet;
	// PC专用表格列
	private List<Field> fields = new ArrayList<>();
	// 当前样式选择
	private String CSSClass_PC = "dbgrid";
	private String CSSClass_Phone = "context";
	private String CSSStyle;
	// 分页控制
	private MutiPage pages = new MutiPage();
	// 是否允许修改
	private boolean readonly = true;
	//
	private HttpServletRequest request;
	protected ActionForm form;
	protected boolean extGrid;

	public Grid() {
		super();
		this.setId("grid");
	}

	public Grid(Component owner) {
		super(owner);
		this.setId("grid");
	}

	@Override
	public Record getRecord() {
		return dataSet.getCurrent();
	}

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		if (this.dataSet == dataSet)
			return;
		this.dataSet = dataSet;
		if (request == null)
			throw new RuntimeException("request is null");

		int pageno = 1;
		String tmp = request.getParameter("pageno");
		if (tmp != null && !tmp.equals("")) {
			pageno = Integer.parseInt(tmp);
		}
		pages.setRecordCount(dataSet.size());
		pages.setCurrent(pageno);
	}

	public void addField(Field field) {
		fields.add(field);
	}

	public String getCSSClass_PC() {
		return CSSClass_PC;
	}

	public void setCSSClass_PC(String cSSClass_PC) {
		CSSClass_PC = cSSClass_PC;
	}

	public String getCSSClass_Phone() {
		return CSSClass_Phone;
	}

	public void setCSSClass_Phone(String cSSClass_Phone) {
		CSSClass_Phone = cSSClass_Phone;
	}

	public String getCSSStyle() {
		return CSSStyle;
	}

	public void setCSSStyle(String cSSStyle) {
		CSSStyle = cSSStyle;
	}

	@Override
	public void output(HtmlWriter html) {
		if (this.dataSet.size() == 0)
			return;
		if (form != null) {
			html.print("<form");
			if (form.getAction() != null)
				html.print(" action=\"%s\"", form.getAction());
			if (form.getMethod() != null)
				html.print(" method=\"%s\"", form.getMethod());
			if (form.getId() != null)
				html.print(" id=\"%s\"", form.getId());
			if (form.getEnctype() != null)
				html.print(" enctype=\"%s\"", form.getEnctype());
			html.println(">");
			for (String key : form.getItems().keySet()) {
				String value = form.getItems().get(key);
				html.print("<input");
				html.print(" type=\"hidden\"");
				html.print(" name=\"%s\"", key);
				html.print(" id=\"%s\"", key);
				html.print(" value=\"%s\"", value);
				html.println("/>");
			}
			outputGrid(html);
			html.println("</form>");
		} else{
			outputGrid(html);
		}
	}

	public MutiPage getPages() {
		return pages;
	}

	public List<Field> getFields() {
		return this.fields;
	}

	@Override
	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		if (this.readonly == readonly)
			return;
		for (Field field : this.getFields())
			field.setReadonly(readonly);
		this.readonly = readonly;
	}

	@Override
	public int getRecNo() {
		return dataSet.getRecNo();
	}

	@Deprecated
	public ActionForm getForm() {
		return form;
	}

	@Deprecated
	public void setForm(ActionForm form) {
		this.form = form;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public abstract void outputGrid(HtmlWriter html);

	@Deprecated
	public boolean isExtGrid() {
		return this.extGrid;
	}

	@Deprecated
	public void setExtGrid(boolean extGrid) {
		this.extGrid = extGrid;
	}
}
