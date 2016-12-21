package cn.cerc.jpage.core;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.DataSet;

public interface DataSource {

	public void addField(IField field);

	public DataSet getDataSet();

	default public void updateValue(String id, String code) {

	}

	default public boolean isReadonly() {
		return true;
	}

	default public HttpServletRequest getRequest() {
		return null;
	}
}