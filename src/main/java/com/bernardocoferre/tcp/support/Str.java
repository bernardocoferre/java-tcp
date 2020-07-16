package com.bernardocoferre.tcp.support;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Str {

	public static String fromHex(String hex) {
		try {
			if (StringUtils.isBlank(hex))
				return null;

			byte[] bytes = Hex.decodeHex(hex.replaceAll("\\s", "").toCharArray());
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (DecoderException e) {
			throw new IllegalArgumentException(new Builder("Failed to parse Hex value to java.lang.String for input: [{}]").build(hex), e);
		}
	}

	public static class Builder {

		private String base;
		private List<Object> objects = new ArrayList<Object>();

		public Builder() {
			//
		}

		public Builder(String message) {
			this.base(message);
		}

		public Builder(String message, int current, int total) {
			this.base(message);
			this.withProgress(current, total);
		}

		public Builder base(String message) {
			this.base = message;

			return this;
		}

		public Builder withProgress(int current, int total) {
			List<Object> temp = new ArrayList<>();

			temp.add(current);
			temp.add(total);
			temp.add(current * 100 / total);

			if (this.objects != null && this.objects.size() > 0)
				temp.addAll(this.objects);

			this.objects = temp;

			return this;
		}

		public Builder withParam(Object... objects) {
			if (objects != null)
				Collections.addAll(this.objects, objects);

			return this;
		}

		public String build(Object... objects) {
			// add object parameters
			this.withParam(objects);

			if (this.base == null)
				this.base = "";

			return this.compile();
		}

		public String build() {
			return
					this.build((Object) null);
		}

		private String compile() {
			String message = this.base;

			int index = 0;

			while (message.contains("{}"))
				message = message.replaceFirst("\\{}", String.valueOf(this.objects.get(index++)));

			return message;
		}
	}

}