package com.liferay.blade.samples.portlet.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.portlet.RenderResponse;
import javax.portlet.filter.RenderResponseWrapper;

public class RenderResponseBuffer extends RenderResponseWrapper {
	
	private final ByteArrayOutputStream buffer;
	private OutputStream output;
	private PrintWriter writer;

	public RenderResponseBuffer(RenderResponse response) {
		super(response);
		buffer = new ByteArrayOutputStream(response.getBufferSize());
	}

	@Override
	public OutputStream getPortletOutputStream() {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called on this response.");
		}

		if (output == null) {
			output = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					buffer.write(b);
				}

				@Override
				public void flush() throws IOException {
					buffer.flush();
				}

				@Override
				public void close() throws IOException {
					buffer.close();
				}
			};
		}

		return output;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (output != null) {
			throw new IllegalStateException("getPortletOutputStream() has already been called on this response.");
		}

		if (writer == null) {
			writer = new PrintWriter(new OutputStreamWriter(buffer, getCharacterEncoding()));
		}

		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		super.flushBuffer();

		if (writer != null) {
			writer.flush();
		} else if (output != null) {
			output.flush();
		}
	}

	public byte[] getResponseAsBytes() throws IOException {
		if (writer != null) {
			writer.close();
		} else if (output != null) {
			output.close();
		}

		return buffer.toByteArray();
	}

	public String getResponseAsString() throws IOException {
		return new String(getResponseAsBytes(), getCharacterEncoding());
	}


}
