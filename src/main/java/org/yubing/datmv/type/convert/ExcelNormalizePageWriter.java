package org.yubing.datmv.type.convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.type.excel.ExcelWriter;

/**
 * 基于Excel的正规化器
 * 
 * @author yubing
 *
 */
public class ExcelNormalizePageWriter extends ExcelWriter {
	protected ByteArrayOutputStream baos;
	
	public ExcelNormalizePageWriter() {
		this.baos = new ByteArrayOutputStream();
		this.os = baos;
	}

	protected Size writeDataField(DataField dataField, int colNum, int rowNum, boolean transpose)
			throws RowsExceededException, WriteException {
		Size size = new Size(0, 0);
		
		Object data = dataField.getData();
		
		if (data instanceof RecordPage) {
			RecordPage page = (RecordPage)data;
			size = super.writePage(page, colNum, rowNum, transpose);
		} else {
			size = super.writeDataField(dataField, colNum, rowNum, transpose);
		}

		return size;
	}
	
	/**
	 * 获取Excel字节数组
	 * 
	 * @return
	 */
	public byte[] getExcelBytes() {
		return this.baos.toByteArray();
	}
	
	/**
	 * 获取Excel输入流
	 * 
	 * @return
	 */
	public InputStream getExcelInputStream() {
		return new ByteArrayInputStream(this.baos.toByteArray());
	}
}

